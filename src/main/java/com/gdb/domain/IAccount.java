package com.gdb.domain;

import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InvalidAmountException;

public interface IAccount {
    int getAccountNumber();
    String getCustomerName();
    double getBalance();
    String getAccountType();
    String getStatus();
    void deposit(double amount) throws InvalidAmountException, InactiveAccountException;
    void withdraw(double amount, String pin) throws AccountException;
    void displayAccountInfo();
}