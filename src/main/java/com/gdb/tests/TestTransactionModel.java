package com.gdb.tests;

import com.gdb.domain.Account;
import com.gdb.domain.AccountFactory;
import com.gdb.domain.Transaction;
import com.gdb.service.TransferService;

public class TestTransactionModel {
    public static void main(String[] args) throws Exception {
        System.out.println("============================================================");
        System.out.println("  ACTIVITY 16 - TRANSACTION MODEL TEST");
        System.out.println("============================================================");

        Account acc1 = (Account) AccountFactory.createAccount(
                "SAVINGS", 1001, "Rajesh Sharma", 30, 50000, 0, "1234");
        Account acc2 = (Account) AccountFactory.createAccount(
                "SAVINGS", 1002, "Priya Patel", 28, 10000, 0, "5678");

        Transaction deposit = acc1.depositWithTransaction(5000);
        System.out.println("[STEP 10] Deposit Transaction: " + deposit);
        Transaction withdrawal = acc1.withdrawWithTransaction(2000, 1234);
        System.out.println("[STEP 11] Withdrawal Transaction: " + withdrawal);
        Transaction transfer = TransferService.transferWithTransaction(acc1, acc2, 1000, 1234);
        System.out.println("[STEP 12] Transfer Transaction: " + transfer);

        acc1.deposit(1000);
        System.out.printf("[STEP 13] Legacy Deposit +1000: Account #%d | %s | %s | Rs. %.1f | %s%n",
                acc1.getAccountNumber(), acc1.getCustomerName(), acc1.getAccountType(),
                acc1.getBalance(), acc1.getStatus());
    }
}
