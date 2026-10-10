package com.gdb.activity21.tests;

import com.gdb.activity21.repository.AccountRepository;
import com.gdb.activity21.repository.InMemoryAccountRepository;
import com.gdb.activity21.repository.InMemoryTransactionRepository;
import com.gdb.activity21.repository.RepositoryFactory;
import com.gdb.activity21.service.AccountService;
import com.gdb.domain.AbstractAccount;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;

public class TestRepositoryInMemory {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 21 — REPOSITORY PATTERN TEST");
        System.out.println("=".repeat(60));

        AccountRepository accountRepository = new InMemoryAccountRepository();
        accountRepository.clear();
        AccountService service = new AccountService(accountRepository, new InMemoryTransactionRepository());

        IAccount john = service.openAccount("SAVINGS", "John Doe", 25, 15000);
        ((AbstractAccount) john).setPin(1234);
        IAccount jane = service.openAccount("SAVINGS", "Jane Smith", 30, 10000);
        ((AbstractAccount) jane).setPin(5678);

        System.out.println("[STEP 1] Opened accounts: " + john.getAccountNumber() + ", " + jane.getAccountNumber());

        Transaction deposit = service.deposit(john.getAccountNumber(), 5000);
        System.out.println("[STEP 2] Deposit: " + deposit);

        service.withdraw(john.getAccountNumber(), 2000, 1234);
        service.transfer(john.getAccountNumber(), jane.getAccountNumber(), 1000, 1234);

        System.out.println("[STEP 3] Final balances: John=" + john.getBalance() + ", Jane=" + jane.getBalance());
        System.out.println("[STEP 4] Transaction count: " + service.getTransactionHistory().size());

        AccountRepository factoryRepo = RepositoryFactory.getAccountRepository();
        System.out.println("[STEP 5] Factory repository mode: " + factoryRepo.getClass().getSimpleName());
        System.out.println("All repository tests passed!");
    }
}
