package com.gdb.activity21.repository;

import com.gdb.domain.IAccount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<Integer, IAccount> accounts = new HashMap<>();

    @Override
    public IAccount save(IAccount account) {
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    @Override
    public IAccount findById(int accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public List<IAccount> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public IAccount update(IAccount account) {
        if (!accounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account not found: " + account.getAccountNumber());
        }
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    @Override
    public boolean delete(int accountNumber) {
        return accounts.remove(accountNumber) != null;
    }

    @Override
    public boolean exists(int accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    @Override
    public int nextAccountNumber() {
        if (accounts.isEmpty()) {
            return 1001;
        }
        int max = 0;
        for (Integer accountNumber : accounts.keySet()) {
            if (accountNumber > max) {
                max = accountNumber;
            }
        }
        return max + 1;
    }

    @Override
    public void clear() {
        accounts.clear();
    }
}
