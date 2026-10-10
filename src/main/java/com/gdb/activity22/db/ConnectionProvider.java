package com.gdb.activity22.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
    Connection getConnection() throws SQLException;
    default void shutdown() {
    }
    String getProviderName();
}
