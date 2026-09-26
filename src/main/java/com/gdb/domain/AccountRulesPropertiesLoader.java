package com.gdb.domain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class AccountRulesPropertiesLoader {
    private final Properties properties = new Properties();

    public AccountRulesPropertiesLoader(String resourcePath) {
        try (InputStream stream = open(resourcePath)) {
            if (stream != null) properties.load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load rules from " + resourcePath, exception);
        }
    }

    private InputStream open(String resourcePath) throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (stream != null) return stream;
        Path path = Path.of(resourcePath);
        return Files.exists(path) ? Files.newInputStream(path) : null;
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }
}
