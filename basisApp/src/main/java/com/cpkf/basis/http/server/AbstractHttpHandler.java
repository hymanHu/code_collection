package com.cpkf.basis.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.jboss.com.sun.net.httpserver.HttpExchange;
import org.jboss.com.sun.net.httpserver.HttpHandler;

/**
 * http控制器
 * @author hyman
 */
public abstract class AbstractHttpHandler implements HttpHandler {

	public abstract void handle(HttpExchange arg0) throws IOException;

	public void render(String body, HttpExchange exchange, Integer code, String contentType)
			throws IOException {
		OutputStream output = exchange.getResponseBody();
		exchange.getResponseHeaders().set("Content-Type", contentType);
		exchange.getResponseHeaders().set("X-Ostrich-Version", ".0.1snapshot");
		byte[] data = body.getBytes();
		exchange.sendResponseHeaders(code, data.length);
		output.write(data);
		output.flush();
		output.close();
		exchange.close();
	}

	public void render(String body, HttpExchange exchange, Integer code) throws IOException {
		render(body, exchange, code, "text/html");
	}

	public void render(String body, HttpExchange exchange) throws IOException {
		render(body, exchange, 200);
	}

	public String loadResource(String filePath) {
		System.out.println("Loading resource from file: " + filePath);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			InputStream in = this.getClass().getResourceAsStream(filePath);
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(String.format("%s%s", line, "\r\n"));
			}
		} catch (Exception e) {
			sb.append(String.format("%s%s", "Unable to load Resource from Classpath: ", filePath));
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
			}
		}
		return sb == null ? "" : sb.toString();
	}
	
}
