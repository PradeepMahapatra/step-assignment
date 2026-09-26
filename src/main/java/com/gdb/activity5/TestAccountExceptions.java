package com.gdb.activity5;

import com.gdb.activity5.domain.Account;
import com.gdb.activity5.exceptions.AccountException;

public class TestAccountExceptions {
    public static void main(String[] args) {
        System.out.println("Activity 5: Exception-based Account Testing");
        Account account = null;
        try {
            account = new Account(3001, "Kabir Joshi", 28, 3000, "Savings");
            log("1. Valid creation", true);
        } catch (IllegalArgumentException exception) { log("1. Valid creation", false, exception); }

        testCreation("2. Invalid age", 17, 3000, "Savings");
        testCreation("3. Invalid account type", 25, 3000, "Business");
        testCreation("4. Minimum balance violation", 25, 100, "Savings");

        if (account == null) return;
        try {
            account.setPin(4321);
            account.deposit(500);
            log("5. Valid deposit", true);
            account.withdraw(750, 4321);
            log("6. Valid withdrawal", true);
        } catch (AccountException exception) { log("5-6. Valid transactions", false, exception); }
        try { account.deposit(-10); log("7. Invalid deposit", false); }
        catch (AccountException exception) { log("7. Invalid deposit", true); }
        try { account.withdraw(10000, 4321); log("8. Insufficient balance", false); }
        catch (AccountException exception) { log("8. Insufficient balance", true); }
        try { account.withdraw(2500, 4321); log("9. Minimum balance violation", false); }
        catch (AccountException exception) { log("9. Minimum balance violation", true); }
        try {
            account.closeAccount();
            account.deposit(10);
            log("10. Inactive account operation", false);
        } catch (AccountException exception) { log("10. Inactive account operation", true, exception); }
        try {
            log("11. PIN verification", account.verifyPin(4321) && !account.verifyPin(9999));
        } catch (RuntimeException exception) { log("11. PIN verification", false, exception); }
        System.out.printf("Summary: #%d | %s | %s | Balance: Rs %.2f%n",
                account.getAccountNumber(), account.getName(), account.getStatus(), account.getBalance());
    }

    private static void testCreation(String label, int age, double balance, String type) {
        try { new Account(3999, "Test", age, balance, type); log(label, false); }
        catch (IllegalArgumentException exception) { log(label, true); }
    }

    private static void log(String label, boolean passed) { System.out.printf("%s: %s%n", label, passed ? "PASS" : "FAIL"); }
    private static void log(String label, boolean passed, Exception exception) {
        System.out.printf("%s: %s (%s)%n", label, passed ? "PASS" : "FAIL", exception.getMessage());
    }
}
