package com.gdb.tests;

import com.gdb.activity3.AccountEnhanced;

public class TestActivity4 {
    public static void main(String[] args) {
        System.out.println("=== Activity 4: Account Validation and PIN Security ===");
        AccountEnhanced account = new AccountEnhanced(4001, "Nisha Singh", 16, 100, "Investment");
        System.out.printf("Corrected age: %d | Type: %s | Balance: Rs %.2f%n",
                account.getAge(), account.getAccountType(), account.getBalance());
        System.out.println("Set PIN 1234: " + account.setPin(1234));
        System.out.println("Correct PIN: " + account.verifyPin(1234));
        System.out.println("Wrong PIN: " + account.verifyPin(9999));
        System.out.println("Close account: " + account.closeAccount());
        System.out.println("Deposit while inactive: " + account.deposit(100));
    }
}