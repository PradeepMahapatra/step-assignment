package com.gdb.tests;

import com.gdb.domain.CurrentAccount;
import com.gdb.domain.FixedDepositAccount;
import com.gdb.domain.SalaryAccount;
import com.gdb.domain.SavingsAccount;
import com.gdb.exceptions.AccountException;

public class TestActivity9 {
    public static void main(String[] args) throws AccountException {
        System.out.println("=== Activity 9: Abstract Account & Template Pattern ===");

        SavingsAccount savings = new SavingsAccount(9001, "Aarav Sharma", 25, 10000);
        savings.setPin(1111);
        savings.withdraw(2000, 1111);
        System.out.printf("[Savings] Withdraw 2000: SUCCESS | Balance: Rs %.1f%n", savings.getBalance());

        try {
            savings.withdraw(7600, 1111);
            System.out.println("[Savings] Withdraw below min balance: FAIL");
        } catch (AccountException exception) {
            System.out.println("[Savings] Withdraw below min balance: Caught "
                    + exception.getClass().getSimpleName() + " [PASS]");
        }

        CurrentAccount current = new CurrentAccount(9002, "Meera Patel", 31, 2000);
        current.setPin(2222);
        current.withdraw(5000, 2222);
        System.out.printf("[Current] Overdraft debit: SUCCESS | Balance: Rs %.1f%n", current.getBalance());

        FixedDepositAccount fixedDeposit = new FixedDepositAccount(9003, "Rohan Mehta", 29, 10000);
        fixedDeposit.setPin(3333);
        try {
            fixedDeposit.withdraw(1000, 3333);
            System.out.println("[FixedDeposit] Premature debit: FAIL");
        } catch (AccountException exception) {
            System.out.println("[FixedDeposit] Premature debit: Caught AccountException [PASS]");
        }

        SalaryAccount salary = new SalaryAccount(9004, "Nisha Singh", 28, 3000);
        salary.creditSalary(2000);
        System.out.println("[Salary] Credit history: " + salary.hasSalaryCreditHistory() + " [PASS]");
        System.out.println("Template method pattern executed successfully!");
    }
}