package mypkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {

	PrintWriter out;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html");

		ServletOutputStream out = res.getOutputStream();
		out.println("<html>");
		out.println("<head><title>Logout</title></head>");
		out.println("<body style=\"font-family:courier;\" bgcolor=\"#66cc99\" background =\"images/background.png\"> ");
		out.println("<h1>Access failure: You are not logged in!</h1>");
		out.println("</body></html>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// Used to set the MIME type of the http response - here text/html
		res.setContentType("text/html");

		// Returns a ServletOutputStream suitable for writing binary data
		// in the response.
		out = res.getWriter();

		out.println("<html>");
		out.println("<head><title>Logout</title></head>");
		out.println("<body style=\"font-family:courier;\" bgcolor=\"#66cc99\" background =\"images/background.png\"> ");

		// Get session object, create one if necessary
		HttpSession session = req.getSession(true);

		// Is the user already logged in?
		Object login = session.getAttribute("login");

		if (login == null) {
			out.println("You cannot log out because you are not logged in!");
			out.println("If you want to log in, please click <a href=\"login2.html\">here</A>!");
		} else {
			out.println("<table>");
			out.println("<tr><td><img src=\"images/cpbm.png\" height=\"158px\", width=\"300px\"></td>");
			out.println("<td><br><br><h1>  </h1></td></tr>");
			out.println("</table>");
			
			out.println("<div align = 'center'>");
			out.println("You are now logged out!");
			out.println("<br>");
			out.println("<br>");
			out.println("If you want to log in again, please click <a href=\"login2.html\">here</A>!");
			out.println("</div>");
			session.invalidate();
		}

		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public String getServletInfo() {
		return "Logout Servlet";
	}
}
