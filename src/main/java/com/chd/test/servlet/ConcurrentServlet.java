package com.chd.test.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConcurrentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ConcurrentServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String system = request.getParameter("system");
		String year = request.getParameter("year");
		System.err.println("type:"+type+"system:"+system+"year:"+year);
	}

}
