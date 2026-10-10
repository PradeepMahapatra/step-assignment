package com.gdb.activity21.repository;

import com.gdb.domain.Transaction;
import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findByAccount(int accountNumber);
    List<Transaction> findAll();
    void clear();
}
