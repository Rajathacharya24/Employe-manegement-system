import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetEmployeeServlet")
public class GetEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        if (request.getSession().getAttribute("adminLoggedIn") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String idStr = request.getParameter("id");
        if(idStr == null || idStr.isEmpty()) {
            out.print("{\"success\":false}");
            return;
        }

        boolean found = false;
        StringBuilder jsonBuilder = new StringBuilder();
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Employee WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(idStr));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        found = true;
                        jsonBuilder.append("{")
                                   .append("\"emp_id\":").append(rs.getInt("emp_id")).append(",")
                                   .append("\"name\":\"").append(escapeJson(rs.getString("name"))).append("\",")
                                   .append("\"email\":\"").append(escapeJson(rs.getString("email"))).append("\",")
                                   .append("\"department\":\"").append(escapeJson(rs.getString("department"))).append("\",")
                                   .append("\"salary\":").append(rs.getDouble("salary"))
                                   .append("}");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(found) {
            out.print(jsonBuilder.toString());
        } else {
            out.print("{\"success\":false}");
        }
        out.flush();
    }
    
    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
