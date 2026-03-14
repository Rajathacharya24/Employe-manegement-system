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

@WebServlet("/ViewEmployeesServlet")
public class ViewEmployeesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        // Security check
        if (request.getSession().getAttribute("adminLoggedIn") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Employee ORDER BY emp_id DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    boolean first = true;
                    while (rs.next()) {
                        if (!first) {
                            jsonBuilder.append(",");
                        }
                        first = false;
                        
                        // Manually building JSON for simplicity and zero dependencies
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
        
        jsonBuilder.append("]");
        out.print(jsonBuilder.toString());
        out.flush();
    }
    
    // Tiny helper to escape quotes in JSON string values
    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
