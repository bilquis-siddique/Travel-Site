package mypkg;
import java.io.*;
import java.sql.*;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class DisplayCityPlaces extends HttpServlet {
private String databaseURL, username, password;
//@Override
/*public void init(ServletConfig config) throws ServletException {
   super.init(config);
   ServletContext context = config.getServletContext();
   databaseURL = context.getInitParameter("databaseURL");
   username = context.getInitParameter("username");
   password = context.getInitParameter("password");
}
*/
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
   response.setContentType("text/html;charset=UTF-8");
   PrintWriter out = response.getWriter();
   Connection conn = null;
   Statement  stmt = null;
   ResultSet  rset = null;
   String     sqlStr = null;
   try {
	   out.println("<html>");
	      out.println("<head><title>Query...</title></head>");
	      out.println("<body style=\"font-family:courier;\" bgcolor=\"#66cc99\" background =\"images/background.png\">");
	      out.println("<table>");
	      out.println("<tr><td><img src=\"images/cpbm.png\" height=\"158px\", width=\"300px\"></td>");
	      out.println("<td><br><br><h1></h1></td></tr>");
	      out.println("</table>");
      // Retrieve and process request parameters: id(s), cust_name, cust_email, cust_phone
      //String[] ids = request.getParameterValues("id");  // Possibly more than one values
      /*String firstName = request.getParameter("country");
      boolean hasFirstName = firstName != null && ((firstName = firstName.trim()).length() > 0);
      */
      String city1 = request.getParameter("city_name").trim();
      boolean hasCity1 = city1 != null && ((city1 = city1.trim()).length() > 0);
     // Validate inputs
      if (!hasCity1) {
         out.println("<h3>Please Enter City Name!</h3>");
      }/* else if (!hasLastName) {
         out.println("<h3>Please Enter Your Last Name!</h3>");
	    }*/ else {
         // Display the names (arranged in a table)
         out.println("<table>");
         //out.println("<tr><td>First Name:</td><td>" + firstName + "</td></tr>");
         out.println("<tr><td><h3>City:</h3></td><td><h3>" + city1 + "</h3></td></tr></table>");
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TravelSites", "root", "");
         stmt = conn.createStatement();
         // Print the book(s) ordered in a table
         //out.println("<br />");
         //out.println("<table width='800px' border='1' cellpadding='6'>");
         out.println("<table align = 'center' width='1100px'>");
         sqlStr = "SELECT * FROM placeOfCity WHERE cityName = '" + city1 +"'";
            //System.out.println(sqlStr);  // for debugging
            rset = stmt.executeQuery(sqlStr);
            // Expect only one row in ResultSet
            rset.next();
            String Place1Name = rset.getString("place1Name");
            String Place1Url = rset.getString("place1Url");
            String Place1Description = rset.getString("place1Description");
            
            String Place2Name = rset.getString("place2Name");
            String Place2Url = rset.getString("place2Url");
            String Place2Description = rset.getString("place2Description");
            out.println("<tr><th>"+ Place1Name+"</th><th></th><th>"+Place2Name+"</th></tr>");
            
               // Display this city places
               out.println("<tr>");
               //out.println("<td>" + city1 + " " + "</td>");
               out.println("<td width='500px' align = 'center'> <img src='" + Place1Url + "' height='320px', width='500px'> </td>");
               out.println("<td width='100px' ></td>");
               out.println("<td  width='500px' align = 'center'> <img src='" + Place2Url + "' height='320px', width='500px'> </td></tr>");
               
               out.println("<tr>");
              // out.println("<td>" + city1 + " " + "</td>");
               out.println("<td bgcolor=\"#FFFFFF\" width='500px' > " +Place1Description+"</td>");
               out.println("<td width='100px' ></td>");
               out.println("<td bgcolor=\"#FFFFFF\" width='500px' > " +Place2Description+"</td></tr>");
              
}
            out.println("</table>");
            //out.println("<br>");
            out.println("<br>");
            out.println("<table>");
            out.println("<tr><td><A HREF='EnterCountry' method = 'post'>Back to Search for Travel Sites</A></td></tr>");
            out.println("</table>");
            out.println("<br>");
            //out.println("<br>");
            //out.println("<br>");
            out.println("</body></html>");
         }

         catch (SQLException ex) {
         out.println("<h3>Service not available. Please try again later!</h3></body></html>");
         Logger.getLogger(DisplayCityPlaces.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
         out.close();
         try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
         } catch (SQLException ex) {
            Logger.getLogger(DisplayCityPlaces.class.getName()).log(Level.SEVERE, null, ex);
         }
} }
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      doGet(request, response);
}
}
