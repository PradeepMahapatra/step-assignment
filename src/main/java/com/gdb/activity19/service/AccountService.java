package com.gdb.activity19.service;

import com.gdb.activity19.command.DepositCommand;
import com.gdb.activity19.command.TransactionCommand;
import com.gdb.activity19.command.TransferCommand;
import com.gdb.activity19.command.WithdrawCommand;
import com.gdb.activity19.logging.TransactionLogger;
import com.gdb.domain.AbstractAccount;
import com.gdb.domain.AccountFactory;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InvalidAmountException;
import com.gdb.exceptions.InvalidPinException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {
    private final Map<Integer, IAccount> accounts = new HashMap<>();
    private final TransactionLogger logger;
    private int nextAccountNumber = 1001;

    public AccountService(TransactionLogger logger) {
        this.logger = logger;
    }

    public IAccount openAccount(String type, String name, int age, double balance) {
        IAccount account = AccountFactory.createAccount(type, nextAccountNumber, name, age, balance);
        accounts.put(account.getAccountNumber(), account);
        nextAccountNumber++;
        return account;
    }

    public void closeAccount(int accountNumber, int pin) throws AccountException {
        IAccount account = getAccount(accountNumber);
        if (!(account instanceof AbstractAccount abstractAccount)) {
            throw new AccountException("Account not found: " + accountNumber);
        }
        abstractAccount.validatePin(pin);
        abstractAccount.closeAccount();
    }

    public Transaction deposit(int accountNumber, double amount) throws Exception {
        IAccount account = getAccount(accountNumber);
        DepositCommand command = new DepositCommand(account, amount);
        command.execute();
        logger.log(command);
        return command.getTransaction();
    }

    public Transaction withdraw(int accountNumber, double amount, int pin) throws Exception {
        IAccount account = getAccount(accountNumber);
        WithdrawCommand command = new WithdrawCommand(account, amount, pin);
        command.execute();
        logger.log(command);
        return command.getTransaction();
    }

    public Transaction transfer(int fromAccountNumber, int toAccountNumber, double amount, int pin)
            throws Exception {
        IAccount fromAccount = getAccount(fromAccountNumber);
        IAccount toAccount = getAccount(toAccountNumber);
        TransferCommand command = new TransferCommand(fromAccount, toAccount, amount, pin);
        command.execute();
        logger.log(command);
        return command.getTransaction();
    }

    public IAccount getAccount(int accountNumber) throws AccountException {
        IAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountException("Account not found: " + accountNumber);
        }
        return account;
    }

    public List<IAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public List<TransactionCommand> getTransactionHistory() {
        return logger.readAll();
    }

    public int getNextAccountNumber() {
        return nextAccountNumber;
    }
}
