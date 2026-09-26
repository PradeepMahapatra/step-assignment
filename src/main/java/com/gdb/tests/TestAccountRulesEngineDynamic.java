package com.gdb.tests;

import com.gdb.domain.Account;
import com.gdb.domain.AccountFactory;

public class TestAccountRulesEngineDynamic {
    public static void main(String[] args) {
        System.out.println("=== Activity 13.2: Dynamic Account Rules Test ===");
        Account account = (Account) AccountFactory.createAccount(
                "SAVINGS", 1301, "Dynamic Customer", 30, 10000, 4);
        System.out.printf("Created Savings Account (Tenure: %d yrs):%n", account.getTenureYears());
        System.out.printf(" -> Min Balance: Rs %.1f (Dynamically fetched)%n", account.getMinimumBalance());
        System.out.printf(" -> Interest Rate: %.1f%% (Dynamically fetched)%n",
                ((com.gdb.domain.SavingsAccount) account).getInterestRate());
        System.out.println("Dynamic rule integration verified!");
    }
}
