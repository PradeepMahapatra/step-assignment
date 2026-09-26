package com.gdb.activity5.domain;

import com.gdb.activity5.exceptions.AccountException;
import com.gdb.activity5.exceptions.InactiveAccountException;
import com.gdb.activity5.exceptions.InsufficientBalanceException;
import com.gdb.activity5.exceptions.InvalidAmountException;
import com.gdb.activity5.exceptions.InvalidPinException;
import com.gdb.activity5.exceptions.MinimumBalanceViolationException;

public class Account {
    private final int accountNumber;
    private String name;
    private final int age;
    private double balance;
    private final String accountType;
    private String status;
    private Integer pin;

    public Account(int accountNumber, String name, int age, double initialBalance, String accountType) {
        if (age < 18) throw new IllegalArgumentException("Age must be at least 18");
        if (!"Savings".equals(accountType) && !"Current".equals(accountType)) {
            throw new IllegalArgumentException("Account type must be Savings or Current");
        }
        if (initialBalance < minimumBalance(accountType)) {
            throw new IllegalArgumentException("Initial balance is below the minimum balance");
        }
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.status = "Active";
    }

    public void deposit(double amount) throws InvalidAmountException, InactiveAccountException {
        ensureActive();
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be positive");
        balance += amount;
    }

    public void withdraw(double amount, int suppliedPin) throws AccountException {
        ensureActive();
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive");
        if (!verifyPin(suppliedPin)) throw new InvalidPinException("Invalid or unset PIN");
        if (amount > balance) throw new InsufficientBalanceException("Insufficient balance");
        if (balance - amount < minimumBalance(accountType)) {
            throw new MinimumBalanceViolationException("Withdrawal would violate the minimum balance");
        }
        balance -= amount;
    }

    public void setPin(int pin) {
        if (pin < 1000 || pin > 9999) throw new IllegalArgumentException("PIN must be exactly four digits");
        this.pin = pin;
    }

    public boolean verifyPin(int suppliedPin) { return pin != null && pin == suppliedPin; }
    public boolean hasPin() { return pin != null; }

    public void closeAccount() throws IllegalStateException {
        if (!"Active".equals(status)) throw new IllegalStateException("Account is already inactive");
        status = "Inactive";
    }

    public void reopenAccount() throws IllegalStateException {
        if (!"Inactive".equals(status)) throw new IllegalStateException("Account is already active");
        status = "Active";
    }

    private void ensureActive() throws InactiveAccountException {
        if (!"Active".equals(status)) throw new InactiveAccountException("Account is inactive");
    }

    private static double minimumBalance(String type) { return "Savings".equals(type) ? 500.00 : 1000.00; }

    public int getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    public String getStatus() { return status; }
    public void setName(String name) { this.name = name; }
}
