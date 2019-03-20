package mypkg;
import java.io.*;
import java.sql.*;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class EnterCountry extends HttpServlet { private String databaseURL, username, password;

@Override
   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      ServletContext context = config.getServletContext();
      databaseURL = context.getInitParameter("databaseURL");
      username = context.getInitParameter("username");
      password = context.getInitParameter("password");
      }

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
   response.setContentType("text/html;charset=UTF-8");
   PrintWriter out = response.getWriter();
   Connection conn = null;
   Statement stmt = null;
   out.println("<html>");
   out.println("<head><title>Query...</title>");
   out.println("<link 'rel=stylesheet' type='text/css' href='css/style.css' /> </head>");
   out.println("<body style=\"font-family:courier;\" bgcolor=\"#66cc99\" background =\"images/background.png\">");
   out.println("<table>");
   out.println("<tr><td><img src=\"images/cpbm.png\" height=\"158px\", width=\"300px\"></td>");
   out.println("<td><br><br><h1></h1></td></tr>");
   out.println("</table>");
try {
            // Print the result in an HTML form inside a table
            out.println("<form method='get' action='DisplayCity'>");
            // Ask for name, email and phone using text fields (arranged in a table)
            out.println("<table align='center' cellpadding='10'>");
            //out.println("<tr><td>Enter your First Name:</td>");
            //out.println("<td><input type='text' name='first_name' /></td></tr>");
            out.println("<tr><td >Enter Country Name:</td>");
            out.println("<td><input type='text' name='country_name' /></td></tr>");
               // Submit and reset buttons
            out.println("<table align='center' cellpadding='10'>");
            out.println("<tr><td><input type='submit' value='Display Cities of the Country' /></td>");
            out.println("<td><input type='reset' value='CLEAR' /></form><?td><tr>");
            out.println("</body></html>");
            }


catch (Exception ex) {
         out.println("<h3>Service not available. Please try again later!</h3></body></html>");
         Logger.getLogger(EnterCountry.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
         out.close();
         try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
         } catch (SQLException ex) {
            Logger.getLogger(EnterCountry.class.getName()).log(Level.SEVERE, null, ex);
         }
}
}
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      doGet(request, response);
}
}
