package com.chd.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chd.test.compiler.JavaCompilerTest;
import com.chd.test.utils.StringUtils;

public class CompilerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CompilerServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String source = request.getParameter("source");
		String className = request.getParameter("className");
		String methodName = request.getParameter("methodName");
		PrintWriter writer = response.getWriter();
		if(StringUtils.isNotEmpty(source) && StringUtils.isNotEmpty(className) && StringUtils.isNotEmpty(methodName)) {
			JavaCompilerTest test = new JavaCompilerTest();
			try {
				Object exec = test.exec(source, className, methodName);
				writer.write(exec.toString());
			} catch (Exception e) {
				e.printStackTrace();
				String message = e.getMessage();
				writer.write(message);
			}
		}else {
			writer.write("缺少必要参数：soruce(源文件),className(类的全限定名),methodName(调用的方法)");
		}
	}

}
