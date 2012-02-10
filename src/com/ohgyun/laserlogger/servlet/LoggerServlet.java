package com.ohgyun.laserlogger.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ohgyun.laserlogger.model.Channel;

/**
 * 선택한 채널로 로깅한다.
 * 전달받은 로그 데이터를 뷰어 페이지로 푸시하는 역할을 한다.
 */
@SuppressWarnings("serial")
public class LoggerServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		boolean success = pushLog(req);
		if (success) {
			responseBlankImage(resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	private boolean pushLog(HttpServletRequest req) {
		Channel channel = new Channel(req.getParameter("channel"));
		String data = req.getParameter("data");
		return channel.trigger(data);
	}

	private void responseBlankImage(HttpServletResponse resp)
			throws IOException {
		// Get the absolute path of the image
		ServletContext sc = getServletContext();
		String filename = sc.getRealPath("img/blank.gif");

		// Set content type
		resp.setContentType("image/gif");

		// Set content size
		File file = new File(filename);
		resp.setContentLength((int) file.length());

		// Open the file and output streams
		FileInputStream in = new FileInputStream(file);
		OutputStream out = resp.getOutputStream();

		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		in.close();
		out.close();
	}	
}
