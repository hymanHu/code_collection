package com.thornbird.framework.stats.stat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.thornbird.framework.stats.stat.Piegram.Item;

/**
 * stats 摘要类
 * @author hyman
 */
public class StatSummary {
	private Map<String, Long> counters = new HashMap<String, Long>();
	private Map<String, Long> jvm = new HashMap<String, Long>();
	private Map<String, Double> gauges = new HashMap<String, Double>();
	private Map<String, String> labels = new HashMap<String, String>();
	private Map<String, Map<String, Double>> distributions = new HashMap<String, Map<String, Double>>();
	private Map<String, List<Item>> piegrams = new HashMap<String, List<Item>>();
	
	public Map<String, Long> getCounters() {
		return counters;
	}

	public void setCounters(Map<String, Long> counters) {
		this.counters = counters;
	}

	public Map<String, Long> getJvm() {
		return jvm;
	}

	public void setJvm(Map<String, Long> jvm) {
		this.jvm = jvm;
	}

	public Map<String, Double> getGauges() {
		return gauges;
	}

	public void setGauges(Map<String, Double> gauges) {
		this.gauges = gauges;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public Map<String, Map<String, Double>> getDistributions() {
		return distributions;
	}

	public void setDistributions(Map<String, Map<String, Double>> distributions) {
		this.distributions = distributions;
	}

	public Map<String, List<Item>> getPiegrams() {
		return piegrams;
	}

	public void setPiegrams(Map<String, List<Item>> piegrams) {
		this.piegrams = piegrams;
	}

	public String toSimpleString() {
		StringBuffer sb = new StringBuffer();
		sb.append("counterSummary:---->\r\n");
		sb.append(String.format("%s%s", this.counters.toString(), "\r\n"));
		sb.append("jvmSummary:---->\r\n");
		sb.append(String.format("%s%s", this.jvm.toString(), "\r\n"));
		sb.append("gaugeSummary:---->\r\n");
		sb.append(String.format("%s%s", this.gauges.toString(), "\r\n"));
		sb.append("labelSummary:---->\r\n");
		sb.append(String.format("%s%s", this.labels.toString(), "\r\n"));
		sb.append("piegramSummary:---->\r\n");
		sb.append(String.format("%s%s", this.distributions.toString(), "\r\n"));
		sb.append("itemSummary:---->\r\n");
		sb.append(String.format("%s%s", this.piegrams.toString(), "\r\n"));
		return sb.toString();
	}
	
	public String toSimpleHtmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<strong>counterSummary:-----------></strong><br>");
		sb.append(String.format("%s%s", this.counters.toString(), "<br>"));
		sb.append("<strong>jvmSummary:-----------></strong><br>");
		sb.append(String.format("%s%s", this.jvm.toString(), "<br>"));
		sb.append("<strong>gaugeSummary:-----------></strong><br>");
		sb.append(String.format("%s%s", this.gauges.toString(), "<br>"));
		sb.append("<strong>labelSummary:-----------></strong><br>");
		sb.append(String.format("%s%s", this.labels.toString(), "<br>"));
		sb.append("<strong>piegramSummary:-----------></strong><br>");
		sb.append(String.format("%s%s", this.distributions.toString(), "<br>"));
		sb.append("<strong>itemSummary:-----------></strong><br>");
		sb.append(String.format("%s%s", this.piegrams.toString(), "<br>"));
		return sb.toString();
	}
	
	public String toJson() {
		return new GsonBuilder().create().toJson(this);
	}

	public static void main(String[] args) {
	}

}
