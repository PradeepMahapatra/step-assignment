package com.gdb.activity22.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    private final ConnectionProvider connectionProvider;

    public SchemaInitializer(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void initialize() throws SQLException, IOException {
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement()) {
            String schemaSql = readSchemaSql();
            for (String sql : schemaSql.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        }
    }

    private String readSchemaSql() throws IOException {
        String resourcePath = "schema.sql";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append(System.lineSeparator());
                }
                return builder.toString();
            }
        }
    }
}
