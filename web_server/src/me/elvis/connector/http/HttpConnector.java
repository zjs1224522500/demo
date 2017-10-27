package me.elvis.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Version:v1.0 (description:  )
 */
public class HttpConnector implements Runnable {

	boolean stopped;
	private String scheme = "http";

	/**
	 * 返回请求协议
	 * @return scheme
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * 新开一个线程等待客户端发起连接
	 */
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket =  new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (!stopped) {
			// Accept the next incoming connection from the server socket
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			}
			catch (Exception e) {
				continue;
			}
			// Hand this socket off to an HttpProcessor
			/**
			 * 为每一个请求创建相应的处理器，利用connector进行初始化
			 * 并传入请求对应的套接字
			 */
			HttpProcessor processor = new HttpProcessor(this);
			processor.process(socket);
		}
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
}
