import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchAtta")
public class SearchAtta extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchAtta() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Registration Verification";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#ffffff\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionAtta.getDBConnection();
         connection = DBConnectionAtta.connection;

            String selectSQL = "SELECT * FROM Project WHERE PHONE LIKE ?";
            String theEmail = "%" + keyword;
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theEmail);
            boolean resultFound = true;
         ResultSet rs = preparedStatement.executeQuery();

         
         while (rs.next()) {
            String firstName = rs.getString("firstName").trim();
            String lastName = rs.getString("lastName").trim();
            String email = rs.getString("email").trim();
            String phone = rs.getString("phone").trim();

            if (phone.contains(keyword)) {
               out.println("Full Name: " + firstName + " " + lastName + ", ");
               out.println("Phone: " + phone + ", ");
               out.println("Email: " + email + "<br>");
               out.println("<br>You are on the list!<br><br>");
               resultFound = false;
            }

         }
         
         
         if(resultFound)
         {
        	 out.println("No record of patient<br><br>"); 
         }
         
         out.println("<a href=/CSCI4830-ProjectAtta/search_atta.html>Registration Verification</a> <br>");
         out.println("<a href=/CSCI4830-ProjectAtta/insert_atta.html>Sign-up Sheet</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
