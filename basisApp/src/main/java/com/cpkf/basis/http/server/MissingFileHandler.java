package com.cpkf.basis.http.server;

import java.io.IOException;

import org.jboss.com.sun.net.httpserver.HttpExchange;

public class MissingFileHandler extends AbstractHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		render("no such file.", exchange, 404);
	}
}
