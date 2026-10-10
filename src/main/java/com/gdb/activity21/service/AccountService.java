package com.gdb.activity21.service;

import com.gdb.activity21.repository.AccountRepository;
import com.gdb.activity21.repository.TransactionRepository;
import com.gdb.domain.AbstractAccount;
import com.gdb.domain.Account;
import com.gdb.domain.AccountFactory;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;
import com.gdb.domain.TransactionType;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InvalidAmountException;
import com.gdb.service.TransferService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public IAccount openAccount(String type, String name, int age, double balance) {
        int nextAccountNumber = accountRepository.nextAccountNumber();
        IAccount account = AccountFactory.createAccount(type, nextAccountNumber, name, age, balance);
        accountRepository.save(account);
        return account;
    }

    public void closeAccount(int accountNumber, int pin) throws AccountException {
        IAccount account = getAccount(accountNumber);
        if (!(account instanceof AbstractAccount abstractAccount)) {
            throw new AccountException("Account not found: " + accountNumber);
        }
        abstractAccount.validatePin(pin);
        abstractAccount.closeAccount();
        accountRepository.update(account);
    }

    public Transaction deposit(int accountNumber, double amount)
            throws AccountException, InvalidAmountException, InactiveAccountException {
        IAccount account = getAccount(accountNumber);
        account.deposit(amount);
        Transaction transaction = new Transaction(
                Transaction.generateId(),
                LocalDateTime.now(),
                account.getAccountNumber(),
                TransactionType.DEPOSIT,
                amount,
                account.getBalance(),
                "SUCCESS",
                "Deposit of Rs. " + amount,
                0,
                account.getAccountNumber()
        );
        transactionRepository.save(transaction);
        return transaction;
    }

    public Transaction withdraw(int accountNumber, double amount, int pin) throws AccountException {
        IAccount account = getAccount(accountNumber);
        if (!(account instanceof AbstractAccount abstractAccount)) {
            throw new AccountException("Account not found: " + accountNumber);
        }
        abstractAccount.withdraw(amount, pin);
        Transaction transaction = new Transaction(
                Transaction.generateId(),
                LocalDateTime.now(),
                account.getAccountNumber(),
                TransactionType.WITHDRAW,
                amount,
                account.getBalance(),
                "SUCCESS",
                "Withdrawal of Rs. " + amount,
                account.getAccountNumber(),
                0
        );
        transactionRepository.save(transaction);
        return transaction;
    }

    public Transaction transfer(int fromAccountNumber, int toAccountNumber, double amount, int pin) throws AccountException {
        IAccount fromAccount = getAccount(fromAccountNumber);
        IAccount toAccount = getAccount(toAccountNumber);
        Transaction transaction = TransferService.transferWithTransaction(fromAccount, toAccount, amount, pin);
        transactionRepository.save(transaction);
        return transaction;
    }

    public IAccount getAccount(int accountNumber) throws AccountException {
        IAccount account = accountRepository.findById(accountNumber);
        if (account == null) {
            throw new AccountException("Account not found: " + accountNumber);
        }
        return account;
    }

    public List<IAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }

    public int getNextAccountNumber() {
        return accountRepository.nextAccountNumber();
    }
}
