package com.thornbird.framework.stats.stat;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * stats类，long增量统计、double计量统计、JVM计量统计、标签统计、柱状统计、饼图统计等
 * ConcurrentHashMap是一个线程安全的Hash Table
 * @author Administrator
 */
public class Stats {

	protected Map<String, Counter> counterMap = new ConcurrentHashMap<String, Counter>();
	protected Map<String, Double> gaugeMap = new ConcurrentHashMap<String, Double>();
	protected Map<String, String> labelMap = new ConcurrentHashMap<String, String>();
	protected Map<String, Piegram> piegramMap = new ConcurrentHashMap<String, Piegram>();
	protected Map<String, Histogram> histogramMap = new ConcurrentHashMap<String, Histogram>();

	protected Stats(){
	}
	
	/*
	 * get操作
	 */
	public long getCounter(String name) {
		return counterMap.get(name).get();
	}

	public Map<String, Counter> getCounters() {
		return this.counterMap;
	}

	public Double getGauge(String name) {
		return gaugeMap.get(name);
	}

	public Map<String, Double> getGauges() {
		return this.gaugeMap;
	}

	public String getLabel(String name) {
		return labelMap.get(name);
	}

	public Map<String, String> getLabels() {
		return this.labelMap;
	}

	public Piegram getPie(String name) {
		return piegramMap.get(name);
	}

	public Map<String, Piegram> getPies() {
		return this.piegramMap;
	}

	public Histogram getHistogram(String name) {
		return histogramMap.get(name);
	}

	public Map<String, Histogram> getHistograms() {
		return this.histogramMap;
	}
	
	public StatSummary getSummary() {
		StatSummary statSummary = new StatSummary();
		
		for (String key : counterMap.keySet()) {
			statSummary.getCounters().put(key, counterMap.get(key).get());
		}
		
		for (String key : gaugeMap.keySet()) {
			statSummary.getGauges().put(key, gaugeMap.get(key));
		}
		
		for (String key : labelMap.keySet()) {
			statSummary.getLabels().put(key, labelMap.get(key));
		}
		
		for (String key : piegramMap.keySet()) {
			Piegram piegram = piegramMap.get(key);
			statSummary.getDistributions().put(key, piegram.toMap());
			statSummary.getPiegrams().put(key, piegram.toItemsList());
		}
		
		return statSummary;
	}
	
	public StatSummary getSummaryWithJVM() {
		StatSummary statSummary = new StatSummary();
		
		this.filljvmSummary(statSummary.getJvm());
		
		for (String key : counterMap.keySet()) {
			statSummary.getCounters().put(key, counterMap.get(key).get());
		}
		
		for (String key : gaugeMap.keySet()) {
			statSummary.getGauges().put(key, gaugeMap.get(key));
		}
		
		for (String key : labelMap.keySet()) {
			statSummary.getLabels().put(key, labelMap.get(key));
		}
		
		for (String key : piegramMap.keySet()) {
			Piegram piegram = piegramMap.get(key);
			statSummary.getDistributions().put(key, piegram.toMap());
			statSummary.getPiegrams().put(key, piegram.toItemsList());
		}
		
		return statSummary;
	}

	/*
	 * set操作
	 */
	public boolean resetCounterMap() {
		synchronized (this) {
			for (String counterName : counterMap.keySet()) {
				Counter counter = counterMap.get(counterName);
				counter.reset();
			}
		}
		return true;
	}

	public Double setGauge(String gaugeName, Double gaugeValue) {
		gaugeMap.put(gaugeName, gaugeValue);
		return gaugeValue;
	}

	public String setLabel(String labelName, String labelValue) {
		labelMap.put(labelName, labelValue);
		return labelValue;
	}

	/*
	 * add操作
	 */
	public Long incr(String name, Integer value) {
		Counter counter = counterMap.get(name);
		if (counter == null) {
			synchronized (this) {
				if (counter == null) {
					counter = new Counter();
					counterMap.put(name, counter);
				}
			}
		}
		return counter.incr(value);
	}

	public Long incr(String name) {
		Counter counter = counterMap.get(name);
		if (counter == null) {
			synchronized (this) {
				if (counter == null) {
					counter = new Counter();
					counterMap.put(name, counter);
				}
			}
		}
		return counter.incr();
	}

	public Double addGauge(String gaugeName, Double gaugeValue) {
		Double gauge = gaugeMap.get(gaugeName);
		if (gauge == null) {
			synchronized (this) {
				if (gauge == null) {
					gauge = gaugeValue;
					gaugeMap.put(gaugeName, gauge);
				}
			}
		} else {
			synchronized (this) {
				gauge += gaugeValue;
				gaugeMap.put(gaugeName, gauge);
			}
		}
		return gauge;
	}

	public void addPiegram(String piegramName, String itemName, Double itemValue) {
		Piegram piegram = piegramMap.get(piegramName);
		if (piegram == null) {
			synchronized (this) {
				if (piegram == null) {
					piegram = new Piegram();
					piegramMap.put(piegramName, piegram);
				}
			}
		}
		piegram.addItem(itemName, itemValue);
	}

	public void addPiegram(String piegramName, Map<String, Double> items) {
		for (String itemName : items.keySet()) {
			addPiegram(piegramName, itemName, items.get(itemName));
		}
	}

	/*
	 * jvm操作
	 * java类--java编译器转化为class文件--jvm(类装载器，字节码校验器，解释器)--操作平台运行
	 * jvm五种规格：JVM指令系统、JVM寄存器、JVM栈结构、JVM碎片回收堆、JVM存储区 Java SE 5引入了
	 * java.lang.management 包定义了 9 个 MBean，称为平台 MBean 或 MXBean
	 * ClassLoadingMXBean--类装载器
	 * CompilationMXBean--编译器
	 * MemoryMXBean--内存
	 * ThreadMXBean--线程
	 * RuntimeMXBean--运行时
	 * OperatingSystemMXBean--操作系统
	 * GarbageCollectorMXBean--垃圾收集器
	 * MemoryManagerMXBean--内存管理器
	 * MemoryPoolMXBean--内存池
	 */
	private void filljvmSummary(Map<String, Long> jvmSummary) {
		// 记录每次GC时间和次数，以及总时间和次数
		Long totalCollectionCount = 0L;
		Long totalCollectionTime = 0L;
		List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean gc : gcs) {
			String gcName = gc.getName();
			jvmSummary.put(String.format("%s%s%s", "jvm_gc_", gcName, "_count"),
					gc.getCollectionCount());
			jvmSummary.put(String.format("%s%s%s", "jvm_gc_", gcName, "_time"),
					gc.getCollectionTime());
			totalCollectionCount += gc.getCollectionCount();
			totalCollectionTime += gc.getCollectionTime();
		}
		jvmSummary.put("jvm_gc_total_count", totalCollectionCount);
		jvmSummary.put("jvm_gc_total_time", totalCollectionTime);

		/*
		 * 记录虚拟机内存系统 
		 * HeapMemoryUsage: 用于描述当前堆内存使用情况的只读属性(所有类实例和数组的内存)
		 * NonHeapMemoryUsage:用于描述当前的非堆内存的使用情况的只读属性(方法区 和 Java 虚拟机的内部处理或优化所需的内存)
		 * ObjectPendingFinalizationCount:用于描述有多少对象被挂起以便回收 Verbose: 用于动态设置 GC
		 * 是否跟着详细的堆栈信息，为一个布尔变量
		 */
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
		jvmSummary.put("jvm_heap_committed", heap.getCommitted());
		jvmSummary.put("jvm_heap_max", heap.getMax());
		jvmSummary.put("jvm_heap_used", heap.getUsed());
		this.addPiegram("jvm_heap", "used", Double.valueOf(heap.getMax()));
		this.addPiegram("jvm_heap", "free", Double.valueOf(heap.getMax() - heap.getUsed()));
		MemoryUsage noHeap = memoryMXBean.getNonHeapMemoryUsage();
		jvmSummary.put("jvm_noheap_committed", noHeap.getCommitted());
		jvmSummary.put("jvm_noheap_max", noHeap.getMax());
		jvmSummary.put("jvm_noheap_used", noHeap.getUsed());
		this.addPiegram("jvm_noheap", "used", Double.valueOf(noHeap.getUsed()));
		this.addPiegram("jvm_noheap", "free", Double.valueOf(noHeap.getMax() - noHeap.getUsed()));
		
		/*
		 * 记录虚拟机线程系统
		 * getThreadCount(): 返回活动线程的当前数目，包括守护线程和非守护线程
		 * getPeakThreadCount(): 返回自从Java虚拟机启动或峰值重置以来峰值活动线程计数
		 * getDaemonThreadCount(): 返回活动守护线程的当前数目
		 */
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		jvmSummary.put("jvm_thread_count", Long.valueOf(threadMXBean.getThreadCount()));
		jvmSummary.put("jvm_thread_peak_count", Long.valueOf(threadMXBean.getPeakThreadCount()));
		jvmSummary.put("jvm_thread_daemon_count", Long.valueOf(threadMXBean.getDaemonThreadCount()));
		
		/*
		 * 记录虚拟机runtime参数
		 */
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		jvmSummary.put("jvm_start_time", runtimeMXBean.getStartTime());
		jvmSummary.put("jvm_upTime", runtimeMXBean.getUptime());
		
		/*
		 * 记录操作系统信息
		 */
		OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
		jvmSummary.put("jvm_available_cpus", Long.valueOf(os.getAvailableProcessors()));
		
		/*
		 * 记录虚拟机内存池管理
		 */
		Long totalUsage = 0L;
		List<MemoryPoolMXBean> memoryPools = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean memoryPool : memoryPools) {
			totalUsage += memoryPool.getUsage().getUsed();
		}
		jvmSummary.put("jvm_post_gc_used", totalUsage);
	}

	/*
	 * clear操作
	 */
	public boolean clearCounterMap(String key) {
		this.counterMap.remove(key);
		return true;
	}
	
	public boolean clearGaugeMap(String key) {
		this.gaugeMap.remove(key);
		return true;
	}
	
	public boolean clearLabelMap(String key) {
		this.labelMap.remove(key);
		return true;
	}
	
	public boolean clearPiegramMap(String key) {
		this.piegramMap.remove(key);
		return true;
	}
	
	public boolean clearHistogramMap(String key) {
		this.histogramMap.remove(key);
		return true;
	}
	
	public boolean clearAll() {
		synchronized (this) {
			this.counterMap.clear();
			this.gaugeMap.clear();
			this.labelMap.clear();
			this.piegramMap.clear();
			this.histogramMap.clear();
		}
		return true;
	}
	
	/*
	 * to string操作
	 */
	public String toJson() {
		StatSummary summary = this.getSummary();
		return summary.toJson();
	}
	
	public String toJsonWithJvm() {
		StatSummary summary = this.getSummaryWithJVM();
		return summary.toJson();
	}
	
	public String toSimpleString() {
		StatSummary summary = this.getSummary();
		return summary.toSimpleString();
	}
	
	public String toSimpleHtmlString() {
		StatSummary summary = this.getSummary();
		return summary.toSimpleHtmlString();
	}
	
	public static void main(String[] args) {
		Stats stats = new Stats();
		System.out.println(stats.toJsonWithJvm());
	}
}
