package com.thornbird.framework.stats.server.impl;

import java.io.IOException;

import org.jboss.com.sun.net.httpserver.HttpExchange;

import com.thornbird.framework.stats.server.AbstractHttpHandler;
import com.thornbird.framework.stats.stat.impl.DefaultStats;
import com.thornbird.framework.stats.stat.impl.GlobalStats;

/**
 * 清除stats控制器
 * @author hyman
 */
public class ClearStatsHandler extends AbstractHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		boolean globalStatsClear = GlobalStats.clear();
		boolean defaultStatsClear = DefaultStats.clear();
		render(String.format("%s%s%s", "{\"result\":", (globalStatsClear && defaultStatsClear), "}"),
				exchange, 200, "application/json");
	}

	public static void main(String[] args) {
	}

}
