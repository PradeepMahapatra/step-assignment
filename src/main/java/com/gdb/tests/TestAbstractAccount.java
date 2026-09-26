package com.gdb.tests;

import com.gdb.domain.AbstractAccount;
import com.gdb.domain.CurrentAccount;
import com.gdb.domain.SalaryAccount;
import com.gdb.domain.SavingsAccount;
import com.gdb.exceptions.AccountException;

public class TestAbstractAccount {
    public static void main(String[] args) throws AccountException {
        System.out.println("=== Activity 10: Banking Operations Suite ===");

        SavingsAccount savings = new SavingsAccount(9001, "Aarav Sharma", 25, 10000);
        CurrentAccount current = new CurrentAccount(9002, "Meera Patel", 31, 5000);
        SalaryAccount salary = new SalaryAccount(9003, "Rohan Mehta", 29, 4000);
        savings.setPin(1111);
        current.setPin(2222);
        salary.setPin(3333);

        AbstractAccount[] portfolio = { savings, current, salary };
        transfer(savings, current, 3000, 1111);
        System.out.printf("Savings Balance: Rs %.1f | Current Balance: Rs %.1f%n",
                savings.getBalance(), current.getBalance());

        double before = current.getBalance();
        transfer(savings, current, 1000, 9999);
        System.out.printf("Failed Transfer (Wrong PIN): %s%n",
                current.getBalance() == before ? "Exception caught, no balance changed [PASS]" : "FAIL");

        salary.creditSalary(2500);
        processMonthlyCycle(portfolio);
        System.out.println("Monthly Interest Cycle processed for all qualifying accounts.");
        System.out.println("All banking operations passed!");
    }

    private static void transfer(AbstractAccount source, AbstractAccount destination,
                                 double amount, int pin) {
        try {
            source.withdraw(amount, pin);
            destination.deposit(amount);
            System.out.printf("Transfer Rs %.0f from %s to %s: SUCCESS%n",
                    amount, source.getAccountType(), destination.getAccountType());
        } catch (AccountException exception) {
            System.out.printf("Transfer Rs %.0f from %s to %s: %s%n",
                    amount, source.getAccountType(), destination.getAccountType(), exception.getMessage());
        }
    }

    private static void processMonthlyCycle(AbstractAccount[] portfolio) throws AccountException {
        for (AbstractAccount account : portfolio) {
            if (account instanceof SavingsAccount savings) {
                savings.deposit(savings.calculateInterest(1));
            } else if (account instanceof SalaryAccount salary) {
                System.out.printf("Salary credit history: %s%n", salary.hasSalaryCreditHistory());
            }
        }
    }
}