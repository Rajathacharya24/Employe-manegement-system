import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        
        boolean isValidUser = false;
        boolean databaseError = false;
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Admin WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, user);
                stmt.setString(2, pass);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        isValidUser = true;
                    }
                }
            }
        } catch (Exception e) {
            databaseError = true;
            e.printStackTrace();
        }

        if (databaseError) {
            response.sendRedirect(request.getContextPath() + "/web/login.html?error=db");
            return;
        }
        
        if (isValidUser) {
            // Create session to keep admin logged in
            HttpSession session = request.getSession();
            session.setAttribute("adminLoggedIn", true);
            session.setAttribute("username", user);
            
            // Redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/web/dashboard.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/web/login.html?error=invalid_credentials");
        }
    }
}
