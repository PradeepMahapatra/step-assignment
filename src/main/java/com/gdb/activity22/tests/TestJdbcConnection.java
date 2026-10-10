package com.gdb.activity22.tests;

import com.gdb.activity22.db.ConnectionProvider;
import com.gdb.activity22.db.JdbcConnectionProvider;
import com.gdb.activity22.db.SchemaInitializer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJdbcConnection {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 22 — JDBC FOUNDATION TEST");
        System.out.println("=".repeat(60));

        ConnectionProvider provider = new JdbcConnectionProvider("jdbc:sqlite:gdb.db", "org.sqlite.JDBC");
        try (Connection connection = provider.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT 1 AS ok");
            if (resultSet.next() && resultSet.getInt("ok") == 1) {
                System.out.println("[STEP 1] JDBC connection ready and SELECT 1 succeeded.");
            } else {
                throw new IllegalStateException("SELECT 1 did not return expected value");
            }
        }

        SchemaInitializer initializer = new SchemaInitializer(provider);
        initializer.initialize();
        System.out.println("[STEP 2] Schema initialized successfully.");

        try (Connection connection = provider.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name IN ('accounts', 'transactions') ORDER BY name");
            int count = 0;
            while (resultSet.next()) {
                System.out.println("[STEP 3] Found table: " + resultSet.getString("name"));
                count++;
            }
            if (count != 2) {
                throw new IllegalStateException("Expected 2 tables but found " + count);
            }
        }

        System.out.println("All JDBC connection and schema checks passed!");
    }
}
