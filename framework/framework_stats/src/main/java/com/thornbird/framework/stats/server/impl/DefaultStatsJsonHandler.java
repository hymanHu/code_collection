package com.thornbird.framework.stats.server.impl;

import java.io.IOException;

import org.jboss.com.sun.net.httpserver.HttpExchange;

import com.thornbird.framework.stats.server.AbstractHttpHandler;
import com.thornbird.framework.stats.stat.impl.DefaultStats;

/**
 * 默认stats控制器，返回json字符串
 * @author hyman
 */
public class DefaultStatsJsonHandler extends AbstractHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		DefaultStats.incr("total_stats_requests");
		String callbackValue = "";
		String uri = exchange.getRequestURI().toString();
		String paramString = uri.substring(uri.indexOf("?") + 1, uri.length());
		String[] params = paramString.split("&");
		for (String param : params) {
			String[] paramKeyAndValue = param.split("=");
			if (paramKeyAndValue[0].trim().equals("callback")) {
				callbackValue = paramKeyAndValue[1];
			}
		}
		
		if (callbackValue == null || callbackValue.length() == 0) {
			render(DefaultStats.toJson(), exchange, 200, "application/json");
		} else {
			render(String.format("%s%s%s%s", callbackValue, "(", DefaultStats.toJson(), ")"),
					exchange, 200, "application/json");
		}
	}

	public static void main(String[] args) {
	}

}
