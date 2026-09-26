package com.gdb.activity3;

public class TestAccountEnhanced {
    public static void main(String[] args) {
        AccountEnhanced valid = new AccountEnhanced(2001, "Ishaan Rao", 22, 2500, "Current");
        AccountEnhanced corrected = new AccountEnhanced(2002, "Nisha Singh", 16, 100, "Investment");

        System.out.println("Activity 3: Enhanced Account Testing");
        printAccount(valid);
        printAccount(corrected);
        System.out.printf("Corrected age: %d, type: %s, minimum balance: %.2f%n",
                corrected.getAge(), corrected.getAccountType(), corrected.getBalance());
        System.out.printf("Set valid PIN: %s | Has PIN: %s | Correct PIN: %s | Wrong PIN: %s%n",
                valid.setPin(1234), valid.hasPin(), valid.verifyPin(1234), valid.verifyPin(9999));
        System.out.printf("Withdraw below minimum: %s%n", valid.withdraw(1600, 1234));
        System.out.printf("Close account: %s | Deposit while inactive: %s | Reopen: %s%n",
                valid.closeAccount(), valid.deposit(100), valid.reopenAccount());
        printAccount(valid);
    }

    private static void printAccount(AccountEnhanced account) {
        System.out.printf("Account #%d | %s | Age: %d | Type: %s | Status: %s | Balance: Rs %.2f%n",
                account.getAccountNumber(), account.getName(), account.getAge(), account.getAccountType(),
                account.getStatus(), account.getBalance());
    }
}
