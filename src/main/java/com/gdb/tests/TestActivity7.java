package com.gdb.tests;

import com.gdb.activity5.domain.Account;
import com.gdb.activity5.exceptions.AccountException;

public class TestActivity7 {
    public static void main(String[] args) throws AccountException {
        System.out.println("=== Activity 7: Account Lifecycle ===");
        Account account = new Account(7001, "Riya Kapoor", 30, 5000, "Savings");
        account.setPin(7777);
        System.out.println("Account active: " + account.getStatus());
        account.closeAccount();
        System.out.println("Account closed: " + account.getStatus());
        try {
            account.deposit(100);
            System.out.println("Inactive deposit: FAIL");
        } catch (AccountException exception) {
            System.out.println("Inactive deposit blocked: PASS");
        }
        account.reopenAccount();
        System.out.println("Account reopened: " + account.getStatus());
    }
}