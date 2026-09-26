package com.gdb.activity3;

public class AccountEnhanced {
    private final int accountNumber;
    private String name;
    private int age;
    private double balance;
    private final String accountType;
    private String status;
    private Integer pin;

    public AccountEnhanced(int accountNumber, String name, int age, double initialBalance, String accountType) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = Math.max(age, 18);
        this.accountType = isValidType(accountType) ? accountType : "Savings";
        this.balance = Math.max(initialBalance, getMinimumBalance());
        this.status = "Active";
    }

    public boolean deposit(double amount) {
        if (!isActive() || amount <= 0) return false;
        balance += amount;
        return true;
    }

    public boolean withdraw(double amount, int suppliedPin) {
        if (!isActive() || amount <= 0 || !verifyPin(suppliedPin)
                || balance - amount < getMinimumBalance()) return false;
        balance -= amount;
        return true;
    }

    public boolean setPin(int pin) {
        if (pin < 1000 || pin > 9999) return false;
        this.pin = pin;
        return true;
    }

    public boolean verifyPin(int pin) { return this.pin != null && this.pin == pin; }
    public boolean hasPin() { return pin != null; }

    public boolean closeAccount() {
        if (!isActive()) return false;
        status = "Inactive";
        return true;
    }

    public boolean reopenAccount() {
        if (isActive()) return false;
        status = "Active";
        return true;
    }

    private boolean isActive() { return "Active".equals(status); }
    private boolean isValidType(String type) { return "Savings".equals(type) || "Current".equals(type); }
    private double getMinimumBalance() { return "Savings".equals(accountType) ? 500.00 : 1000.00; }

    public int getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    public String getStatus() { return status; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
}
