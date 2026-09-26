package com.gdb.domain;

import com.gdb.exceptions.AccountException;

public class FixedDepositAccount extends Account {
    public FixedDepositAccount(int accountNumber, String name, int age, double initialBalance) {
        super(accountNumber, name, age, initialBalance, "Fixed Deposit", 0.0);
    }

    public FixedDepositAccount(int accountNumber, String name, int age, double initialBalance, int tenureYears) {
        super(accountNumber, name, age, initialBalance, "Fixed Deposit", 0.0, tenureYears);
    }

    @Override
    public void processDebit(double amount) throws AccountException {
        throw new AccountException("Premature withdrawal is not permitted for a fixed deposit");
    }
}