package me.elvis;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Version:v1.0 (description: 搭建简单的web服务器  )
 */
public class HttpServer {

	public static final String WEB_ROOT =
			System.getProperty("user.dir") + File.separator + "webRoot";

	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	private boolean shutdown = false;

	public static void main(String[] args){
	    HttpServer httpServer = new HttpServer();
	    //服务器持续等待请求
	    httpServer.await();
	}

	public void await() {
		ServerSocket serverSocket  = null;
		int port = 8080;
		int maxQueueLength = 1;
		InetAddress address = null;
		try {
			address = InetAddress.getByName("127.0.0.1");

			//创建相应的服务器套接字
			serverSocket = new ServerSocket(port,maxQueueLength,address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//服务器持续监听连接
		while (!shutdown) {
			Socket socket = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;

			try {
				//当有连接建立时，服务器端套接字（监听套接字）创建一个新的套接字和客户端套接字进行三次握手
				socket = serverSocket.accept();
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				Request request = new Request(inputStream);
				request.parse();

				Response response = new Response(outputStream);
				response.setRequest(request);
				response.sendStaticResource();


				socket.close();
				shutdown = SHUTDOWN_COMMAND.equals(request.getUri());

			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}

	}
}
