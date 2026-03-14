import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateEmployeeServlet")
public class UpdateEmployeeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        if (request.getSession().getAttribute("adminLoggedIn") == null) {
            response.sendRedirect("web/login.html");
            return;
        }

        String idStr = request.getParameter("emp_id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String department = request.getParameter("department");
        String salaryStr = request.getParameter("salary");

        boolean success = false;
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Employee SET name = ?, email = ?, department = ?, salary = ? WHERE emp_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, department);
                stmt.setDouble(4, Double.parseDouble(salaryStr));
                stmt.setInt(5, Integer.parseInt(idStr));
                
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    success = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            response.sendRedirect("web/viewEmployees.html?updated=true");
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<script>alert('Failed to update employee. Please try again.'); window.history.back();</script>");
        }
    }
}
