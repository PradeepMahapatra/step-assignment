package com.gdb.activity21.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class RepositoryFactory {
    private RepositoryFactory() {
    }

    public static AccountRepository getAccountRepository() {
        return new InMemoryAccountRepository();
    }

    public static TransactionRepository getTransactionRepository() {
        return new InMemoryTransactionRepository();
    }

    public static String getMode() {
        Properties properties = new Properties();
        try (InputStream inputStream = RepositoryFactory.class.getClassLoader()
                .getResourceAsStream("config/persistence.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read persistence configuration", e);
        }
        return properties.getProperty("persistence.mode", "memory").trim().toLowerCase();
    }
}
