package com.gdb.tests;

import com.gdb.domain.Account;
import com.gdb.domain.AccountFactory;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.service.TransferService;

public class TestTransfer {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Activity 15: Transfer with Daily Limits ===");
        Account acc1 = (Account) AccountFactory.createAccount("SAVINGS", 1001, "Rajesh Sharma", 30, 100000, 0, "1234");
        Account acc2 = (Account) AccountFactory.createAccount("SAVINGS", 1002, "Priya Patel", 28, 20000, 0, "5678");

        TransferService.transfer(acc1, acc2, 5000, 1234);
        System.out.printf("Transfer Rs. 5000: SUCCESS | acc1 = Rs. %.1f | acc2 = Rs. %.1f%n",
                acc1.getBalance(), acc2.getBalance());

        try {
            TransferService.transfer(acc1, acc2, 100000, 1234);
        } catch (InsufficientBalanceException exception) {
            System.out.println("Caught InsufficientBalanceException: " + exception.getMessage());
        }

        System.out.println("Daily limit: Rs. " + acc1.getDailyTransferLimit());
        try {
            while (true) TransferService.transfer(acc1, acc2, 20000, 1234);
        } catch (AccountException exception) {
            System.out.println("Caught AccountException: " + exception.getMessage());
        }
        System.out.printf("Used today: Rs. %.1f | Remaining: Rs. %.1f%n",
                acc1.getDailyTransferTotal(), acc1.getRemainingDailyTransferLimit());
    }
}
