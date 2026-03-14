import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // Allow overriding DB location using: -Demployee.db.path=/absolute/path/to/employee_management.db
    private static final String DB_PATH = System.getProperty("employee.db.path", "employee_management.db");
    private static final String URL = "jdbc:sqlite:" + new File(DB_PATH).getAbsolutePath();
    private static volatile boolean dbPathLogged = false;
    
    public static Connection getConnection() {
        try {
            // Load SQLite JDBC Driver
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            
            // Initialize database tables if they don't exist
            initializeDatabase(conn);
            logDatabasePathOnce();
            return conn;
            
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(
                    "SQLite JDBC driver not found. Ensure sqlite-jdbc is in runtime classpath.", e);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to connect to SQLite database at: " + new File(DB_PATH).getAbsolutePath(), e);
        }
    }

    private static void logDatabasePathOnce() {
        if (!dbPathLogged) {
            synchronized (DBConnection.class) {
                if (!dbPathLogged) {
                    System.out.println("SQLite database path: " + new File(DB_PATH).getAbsolutePath());
                    dbPathLogged = true;
                }
            }
        }
    }
    
    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Create Admin table
            stmt.execute("CREATE TABLE IF NOT EXISTS Admin (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL UNIQUE, " +
                    "password TEXT NOT NULL)");
            
            // Create Employee table
            stmt.execute("CREATE TABLE IF NOT EXISTS Employee (" +
                    "emp_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "email TEXT NOT NULL UNIQUE, " +
                    "department TEXT NOT NULL, " +
                    "salary REAL NOT NULL)");
            
            // Insert default admin if not exists
            stmt.execute("INSERT OR IGNORE INTO Admin (username, password) " +
                    "VALUES ('admin', 'admin123')");
            
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize SQLite database tables.", e);
        }
    }
}
