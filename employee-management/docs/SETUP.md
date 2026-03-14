# Employee Management System - SQLite Setup

## SQLite Integration

This project now uses SQLite as the database, which is a lightweight, file-based database perfect for small to medium applications.

## Prerequisites

### SQLite JDBC Driver

Download the SQLite JDBC driver from:
- https://github.com/xerial/sqlite-jdbc/releases
- Or use Maven/Gradle (see below)

### Manual Setup

1. Download `sqlite-jdbc-<version>.jar` from the link above
2. Add it to your project's classpath:
   - For command-line compilation: `javac -cp ".:sqlite-jdbc-3.45.1.0.jar" src/*.java`
   - For IDE: Add the JAR to your project's build path

### Using Maven

Add to your `pom.xml`:
```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.45.1.0</version>
</dependency>
```

### Using Gradle

Add to your `build.gradle`:
```gradle
dependencies {
    implementation 'org.xerial:sqlite-jdbc:3.45.1.0'
}
```

## Database File

The database file `employee_management.db` will be automatically created in the project root directory when you first run the application.

## Database Schema

The database contains two tables:
- **Admin**: Stores admin credentials (default: username=`admin`, password=`admin123`)
- **Employee**: Stores employee information (emp_id, name, email, department, salary)

## Command-Line SQLite Access

### Install SQLite Command-Line Tool

**On Linux/Kali:**
```bash
sudo apt-get update
sudo apt-get install sqlite3
```

**On macOS:**
```bash
brew install sqlite3
```

**On Windows:**
Download from https://www.sqlite.org/download.html

### Using SQLite CLI

Open the database:
```bash
sqlite3 employee_management.db
```

Common commands:
```sql
-- List all tables
.tables

-- View table schema
.schema Admin
.schema Employee

-- Query data
SELECT * FROM Admin;
SELECT * FROM Employee;

-- Insert test employee
INSERT INTO Employee (name, email, department, salary) 
VALUES ('John Doe', 'john@example.com', 'IT', 50000.00);

-- Update employee
UPDATE Employee SET salary = 55000.00 WHERE emp_id = 1;

-- Delete employee
DELETE FROM Employee WHERE emp_id = 1;

-- Exit SQLite CLI
.quit
```

### Useful SQLite CLI Commands

- `.tables` - List all tables
- `.schema` - Show CREATE statements
- `.headers on` - Show column headers in output
- `.mode column` - Display results in columns
- `.dump` - Export database to SQL
- `.read filename.sql` - Execute SQL from file
- `.backup filename.db` - Backup database

## Running the Application

1. Ensure the SQLite JDBC driver is in your classpath
2. Compile your Java files
3. Deploy to your servlet container (Tomcat, Jetty, etc.)
4. The database will be automatically initialized on first connection
5. Login with default credentials: `admin` / `admin123`

## Notes

- The database file is portable - you can copy `employee_management.db` to backup or move your data
- No separate database server needed
- Automatic table creation on first run
- Default admin account is created automatically
