package me.elvis.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Version:v1.0 (description: Servlet容器测试类 )
 */
public class PrimitiveServlet implements Servlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		System.out.println("servlet init()");
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	/**
	 * 将request和response作为两个入参
	 * @param servletRequest
	 * @param servletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse)
			throws ServletException, IOException {
		System.out.println("servlet service()");
		PrintWriter out = servletResponse.getWriter();
		out.println("Hello,Roses are red!");
		out.print("Violets are blue");
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {
		System.out.println("servlet destroy()");
	}
}
