package com.thornBird.base;

import java.util.UUID;
import java.util.stream.IntStream;

public class IdTest {
	//////////////////////////////////////////////////////////////////////////////
	// SnowflakeId
	/** 开始时间截 (2015-01-01) */
	private final long twepoch = 1420041600000L;
	/** 机器id所占的位数 */
	private final long workerIdBits = 5L;
	/** 数据标识id所占的位数 */
	private final long datacenterIdBits = 5L;
	/** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	/** 支持的最大数据标识id，结果是31 */
	private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	/** 序列在id中占的位数 */
	private final long sequenceBits = 12L;
	/** 机器ID向左移12位 */
	private final long workerIdShift = sequenceBits;
	/** 数据标识id向左移17位(12+5) */
	private final long datacenterIdShift = sequenceBits + workerIdBits;
	/** 时间截向左移22位(5+5+12) */
	private final long timestampLeftShift = sequenceBits + workerIdBits
			+ datacenterIdBits;
	/** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	/** 工作机器ID(0~31) */
	private long workerId;
	/** 数据中心ID(0~31) */
	private long datacenterId;
	/** 毫秒内序列(0~4095) */
	private long sequence = 0L;
	/** 上次生成ID的时间截 */
	private long lastTimestamp = -1L;

	/**
	 * @param workerId     工作ID (0~31)
	 * @param datacenterId 数据中心ID (0~31)
	 */
	public IdTest(long workerId, long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"worker Id can't be greater than %d or less than 0",
					maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format(
					"datacenter Id can't be greater than %d or less than 0",
					maxDatacenterId));
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();

		// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds",
					lastTimestamp - timestamp));
		}

		// 如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			// 毫秒内序列溢出
			if (sequence == 0) {
				// 阻塞到下一个毫秒,获得新的时间戳
				timestamp = tilNextMillis(lastTimestamp);
			}
		}
		// 时间戳改变，毫秒内序列重置
		else {
			sequence = 0L;
		}

		// 上次生成ID的时间截
		lastTimestamp = timestamp;

		// 移位并通过或运算拼到一起组成64位的ID
		return ((timestamp - twepoch) << timestampLeftShift) //
				| (datacenterId << datacenterIdShift) //
				| (workerId << workerIdShift) //
				| sequence;
	}

	/**
	 * a阻塞到下一个毫秒，直到获得新的时间戳
	 * @param lastTimestamp		上次生成ID的时间截
	 * @return 当前时间戳
	 */
	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis();
	}

	//////////////////////////////////////////////////////////////////////////////
	// 根据Server id和当前时间进行位运算，不重复唯一id
	private static long INFOID_FLAG = 1260000000000L;
	protected static int SERVER_ID = 1;

	public synchronized long nextId2() throws Exception {
		if (SERVER_ID <= 0) {
			throw new Exception("server id is error,please check config file!");
		}
		long infoId = System.currentTimeMillis() - INFOID_FLAG;
		infoId = (infoId << 7) | SERVER_ID;
		Thread.sleep(1);
		return infoId;
	}

	public static void main(String[] args) {
		IdTest idTest = new IdTest(0, 0);

		// uuid，11a886f3-7e09-4804-9281-53192aa238d8
		IntStream.range(0, 9).forEach(item -> {
			System.out.println(UUID.randomUUID().toString());
		});

		// 不重复唯一id
		IntStream.range(0, 9).forEach(item -> {
			try {
				System.out.println(idTest.nextId2());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// 全局唯一，自增id
		IntStream.range(0, 9).forEach(item -> {
			System.out.println(idTest.nextId());
		});
	}
}
