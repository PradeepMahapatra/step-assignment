package com.gdb.activity19;

import com.gdb.activity19.logging.FileLogDestination;
import com.gdb.activity19.logging.TransactionLogger;
import com.gdb.activity19.service.AccountService;
import com.gdb.domain.AbstractAccount;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Global Digital Bank — Service Demo");
        System.out.println("=".repeat(60));

        FileLogDestination destination = new FileLogDestination();
        destination.clear();

        TransactionLogger logger = new TransactionLogger(destination);
        AccountService service = new AccountService(logger);

        IAccount john = service.openAccount("SAVINGS", "John Doe", 25, 15000);
        ((AbstractAccount) john).setPin(1234);
        IAccount jane = service.openAccount("SAVINGS", "Jane Smith", 30, 10000);
        ((AbstractAccount) jane).setPin(5678);

        Transaction deposit = service.deposit(john.getAccountNumber(), 5000);
        System.out.println("Deposit: " + deposit);

        Transaction withdrawal = service.withdraw(john.getAccountNumber(), 2000, 1234);
        System.out.println("Withdrawal: " + withdrawal);

        Transaction transfer = service.transfer(john.getAccountNumber(), jane.getAccountNumber(), 1000, 1234);
        System.out.println("Transfer: " + transfer);

        System.out.println("Final Balances: John = Rs. " + john.getBalance() + ", Jane = Rs. " + jane.getBalance());
        System.out.println("History size: " + service.getTransactionHistory().size());
    }
}
