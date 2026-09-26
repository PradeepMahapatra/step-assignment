package com.gdb.tests;

import com.gdb.activity5.domain.Account;
import com.gdb.activity5.exceptions.AccountException;

public class TestActivity6 {
    public static void main(String[] args) throws AccountException {
        System.out.println("=== Activity 6: Exception Handling ===");
        Account account = new Account(6001, "Kabir Joshi", 28, 3000, "Savings");
        account.setPin(4321);
        account.deposit(500);
        account.withdraw(750, 4321);
        System.out.printf("Successful transactions: balance = Rs %.2f%n", account.getBalance());
        try {
            account.withdraw(10000, 4321);
            System.out.println("Insufficient balance: FAIL");
        } catch (AccountException exception) {
            System.out.println("Insufficient balance: PASS");
        }
    }
}