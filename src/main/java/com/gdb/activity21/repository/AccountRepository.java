package com.gdb.activity21.repository;

import com.gdb.domain.IAccount;
import java.util.List;

public interface AccountRepository {
    IAccount save(IAccount account);
    IAccount findById(int accountNumber);
    List<IAccount> findAll();
    IAccount update(IAccount account);
    boolean delete(int accountNumber);
    boolean exists(int accountNumber);
    int nextAccountNumber();
    void clear();
}
