package com.gdb.activity19.tests;

import com.gdb.activity19.command.TransactionCommand;
import com.gdb.activity19.logging.MemoryLogDestination;
import com.gdb.activity19.logging.TransactionLogger;
import com.gdb.activity19.service.AccountService;
import com.gdb.domain.AbstractAccount;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InvalidPinException;
import java.util.List;

public class TestAccountService {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 19 — ACCOUNT SERVICE DEMO");
        System.out.println("=".repeat(60));

        MemoryLogDestination destination = new MemoryLogDestination();
        destination.clear();

        TransactionLogger logger = new TransactionLogger(destination);
        AccountService service = new AccountService(logger);

        IAccount john = service.openAccount("SAVINGS", "John Doe", 25, 15000);
        ((AbstractAccount) john).setPin(1234);
        IAccount jane = service.openAccount("SAVINGS", "Jane Smith", 30, 10000);
        ((AbstractAccount) jane).setPin(5678);

        System.out.println("[STEP 13] Opened: " + john.getCustomerName() + " / " + jane.getCustomerName());

        Transaction depositTxn = service.deposit(john.getAccountNumber(), 5000);
        System.out.println("[STEP 14] Deposit: " + depositTxn);

        Transaction withdrawalTxn = service.withdraw(john.getAccountNumber(), 2000, 1234);
        System.out.println("[STEP 15] Withdrawal: " + withdrawalTxn);

        Transaction transferTxn = service.transfer(john.getAccountNumber(), jane.getAccountNumber(), 1000, 1234);
        System.out.println("[STEP 16] Transfer: " + transferTxn);

        System.out.println("[STEP 17] Final Balances:");
        System.out.println("  John (Account #" + john.getAccountNumber() + "): Rs. " + john.getBalance());
        System.out.println("  Jane (Account #" + jane.getAccountNumber() + "): Rs. " + jane.getBalance());

        List<TransactionCommand> history = service.getTransactionHistory();
        System.out.println("[STEP 18] Transaction History (" + history.size() + " records):");
        for (int i = 0; i < history.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + history.get(i).getTransaction());
        }

        try {
            service.deposit(9999, 500);
            throw new AssertionError("Expected deposit to missing account to fail");
        } catch (AccountException ex) {
            System.out.println("[STEP 19] Deposit to missing account caught: " + ex.getMessage() + " [PASS]");
        }

        try {
            service.withdraw(john.getAccountNumber(), 100, 9999);
            throw new AssertionError("Expected wrong PIN to fail");
        } catch (InvalidPinException ex) {
            System.out.println("[STEP 19] Withdrawal with wrong PIN caught: " + ex.getMessage() + " [PASS]");
        }
    }
}
