package com.gdb.activity21;

import com.gdb.activity21.repository.AccountRepository;
import com.gdb.activity21.repository.InMemoryAccountRepository;
import com.gdb.activity21.repository.InMemoryTransactionRepository;
import com.gdb.activity21.service.AccountService;
import com.gdb.domain.IAccount;

public class Main {
    public static void main(String[] args) throws Exception {
        AccountRepository accountRepository = new InMemoryAccountRepository();
        AccountService service = new AccountService(accountRepository, new InMemoryTransactionRepository());

        IAccount john = service.openAccount("SAVINGS", "John Doe", 25, 15000);
        ((com.gdb.domain.AbstractAccount) john).setPin(1234);
        IAccount jane = service.openAccount("SAVINGS", "Jane Smith", 30, 10000);
        ((com.gdb.domain.AbstractAccount) jane).setPin(5678);

        service.deposit(john.getAccountNumber(), 5000);
        service.withdraw(john.getAccountNumber(), 2000, 1234);
        service.transfer(john.getAccountNumber(), jane.getAccountNumber(), 1000, 1234);

        System.out.println("Accounts: " + service.getAllAccounts().size());
        System.out.println("Transactions: " + service.getTransactionHistory().size());
    }
}
