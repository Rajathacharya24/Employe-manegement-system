#!/bin/bash
# Quick test script for SQLite database setup

echo "🔍 Testing Employee Management SQLite Database..."
echo ""

# Navigate to project directory
cd "$(dirname "$0")/.."

# Check if database exists
if [ ! -f "employee_management.db" ]; then
    echo "⚠️  Database file not found. Running test to create it..."
    echo ""
fi

# Compile and run test
echo "📝 Compiling test program..."
javac -cp ".:lib/sqlite-jdbc-3.45.1.0.jar" src/DBConnection.java scripts/TestDatabase.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful"
    echo ""
    echo "🚀 Running database test..."
    echo ""
    java -cp ".:src:scripts:lib/sqlite-jdbc-3.45.1.0.jar:lib/slf4j-api-2.0.9.jar:lib/slf4j-simple-2.0.9.jar" TestDatabase
else
    echo "❌ Compilation failed"
    exit 1
fi
