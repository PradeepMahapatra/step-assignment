package com.gdb.activity22;

import com.gdb.activity22.db.JdbcConnectionProvider;
import com.gdb.activity22.db.SchemaInitializer;

public class Main {
    public static void main(String[] args) throws Exception {
        JdbcConnectionProvider provider = new JdbcConnectionProvider();
        SchemaInitializer initializer = new SchemaInitializer(provider);
        initializer.initialize();
        System.out.println("SQLite schema initialized using JDBC provider: " + provider.getProviderName());
    }
}
