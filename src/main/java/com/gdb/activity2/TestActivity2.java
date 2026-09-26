package com.gdb.activity2;

import com.gdb.activity1.Account;

public class TestActivity2 {
    public static void main(String[] args) {
        System.out.println("=== Activity 2: Basic Account Operations ===");
        Account account = new Account(2001, "Aarav Sharma", 25, 5000, "Savings");
        System.out.printf("Initial balance: Rs %.2f%n", account.getBalance());
        System.out.println("Deposit Rs 750: " + account.deposit(750));
        System.out.println("Withdraw Rs 1000: " + account.withdraw(1000));
        System.out.println("Withdraw Rs 10000: " + account.withdraw(10000));
        System.out.printf("Final balance: Rs %.2f%n", account.getBalance());
    }
}