package com.gdb.tests;

import com.gdb.domain.AccountFactory;
import com.gdb.domain.IAccount;
import com.gdb.exceptions.AccountException;

public class TestActivity12 {
    public static void main(String[] args) {
        System.out.println("=== Activity 12: Factory-Driven System Suite ===");
        IAccount savings = AccountFactory.createAccount("SAVINGS", 1201, "Savings User", 25, 10000, "1111");
        IAccount current = AccountFactory.createAccount("CURRENT", 1202, "Current User", 25, 2000, "2222");
        IAccount fixedDeposit = AccountFactory.createAccount("FD", 1203, "FD User", 25, 10000, "3333");

        try {
            savings.deposit(500);
            System.out.println("[Test 1] Savings Account Creation & Deposit: [PASS]");
        } catch (AccountException exception) {
            System.out.println("[Test 1] Savings Account Creation & Deposit: [FAIL] " + exception.getMessage());
        }

        try {
            savings.withdraw(10050, "1111");
            System.out.println("[Test 1b] Savings Minimum Balance Rule: [FAIL]");
        } catch (AccountException exception) {
            System.out.println("[Test 1b] Savings Minimum Balance Rule: [PASS]");
        }

        try {
            current.withdraw(5000, "2222");
            System.out.println("[Test 2] Current Account Overdraft Withdrawal: [PASS]");
        } catch (AccountException exception) {
            System.out.println("[Test 2] Current Account Overdraft Withdrawal: [FAIL] " + exception.getMessage());
        }

        try {
            fixedDeposit.withdraw(1000, "3333");
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [FAIL]");
        } catch (AccountException exception) {
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [PASS]");
        }

        try {
            AccountFactory.createAccount("UNKNOWN", 1204, "Unknown User", 25, 1000);
            System.out.println("[Test 4] Invalid Type Rejection: [FAIL]");
        } catch (IllegalArgumentException exception) {
            System.out.println("[Test 4] Invalid Type Rejection: [PASS]");
        }

        System.out.println("Factory-driven architecture successfully verified!");
    }
}