# Employee Management System - SQLite Edition

A Java-based employee management web application using SQLite database and Servlets.

## 📁 Project Structure

```
employee-management/
├── src/                          # Java servlet source files
│   ├── AddEmployeeServlet.java
│   ├── DBConnection.java         # SQLite database connection
│   ├── DeleteEmployeeServlet.java
│   ├── GetEmployeeServlet.java
│   ├── LoginServlet.java
│   ├── LogoutServlet.java
│   ├── UpdateEmployeeServlet.java
│   └── ViewEmployeesServlet.java
├── web/                          # Frontend files
│   ├── addEmployee.html
│   ├── dashboard.html
│   ├── editEmployee.html
│   ├── login.html
│   ├── viewEmployees.html
│   ├── css/
│   │   └── styles.css
│   └── js/
│       └── script.js
├── database/                     # Database schema
│   └── database.sql              # SQLite schema file
├── lib/                          # JAR dependencies
│   ├── sqlite-jdbc-3.45.1.0.jar
│   ├── slf4j-api-2.0.9.jar
│   ├── slf4j-simple-2.0.9.jar
│   └── javax.servlet-api-4.0.1.jar
├── scripts/                      # Utility scripts
│   ├── TestDatabase.java         # Database connection test
│   └── sqlite-reference.sh       # SQLite CLI reference
├── docs/                         # Documentation
│   └── SETUP.md                  # Detailed setup guide
└── employee_management.db        # SQLite database file (auto-created)
```

