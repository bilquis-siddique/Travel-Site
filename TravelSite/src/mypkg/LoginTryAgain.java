package mypkg;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class LoginTryAgain extends HttpServlet {

	PrintWriter out;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processLoginAgain(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processLoginAgain(req, res);
	}

	/**
	 * @param res
	 * @throws IOException
	 */
	private void processLoginAgain(HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		// Get session object, create one if necessary
		HttpSession session = req.getSession(true);

		// Does the session indicate that this user is already logged in?
		Object login = session.getAttribute("login");

		// Used to set the MIME type of the http response - here text/html
		res.setContentType("text/html");

		if (login == null) {
			// Returns a ServletOutputStream suitable for writing binary data
			// in the response
			out = res.getWriter();

			out.println("<html>");
			out.println("<head><title>Login2 try again...</title></head>");
			out.println("<body style=\"font-family:courier;\" bgcolor=\"#66cc99\" background =\"images/background.png\"> ");
			out.println("<table>");
			out.println("<tr><td><img src=\"images/cpbm.png\" height=\"158px\", width=\"300px\"></td>");
			out.println("<td><br><br><h1>  </h1></td></tr>");
			out.println("</table>");
			
			
			out.println("<form action='Login2' method='post'>");
			out.println("Login information incorrect! Try again!");
			out.println("<br>");
			out.println("<br>");
			out.println("Enter your login: <input type=\"text\" name=\"login\">");
			out.println("<br>");
			out.println("<br>");
			out.println("Enter your password: <input type=\"password\" name=\"password\">");
			out.println("<br>");
			out.println("<br>");
			out.println("<input type=\"submit\" name=\"submit\" value=\"submit\">");
			out.println("</form>");
		} else {
			out.println("You are already logged in!");
		}
		out.println("</body>");

		out.println("</html>");
		out.close();
	}

	public String getServletInfo() {
		return "Login Try Again Servlet";
	}
}
