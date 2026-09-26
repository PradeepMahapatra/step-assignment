package com.gdb.domain;

import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InvalidAmountException;
import com.gdb.exceptions.InvalidPinException;

public abstract class AbstractAccount implements IAccount {
    private final int accountNumber;
    private String name;
    private final int age;
    protected double balance;
    private final String accountType;
    private String status;
    private Integer pin;
    private final double minimumBalance;

    protected AbstractAccount(int accountNumber, String name, int age, double initialBalance,
                              String accountType, double minimumBalance) {
        if (age < 18) throw new IllegalArgumentException("Age must be at least 18");
        if (initialBalance < minimumBalance) {
            throw new IllegalArgumentException("Initial balance is below the minimum balance");
        }
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.status = "Active";
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException, InactiveAccountException {
        ensureActive();
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be positive");
        balance += amount;
    }

    public final void withdraw(double amount, int suppliedPin) throws AccountException {
        validatePin(suppliedPin);
        ensureActive();
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive");
        processDebit(amount);
    }

    @Override
    public final void withdraw(double amount, String suppliedPin) throws AccountException {
        final int numericPin;
        try {
            numericPin = Integer.parseInt(suppliedPin);
        } catch (NumberFormatException | NullPointerException exception) {
            throw new InvalidPinException("PIN must contain four digits");
        }
        withdraw(amount, numericPin);
    }

    public abstract void processDebit(double amount) throws AccountException;

    public void validatePin(int suppliedPin) throws InvalidPinException {
        if (!verifyPin(suppliedPin)) throw new InvalidPinException("Invalid or unset PIN");
    }

    public void setPin(int pin) {
        if (pin < 1000 || pin > 9999) throw new IllegalArgumentException("PIN must be exactly four digits");
        this.pin = pin;
    }

    public void changePin(int currentPin, int newPin) throws InvalidPinException {
        validatePin(currentPin);
        if (newPin < 1000 || newPin > 9999) {
            throw new IllegalArgumentException("PIN must be exactly four digits");
        }
        pin = newPin;
    }

    public boolean verifyPin(int suppliedPin) { return pin != null && pin == suppliedPin; }
    public boolean hasPin() { return pin != null; }

    @Override
    public void displayAccountInfo() {
        System.out.printf("Account #%d | Name: %s | Age: %d | Type: %s | Status: %s | Balance: Rs %.2f%n",
                accountNumber, name, age, accountType, status, balance);
    }

    public void closeAccount() {
        if (!"Active".equals(status)) throw new IllegalStateException("Account is already inactive");
        status = "Inactive";
    }

    public void reopenAccount() {
        if (!"Inactive".equals(status)) throw new IllegalStateException("Account is already active");
        status = "Active";
    }

    protected final void ensureActive() throws InactiveAccountException {
        if (!"Active".equals(status)) throw new InactiveAccountException("Account is inactive");
    }

    @Override
    public int getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    @Override
    public String getCustomerName() { return name; }
    public int getAge() { return age; }
    @Override
    public double getBalance() { return balance; }
    @Override
    public String getAccountType() { return accountType; }
    @Override
    public String getStatus() { return status; }
    public double getMinimumBalance() { return minimumBalance; }
    public void setName(String name) { this.name = name; }
}