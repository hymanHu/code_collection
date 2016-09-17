package com.cpkf.basis.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * socket操作类
 * memcached默认端口：命令：'stats'
 * redis默认端口：命令：'config get maxmemory','info'
 * @author human
 */
public class SocketSessionImpl{
	
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
		socket.connect(new InetSocketAddress(host, port));
	}

	public String execute(String command) throws IOException {
		if (command == null || command.equals("")) {
			throw new IllegalArgumentException("Command cannot be null.");
		}
		
		// send command to output
		byte[] cmd = (command + "/r/n").getBytes(CHARSET);
		socket.getOutputStream().write(cmd);
		socket.getOutputStream().flush();
		
		// read response from input
		byte[] resp = new byte[INPUT_BUFFER_SIZE];
		socket.getInputStream().read(resp);
		String response = new String(resp, CHARSET);
		
		return response;
	}

	public void close() throws IOException {
		if (socket != null) {
			socket.close();
			socket = null;
		}
	}

	public static void main(String[] args) {
		SocketSessionImpl memcacheSocket = new SocketSessionImpl("localhost", 11211);
//		SocketSessionImpl redisSocket = new SocketSessionImpl("localhost", 6120);
		
		try {
			memcacheSocket.open();
			memcacheSocket.execute("stats");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				memcacheSocket.clone();
			} catch (Exception e2) {
			}
		}
	}
}
