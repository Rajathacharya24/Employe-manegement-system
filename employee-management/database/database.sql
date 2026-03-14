-- SQLite Database Schema for Employee Management System

-- Create Admin table
CREATE TABLE IF NOT EXISTS Admin (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

-- Create Employee table
CREATE TABLE IF NOT EXISTS Employee (
    emp_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    department TEXT NOT NULL,
    salary REAL NOT NULL
);

-- Insert a default admin for testing purposes
INSERT OR IGNORE INTO Admin (username, password) VALUES ('admin', 'admin123');

