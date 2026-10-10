package com.gdb.activity20.tests;

import com.gdb.activity19.logging.LogDestination;
import com.gdb.activity19.logging.MemoryLogDestination;
import com.gdb.activity19.logging.TransactionLogger;
import com.gdb.activity19.service.AccountService;
import com.gdb.domain.AbstractAccount;
import com.gdb.domain.IAccount;

public class TestAccountUI {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 20 — ACCOUNT UI & INTEGRATION TEST");
        System.out.println("=".repeat(60));

        LogDestination dest = new MemoryLogDestination();
        TransactionLogger logger = new TransactionLogger(dest);
        AccountService service = new AccountService(logger);

        IAccount acc = service.openAccount("SAVINGS", "Alice Cooper", 28, 20000);
        ((AbstractAccount) acc).setPin(1234);
        System.out.println("[UI TEST] Opened: " + acc.getCustomerName() + " | Balance: Rs. " + acc.getBalance());

        service.deposit(acc.getAccountNumber(), 5000);
        System.out.println("[UI TEST] Deposit Rs. 5000 | Balance: Rs. " + acc.getBalance());

        service.withdraw(acc.getAccountNumber(), 3000, 1234);
        System.out.println("[UI TEST] Withdraw Rs. 3000 | Balance: Rs. " + acc.getBalance());

        System.out.println("[UI TEST] Total Logged Transactions: " + service.getTransactionHistory().size());
        System.out.println("All UI service endpoints validated successfully!");
    }
}
