package mypkg;
import java.io.*;
import java.sql.*;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class DisplayCity extends HttpServlet {
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
      String country = request.getParameter("country_name").trim();
      boolean hasCountry = country != null && ((country = country.trim()).length() > 0);
     // Validate inputs
      if (!hasCountry) {
         out.println("<h3>Please Enter Country Name!</h3>");
      }/* else if (!hasLastName) {
         out.println("<h3>Please Enter Your Last Name!</h3>");
	    }*/ else {
         // Display the names (arranged in a table)
	     out.println("<div align = \"center\">");
         out.println("<table>");
         //out.println("<tr><td>First Name:</td><td>" + firstName + "</td></tr>");
         out.println("<tr><td align = 'center'>You have selected " + country + "</td>");
         out.println("<tr><td align = 'center'>Below are some cities of it</td>");
         out.println("<tr><td align = 'center'>Please click on the city name to view some places of it</td></tr></table>");
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TravelSites", "root", "");
         stmt = conn.createStatement();
       
         out.println("<br />");
         out.println("<table border='1' cellpadding='6'>");
         //out.println("<tr><th>Country</th><th>City 1</th><th>City 2</th></tr>");
         sqlStr = "SELECT country.countryName, city.city1, city.city2 FROM country LEFT JOIN city ON country.countryCode = city.countryCode"
         		+ " WHERE countryName = '" + country +"'";
            //System.out.println(sqlStr);  // for debugging
            rset = stmt.executeQuery(sqlStr);
            // Expect only one row in ResultSet
            rset.next();
            String city1 = rset.getString("city1");
            String city2 = rset.getString("city2");
            
            out.println("<form method='get' action='DisplayCityPlaces'>");
            out.println("<input type='radio' name = 'city_name' value='"+city1+"' />"+city1);
            out.println("<input type='radio' name = 'city_name' value='"+city2+"' />"+city2);
            out.println("<input type='submit' value='Sumbit' /></form>");
            
               //out.println("<tr>");
               //out.println("<td>" + country  + "</td>");
               //out.println("<td> <img src='" + city1 + "' height='120px', width='150px'> </td>");
               //out.println("<td><A HREF='DisplayCityPlaces' method='post'>" + city1+ "</A></td>");
               //out.println("<td><A HREF='DisplayCityPlaces2' method='post'>" +city2+ "</A></td></tr>");
// out.println("<A HREF='DisplayCityPlaces' method='post'>Show Places of" +city1+ "</A>");
 		//out.println("<A HREF='EnterCountry' method = 'post'>Enter a country name</A>");       
            
}
            out.println("</table>");
            
            out.println("</div>");
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
