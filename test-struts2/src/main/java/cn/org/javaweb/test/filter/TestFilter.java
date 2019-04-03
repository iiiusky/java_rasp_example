package cn.org.javaweb.test.filter;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yz on 2016/11/16.
 */
public class TestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		out.println("-----------------Filter-----------------------");
		out.println(request.getParameter("id"));
		out.println("-----------------Filter-----------------------");
		out.flush();
		out.close();
	}

	@Override
	public void destroy() {

	}

}
