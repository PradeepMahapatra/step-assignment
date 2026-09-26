package com.gdb.domain;

import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InsufficientBalanceException;

public class CurrentAccount extends Account {
    private static final double MINIMUM_BALANCE = 1000.00;
    private static final double OVERDRAFT_LIMIT = 5000.00;
    private double overdraftUsed;

    public CurrentAccount(int accountNumber, String name, int age, double initialBalance) {
        super(accountNumber, name, age, initialBalance, "Current", MINIMUM_BALANCE);
        overdraftUsed = 0.0;
    }

    public CurrentAccount(int accountNumber, String name, int age, double initialBalance, int tenureYears) {
        super(accountNumber, name, age, initialBalance, "Current",
                AccountRulesEngine.getMinimumBalance("Current", tenureYears, MINIMUM_BALANCE), tenureYears);
        overdraftUsed = 0.0;
    }

    @Override
    public void processDebit(double amount) throws AccountException {
        if (amount > balance + getOverdraftLimit()) {
            throw new InsufficientBalanceException("Balance and overdraft limit are insufficient");
        }
        balance -= amount;
        overdraftUsed = Math.max(0.0, -balance);
    }

    public double getOverdraftLimit() {
        return getTenureYears() == 0 ? OVERDRAFT_LIMIT
                : AccountRulesEngine.getCurrentOverdraftLimit(getBalance());
    }
    public double getOverdraftUsed() { return overdraftUsed; }
    public double getAvailableOverdraft() { return OVERDRAFT_LIMIT - overdraftUsed; }
    public boolean isUsingOverdraft() { return overdraftUsed > 0.0; }

    public boolean repayOverdraft(double amount) {
        if (amount <= 0 || overdraftUsed == 0.0 || amount > overdraftUsed) return false;
        balance += amount;
        overdraftUsed -= amount;
        return true;
    }
}
