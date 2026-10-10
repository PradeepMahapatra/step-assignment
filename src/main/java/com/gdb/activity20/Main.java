package com.gdb.activity20;

import com.gdb.activity19.logging.FileLogDestination;
import com.gdb.activity19.logging.TransactionLogger;
import com.gdb.activity19.service.AccountService;
import com.gdb.activity20.ui.AccountUI;

public class Main {
    public static void main(String[] args) {
        System.out.println("Booting Global Digital Bank...");

        FileLogDestination destination = new FileLogDestination();
        destination.clear();

        TransactionLogger logger = new TransactionLogger(destination);
        AccountService service = new AccountService(logger);
        AccountUI ui = new AccountUI(service);
        ui.start();
    }
}
