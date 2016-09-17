package com.thornBird.think.server.datasourceServer.socket.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornBird.think.server.datasourceServer.socket.ISocketSession;

/**
 * socket操作类 memcached默认端口：命令：'stats' redis默认端口：命令：'config get maxmemory','info'
 * 
 * @author human
 */
public class SocketSessionImpl implements ISocketSession {

	private static final Logger logger = LoggerFactory.getLogger(SocketSessionImpl.class);
	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final int INPUT_BUFFER_SIZE = 4096;

	private String host;
	private int port;
	private Socket socket;

	public SocketSessionImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void open() throws IOException {
		socket = new Socket();
		logger.debug(String.format("%s%s%s%s", "Connect to ", host, ":", port));
		socket.connect(new InetSocketAddress(host, port));
	}

	public String execute(String command) throws IOException {
		if (command == null || command.equals("")) {
			throw new IllegalArgumentException("Command cannot be null.");
		}

		// send command to output
		byte[] cmd = (command + "\r\n").getBytes(CHARSET);
		socket.getOutputStream().write(cmd);
		socket.getOutputStream().flush();
		
		// read response from input
		byte[] resp = new byte[INPUT_BUFFER_SIZE];
		socket.getInputStream().read(resp);
		String response = new String(resp, CHARSET);
		logger.debug(String.format("%s%s", "Response: ", response));

		return response;
	}

	public void close() throws IOException {
		logger.debug(String.format("%s%s%s%s", "Close ", host, ":", port));
		if (socket != null) {
			socket.close();
			socket = null;
		}
	}

	public static void main(String[] args) {
		// SocketSessionImpl memcacheSocket = new SocketSessionImpl("localhost",
		// 11211);
		SocketSessionImpl redgraphSocket = new SocketSessionImpl("172.17.20.73", 6120);

		try {
			// memcacheSocket.open();
			// memcacheSocket.execute("stats");

			redgraphSocket.open();
			System.out.println(redgraphSocket.execute("info"));
			System.out.println(redgraphSocket.execute("config get maxmemory"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// memcacheSocket.clone();
				redgraphSocket.clone();
			} catch (Exception e2) {
			}
		}
	}
}
