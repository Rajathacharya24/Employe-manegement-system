import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEmployeeServlet")
public class DeleteEmployeeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        if (request.getSession().getAttribute("adminLoggedIn") == null) {
            response.sendRedirect("web/login.html");
            return;
        }

        String idStr = request.getParameter("emp_id");
        boolean success = false;
        
        if (idStr != null && !idStr.isEmpty()) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM Employee WHERE emp_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, Integer.parseInt(idStr));
                    
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        success = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (success) {
            response.sendRedirect("web/viewEmployees.html?deleted=true");
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<script>alert('Failed to delete employee.'); window.history.back();</script>");
        }
    }
}
