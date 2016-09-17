package com.cpkf.basis.http.server;

import java.io.IOException;

import org.jboss.com.sun.net.httpserver.HttpExchange;

/**
 * 文件夹控制器，传入文件夹的classpath
 * e.g. "http://localhost:9999/testFolder/test.js"
 * new FolderResourceHandler("/testFolder")
 * @author hyman
 */
public class FolderResourceHandler extends AbstractHttpHandler {
	private String staticPathe;

	public FolderResourceHandler(String staticPathe) {
		this.staticPathe = staticPathe;
	}
	
	public String getRelativePath(String requestPath) {
		if (requestPath.startsWith(staticPathe)) {
			return requestPath.substring(staticPathe.length() + 1);
		} else {
			return requestPath;
		}
	}
	
	public String buildPath(String relativePath) {
		return String.format("%s%s%s", staticPathe, "/", relativePath);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String requestPath = exchange.getRequestURI().getPath();
		String relativePath = getRelativePath(requestPath);
		String contentType = "";
		if (relativePath.endsWith(".js")) {
			contentType = "text/javascript";
		} else if (relativePath.endsWith(".html")) {
			contentType = "text/html";
		} else if (relativePath.endsWith(".css")) {
			contentType = "text/css";
		} else {
			contentType = "application/unknown";
		}
		render(loadResource(buildPath(relativePath)), exchange, 200, contentType);
	}

	public static void main(String[] args) {
	}

}
