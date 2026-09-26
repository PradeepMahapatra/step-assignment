package com.gdb.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Account extends AbstractAccount {
    private final int tenureYears;
    private double dailyTransferTotal;
    private LocalDateTime lastTransferDate;

    protected Account(int accountNumber, String name, int age, double initialBalance,
                      String accountType, double minimumBalance) {
        super(accountNumber, name, age, initialBalance, accountType, minimumBalance);
        this.tenureYears = 0;
        this.lastTransferDate = LocalDateTime.now();
    }

    protected Account(int accountNumber, String name, int age, double initialBalance,
                      String accountType, double minimumBalance, int tenureYears) {
        super(accountNumber, name, age, initialBalance, accountType, minimumBalance);
        if (tenureYears < 0) throw new IllegalArgumentException("Tenure cannot be negative");
        this.tenureYears = tenureYears;
        this.lastTransferDate = LocalDateTime.now();
    }

    public int getTenureYears() { return tenureYears; }
    public boolean isActive() { return "Active".equals(getStatus()); }

    public boolean canWithdraw(double amount) {
        if (amount <= 0 || !isActive()) return false;
        if (this instanceof CurrentAccount current) {
            return amount <= getBalance() + current.getOverdraftLimit();
        }
        return amount <= getBalance();
    }

    public double getDailyTransferLimit() {
        return AccountRulesEngine.getInstance().getDailyTransferLimit(getAccountType(), tenureYears);
    }

    public double getRemainingDailyTransferLimit() {
        resetDailyTransferIfNeeded();
        return Math.max(0.0, getDailyTransferLimit() - dailyTransferTotal);
    }

    public boolean canTransfer(double amount) {
        resetDailyTransferIfNeeded();
        return amount > 0 && dailyTransferTotal + amount <= getDailyTransferLimit();
    }

    public void updateDailyTransferTotal(double amount) {
        resetDailyTransferIfNeeded();
        dailyTransferTotal += amount;
        lastTransferDate = LocalDateTime.now();
    }

    public void resetDailyTransferIfNeeded() {
        if (lastTransferDate.toLocalDate().isBefore(LocalDate.now())) {
            dailyTransferTotal = 0.0;
            lastTransferDate = LocalDateTime.now();
        }
    }

    public double getDailyTransferTotal() { return dailyTransferTotal; }
    public LocalDateTime getLastTransferDate() { return lastTransferDate; }

    public Transaction depositWithTransaction(double amount)
            throws com.gdb.exceptions.InvalidAmountException, com.gdb.exceptions.InactiveAccountException {
        deposit(amount);
        return new Transaction(Transaction.generateId(), LocalDateTime.now(), getAccountNumber(),
                TransactionType.DEPOSIT, amount, getBalance(), "SUCCESS",
                "Deposit of " + amount, 0, getAccountNumber());
    }

    public Transaction withdrawWithTransaction(double amount, int pin)
            throws com.gdb.exceptions.AccountException {
        withdraw(amount, pin);
        return new Transaction(Transaction.generateId(), LocalDateTime.now(), getAccountNumber(),
                TransactionType.WITHDRAW, amount, getBalance(), "SUCCESS",
                "Withdrawal of " + amount, getAccountNumber(), 0);
    }
}
