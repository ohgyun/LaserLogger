package com.ohgyun.laserlogger.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.ohgyun.laserlogger.model.Channel;

/**
 * 뷰어 페이지에서 로거가 포함된 페이지로 커맨드를 보낸다.
 */
@SuppressWarnings("serial")
public class CommandServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		Channel channel = new Channel(req.getParameter("channel"));
		String data = req.getParameter("data");
		
		boolean success = channel.trigger(data);
		
		resp.setContentType("text/plain");
		resp.getWriter().write(Boolean.toString(success));
	}
}
