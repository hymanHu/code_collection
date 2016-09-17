package com.thornbird.framework.stats.server.impl;

import java.io.IOException;

import org.jboss.com.sun.net.httpserver.HttpExchange;

import com.thornbird.framework.stats.server.AbstractHttpHandler;
import com.thornbird.framework.stats.stat.impl.GlobalStats;

/**
 * 全局stats控制器，返回html字符串
 * @author hyman
 */
public class GlobalStatsTextHandler extends AbstractHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		GlobalStats.incr("total_stats_requests");
		render(GlobalStats.toSimpleHtmlString(), exchange);
	}

	public static void main(String[] args) {
	}
}
