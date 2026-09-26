package com.gdb.domain;

import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InsufficientBalanceException;

public class SalaryAccount extends Account {
    private boolean salaryCredited;

    public SalaryAccount(int accountNumber, String name, int age, double initialBalance) {
        super(accountNumber, name, age, initialBalance, "Salary", 0.0);
    }

    public SalaryAccount(int accountNumber, String name, int age, double initialBalance, int tenureYears) {
        super(accountNumber, name, age, initialBalance, "Salary", 0.0, tenureYears);
    }

    @Override
    public void processDebit(double amount) throws AccountException {
        if (amount > balance) throw new InsufficientBalanceException("Insufficient available balance");
        balance -= amount;
    }

    public void creditSalary(double amount) throws AccountException {
        deposit(amount);
        salaryCredited = true;
    }

    public boolean hasSalaryCreditHistory() { return salaryCredited; }
}