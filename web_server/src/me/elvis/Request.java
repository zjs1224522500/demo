package me.elvis;

import java.io.IOException;
import java.io.InputStream;

/**
 * Version:v1.0 (description:  )
 */
public class Request {

	private InputStream inputStream;

	private String uri;

	public Request(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void parse() {
		StringBuffer request = new StringBuffer(2048);
		int bufferLength;
		byte[] buffer = new byte[2048];
		try {
			bufferLength = inputStream.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			bufferLength = -1;
		}

		for (int j = 0; j < bufferLength; j++) {
			request.append((char) buffer[j]);
		}

		System.out.println(request.toString());
		uri = parseUri(request.toString());
	}

	private String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(' ');
		if (-1 != index1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1) {
				return requestString.substring(index1 + 1, index2);
			}
		}
		return null;
	}

	public String getUri() {
		return uri;
	}
}
