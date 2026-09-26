package com.gdb.tests;

import com.gdb.domain.AccountRulesEngine;

public class TestAccountRulesEngine {
    public static void main(String[] args) {
        System.out.println("=== Activity 13.1: Account Rules Engine Test ===");
        int[] tenures = {0, 2, 4, 6};
        for (int tenure : tenures) {
            System.out.printf("Tenure %d yrs -> Min Balance: Rs %.1f | Interest: %.1f%%%n",
                    tenure, AccountRulesEngine.getSavingsMinBalance(tenure),
                    AccountRulesEngine.getSavingsInterestRate(tenure));
        }
        System.out.println("Rules Engine lookup completed successfully!");
    }
}
