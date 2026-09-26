package com.gdb.activity8;

import com.gdb.domain.AbstractAccount;
import com.gdb.domain.CurrentAccount;
import com.gdb.domain.SavingsAccount;
import com.gdb.exceptions.AccountException;

public class TestAccountSubclasses {
    public static void main(String[] args) {
        System.out.println("Activity 8: Account Subclass Testing");
        SavingsAccount savings = new SavingsAccount(4001, "Anaya Kapoor", 24, 5000);
        savings.setPin(1111);
        System.out.printf("Savings interest: 1 year = Rs %.2f, 2 years = Rs %.2f, 5 years = Rs %.2f%n",
                savings.calculateInterest(1), savings.calculateInterest(2), savings.calculateInterest(5));

        CurrentAccount current = new CurrentAccount(4002, "Rohan Mehta", 35, 1000);
        current.setPin(2222);
        try {
            current.withdraw(4500, 2222);
            System.out.printf("Overdraft withdrawal: balance = Rs %.2f, used = Rs %.2f, available = Rs %.2f%n",
                    current.getBalance(), current.getOverdraftUsed(), current.getAvailableOverdraft());
            current.withdraw(1500, 2222);
            System.out.printf("Overdraft limit reached: used = Rs %.2f, using overdraft = %s%n",
                    current.getOverdraftUsed(), current.isUsingOverdraft());
        } catch (AccountException exception) { System.out.println("Unexpected transaction failure: " + exception.getMessage()); }
        System.out.printf("Repay overdraft: %s%n", current.repayOverdraft(2000));
        System.out.printf("After repayment: balance = Rs %.2f, used = Rs %.2f%n",
                current.getBalance(), current.getOverdraftUsed());

        System.out.println("Polymorphic accounts:");
    AbstractAccount[] accounts = { savings, current };
        for (AbstractAccount account : accounts) {
            System.out.printf("#%d | %s | %s | Minimum: Rs %.2f | Balance: Rs %.2f%n",
                    account.getAccountNumber(), account.getAccountType(), account.getName(),
                    account.getMinimumBalance(), account.getBalance());
        }

        testInvalidCreation("Invalid age", () -> new SavingsAccount(4991, "Test", 17, 500));
        testInvalidCreation("Invalid savings balance", () -> new SavingsAccount(4992, "Test", 20, 499));
        testInvalidCreation("Invalid current balance", () -> new CurrentAccount(4993, "Test", 20, 999));
    }

    private static void testInvalidCreation(String label, Runnable action) {
        try { action.run(); System.out.println(label + ": FAIL"); }
        catch (IllegalArgumentException exception) { System.out.println(label + ": PASS"); }
    }
}
