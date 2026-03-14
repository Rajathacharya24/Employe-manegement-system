import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/web/addEmployee.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String contextPath = request.getContextPath();
        
        // Basic session check can be added here
        if (request.getSession().getAttribute("adminLoggedIn") == null) {
            response.sendRedirect(contextPath + "/web/login.html");
            return;
        }

        String name = request.getParameter("name") != null ? request.getParameter("name").trim() : "";
        String email = request.getParameter("email") != null ? request.getParameter("email").trim() : "";
        String department = request.getParameter("department") != null ? request.getParameter("department").trim() : "";
        String salaryStr = request.getParameter("salary");
        double salary;

        if (name.isEmpty() || email.isEmpty() || department.isEmpty() || salaryStr == null || salaryStr.trim().isEmpty()) {
            response.sendRedirect(contextPath + "/web/addEmployee.html?error=invalid_input");
            return;
        }

        try {
            salary = Double.parseDouble(salaryStr.trim());
            if (salary <= 0) {
                response.sendRedirect(contextPath + "/web/addEmployee.html?error=invalid_salary");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(contextPath + "/web/addEmployee.html?error=invalid_salary");
            return;
        }

        boolean success = false;
        String errorCode = null;
        
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Database connection could not be established");
            }

            String sql = "INSERT INTO Employee (name, email, department, salary) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, department);
                stmt.setDouble(4, salary);
                
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    success = true;
                }
            }
        } catch (SQLException e) {
            String message = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (message.contains("unique constraint") || message.contains("email")) {
                errorCode = "duplicate_email";
            } else {
                errorCode = "db_error";
            }
            e.printStackTrace();
        } catch (Exception e) {
            errorCode = "server_error";
            e.printStackTrace();
        }

        if (success) {
            response.sendRedirect(contextPath + "/web/addEmployee.html?success=true");
        } else {
            if (errorCode == null) {
                errorCode = "failed";
            }
            response.sendRedirect(contextPath + "/web/addEmployee.html?error=" + errorCode);
        }
    }
}
