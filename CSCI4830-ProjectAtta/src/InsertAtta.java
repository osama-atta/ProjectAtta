
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertAtta")
public class InsertAtta extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertAtta() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String firstName = request.getParameter("firstName");
      String lastName = request.getParameter("lastName");
      String email = request.getParameter("email");
      String phone = request.getParameter("phone");
      String bdate = request.getParameter("bdate");
      String selection = request.getParameter("selection");

      Connection connection = null;
      String insertSql = " INSERT IGNORE INTO Project (id, FIRSTNAME, LASTNAME, EMAIL, PHONE, BDATE, SELECTION) values (default, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnectionAtta.getDBConnection();
         connection = DBConnectionAtta.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, firstName);
         preparedStmt.setString(2, lastName);
         preparedStmt.setString(3, email);
         preparedStmt.setString(4, phone);
         preparedStmt.setString(5, bdate);
         preparedStmt.setString(6, selection);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Thank You for signing up!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" +
            "<body bgcolor=\"#ffffff\">\n" +
            "<h2 align=\"center\">" + title + "</h2>\n" +
            "<ul>\n" + 
            "  <li>Full Name: " + firstName + " " + lastName + "\n" + "  <li>Email: " + email + "\n" +
            "  <li>Phone: " + phone + "\n" + "  <li>Date of Birth: " + bdate + "\n" + "</ul>\n");

      out.println("<a href=/webproject-ex-0128-atta/search_atta.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
