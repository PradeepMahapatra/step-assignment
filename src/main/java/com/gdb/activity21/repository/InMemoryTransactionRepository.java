package com.gdb.activity21.repository;

import com.gdb.domain.Transaction;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findByAccount(int accountNumber) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber() == accountNumber
                    || transaction.getFromAccount() == accountNumber
                    || transaction.getToAccount() == accountNumber) {
                result.add(transaction);
            }
        }
        return result;
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public void clear() {
        transactions.clear();
    }
}
