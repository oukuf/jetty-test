package jp.co.golorp.jettyjartest;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/outerservlet")
public class OuterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().println("<a href=\"./\">index.jsp</a>");
		res.getWriter().println("<h2>" + this.getClass().getSimpleName() + "</h2>");
	}

}
