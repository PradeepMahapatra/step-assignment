package com.gdb.activity22.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionProvider implements ConnectionProvider {
    private final String url;
    private final String driverClassName;

    public JdbcConnectionProvider(String url, String driverClassName) {
        this.url = url;
        this.driverClassName = driverClassName;
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBC driver not found: " + driverClassName, e);
        }
    }

    public JdbcConnectionProvider() {
        this(loadUrlFromProperties(), loadDriverFromProperties());
    }

    public JdbcConnectionProvider(String url) {
        this(url, "org.sqlite.JDBC");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    @Override
    public String getProviderName() {
        return "JDBC";
    }

    private static String loadUrlFromProperties() {
        Properties properties = loadProperties();
        return properties.getProperty("persistence.db.url", "jdbc:sqlite:gdb.db");
    }

    private static String loadDriverFromProperties() {
        Properties properties = loadProperties();
        return properties.getProperty("persistence.db.driver", "org.sqlite.JDBC");
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = JdbcConnectionProvider.class.getClassLoader()
                .getResourceAsStream("config/persistence.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read JDBC configuration", e);
        }
        return properties;
    }
}
