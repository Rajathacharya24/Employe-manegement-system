import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDatabase {
    public static void main(String[] args) {
        System.out.println("Testing SQLite Database Connection...\n");
        
        // Test connection
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.err.println("Failed to connect to database!");
            return;
        }
        
        System.out.println("✓ Database connection successful!");
        System.out.println("✓ Database file created: employee_management.db\n");
        
        try (Statement stmt = conn.createStatement()) {
            // Test Admin table
            System.out.println("Testing Admin table:");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Admin");
            while (rs.next()) {
                System.out.println("  Admin found: " + rs.getString("username"));
            }
            rs.close();
            
            // Test Employee table
            System.out.println("\nTesting Employee table:");
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM Employee");
            if (rs.next()) {
                System.out.println("  Employee records: " + rs.getInt("count"));
            }
            rs.close();
            
            System.out.println("\n✓ All database operations successful!");
            System.out.println("\nYou can now access the database using:");
            System.out.println("  sqlite3 employee_management.db");
            
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Error testing database:");
            e.printStackTrace();
        }
    }
}
