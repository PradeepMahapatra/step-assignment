package com.gdb.domain;

import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.exceptions.MinimumBalanceViolationException;

public class SavingsAccount extends Account {
    private static final double MINIMUM_BALANCE = 500.00;
    private static final double ANNUAL_INTEREST_RATE = 0.04;

    public SavingsAccount(int accountNumber, String name, int age, double initialBalance) {
        super(accountNumber, name, age, initialBalance, "Savings", MINIMUM_BALANCE);
    }

    public SavingsAccount(int accountNumber, String name, int age, double initialBalance, int tenureYears) {
        super(accountNumber, name, age, initialBalance, "Savings",
                AccountRulesEngine.getSavingsMinBalance(tenureYears), tenureYears);
    }

    @Override
    public void processDebit(double amount) throws AccountException {
        if (amount > balance) throw new InsufficientBalanceException("Insufficient balance");
        if (balance - amount < getMinimumBalance()) {
            throw new MinimumBalanceViolationException("Withdrawal would violate the minimum balance");
        }
        balance -= amount;
    }

    public double calculateInterest(int years) {
        if (years < 0) throw new IllegalArgumentException("Years cannot be negative");
        double rate = getTenureYears() == 0 ? ANNUAL_INTEREST_RATE : getInterestRate() / 100.0;
        return getBalance() * rate * years;
    }

    public double getInterestRate() {
        return getTenureYears() == 0 ? ANNUAL_INTEREST_RATE * 100.0
                : AccountRulesEngine.getSavingsInterestRate(getTenureYears());
    }
}
