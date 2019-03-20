
package mypkg;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

/**
 * This servlet deals with the form login2.html. It permits to demonstrate how
 * to deal with SQL injection and security issues (e.g. dbms used, page
 * accessibles without being logged in, messages returned by exceptions...)
 *
 * Flow of activities:
 * login2.html ->
 * If login/password ok -> Login2
 *    In Login2 -> Operation or Logout
 *                 In Operation -> Logout
 * Else -> LoginTryAgain
 * Login2 and Operation cannot be accessed if the user is not logged in
 *
**/

public class Login2 extends HttpServlet {

	PrintWriter out;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html");

		ServletOutputStream out = res.getOutputStream();
		out.println("<html>");
		out.println("<head><title>Login2</title>");
		out.println("<link 'rel=stylesheet' type='text/css' href='css/style.css' /> </head>");
		out.println("<body>");
		out.println("Access failure: You are not logged in! (Login2)");
		out.println("</body></html>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		boolean loginSuccess = false;

		// Used to set the MIME type of the http response - here text/html
		res.setContentType("text/html");

		// Returns a ServletOutputStream suitable for writing binary data
		// in the response
		out = res.getWriter();
		String cssPath = req.getContextPath();

		// Use of sessions to deal with accessing servlets without being logged in
		// Get session object, create one if necessary
		HttpSession session = req.getSession(true);

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Process Login2...</title>");
		//out.println("<link 'rel=stylesheet' type='text/css' href='TravelSites/css/style.css' />");
		   out.println("<head><title>Login Success</title></head>");
		   out.println("<body style=\"font-family:courier;\" bgcolor=\"#66cc99\" background =\"images/background.png\"> ");
		   out.println("<table>");
		   out.println("<tr><td><img src=\"images/cpbm.png\" height=\"158px\", width=\"300px\"></td>");
		   out.println("<td><br><br><h1>  </h1></td></tr>");
		   out.println("</table>");

		String login = req.getParameter("login");
		String password = req.getParameter("password");
		try {
			loginSuccess = SearchTable1(login, password);
		} catch (Exception e) {
		}

		if (loginSuccess) {
			// Add data login to an HttpSession object
			session.setAttribute("login", login);
			out.println("<div align = \"center\">");
			out.println("<br>");
			out.println("Welcome " + login + "!");
			//out.println(cssPath);
			out.println("<br>");
			out.println("<br>");
			out.println("Your are now logged in!!!");
			out.println("<br>");
			out.println("<br>");
			out.println("<A HREF='EnterCountry' method = 'post'>Search for Travel Sites</A>");
			out.println("<br>");
			out.println("<br>");
			out.println("<form action='Logout' method='post'>");
			out.println("<input type='submit' name='submit' value='logout'>");
			out.println("</form>");
			out.println("<br>");
			out.println("<br>");
			out.println("</div>");
		} else {
			// Redirect to the login again page in case of a wrong
			// login/password
			if (session != null) {
				res.sendRedirect("LoginTryAgain");
			}
			return;
		}

		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	// Use of PreparedStatements to deal with SQL injection
	private final boolean SearchTable1(String login, String password)
			throws SQLException, IOException, ClassNotFoundException {
		// Load the MySQL driver
		Class.forName("com.mysql.jdbc.Driver");
		// Connect to the database
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/login","root","");
		PreparedStatement pstmt = conn
				.prepareStatement("select * from login1table where login = ? and password = ?");
		pstmt.setString(1, login);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		//out.println("<br>");
		//out.println("Query: " + pstmt.toString());
	  //out.println("<br>");
		//out.println("<br>");
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	private final boolean SearchTable(String login, String password)
			throws SQLException, IOException, ClassNotFoundException {
		// Load the MySQL driver
		Class.forName("com.mysql.jdbc.Driver");
		// Connect to the database
		/*Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/login"
						+ "user=root&password=12345678");*/
		Connection conn = DriverManager
								.getConnection("jdbc:mysql://localhost:3306/login","root","");
		Statement stmt = conn.createStatement();
		// Do a select to check if the login/password match in the table
		String query = "select * from login1table where login = '" + login
				+ "' and password = '" + password + "'";
		//out.println("<br>");
	  //out.println("Query: " + query);
		//out.println("<br>");
		//out.println("<br>");
		ResultSet rs = stmt.executeQuery(query);
		if (rs.next()) {
			return !containsQuote(login, password);
		}
		return false;
	}

	// Method called by SearchTable. It checks that there are no quote in the login/password
	private final boolean containsQuote(String login, String password) {
		return (login.indexOf("'") >= 0 || password.indexOf("'") >= 0);
	}

	public String getServletInfo() {
		return "Login 2 Servlet";
	}
}

// create table login1table(login varchar(10), password varchar(10));
// insert into login1table(login, password) values ('a','a');
// insert into login1table(login, password) values ('christelle','christelle');
// login: a password: ' OR 1=1 -- // In mysql there is a space after --
// login: c' OR 1=1 -- password: anything
// login: a password: ' OR 1=1 #test // # is also for comments in mysql
// login: a' /*!32302 OR 1=1 */ -- password: anything OR 1=1 is executed
// only if the version of mysql is higher than 32302

// Did not work
// login: CHAR(0x27) OR 1=1 -- password: a
// login: a' OR true ; DELETE FROM login1table where true; -- password: ''
