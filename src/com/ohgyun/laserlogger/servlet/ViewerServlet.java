package com.ohgyun.laserlogger.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * 뷰어 페이지로 이동한다.
 */
@SuppressWarnings("serial")
public class ViewerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		getServletContext().getRequestDispatcher("/viewer.html").forward(req, resp);
	}
}
