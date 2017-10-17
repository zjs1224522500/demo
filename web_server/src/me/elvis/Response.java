package me.elvis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
  HTTP Response = Status-Line
    *(( general-header | response-header | entity-header ) CRLF)
    CRLF
    [ message-body ]
    Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
*/
public class Response {

	private static final int BUFFER_SIZE = 1024;

	private Request request;

	private OutputStream output;

	public Response(OutputStream output) {
		this.output = output;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			//服务端根据路径和统一资源定位符创建对应的文件对象
			File file = new File(HttpServer.WEB_ROOT, request.getUri());
			if (file.exists()) {
				fis = new FileInputStream(file);
				int bytesArrayLength = fis.read(bytes, 0, BUFFER_SIZE);

				//读到文件流末尾时，返回值为-1
				//循环读取文件输入流中的数据，并写入到输出流
				while (bytesArrayLength != -1) {
					output.write(bytes, 0, bytesArrayLength);
					bytesArrayLength = fis.read(bytes, 0, BUFFER_SIZE);
				}
			} else {
				// file not found
				String errorMessage =
						"HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
								+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.out.println(e.toString());
		} finally {
			if (fis != null)
				fis.close();
		}
	}
}
