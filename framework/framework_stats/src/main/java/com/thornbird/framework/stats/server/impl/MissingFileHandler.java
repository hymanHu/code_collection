package com.thornbird.framework.stats.server.impl;

import java.io.IOException;

import org.jboss.com.sun.net.httpserver.HttpExchange;

import com.thornbird.framework.stats.server.AbstractHttpHandler;

public class MissingFileHandler extends AbstractHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		render("no such file.", exchange, 404);
	}
}
