package com.gdb.tests;

import com.gdb.domain.AccountFactory;
import com.gdb.domain.IAccount;

public class TestInterfaceFactory {
    public static void main(String[] args) {
        System.out.println("=== Activity 11: Interface & Factory Pattern Test ===");

        IAccount savings = AccountFactory.createAccount("SAVINGS", 1101, "Rajesh Sharma", 30, 5000);
        IAccount current = AccountFactory.createAccount("CURRENT", 1102, "Priya Patel", 32, 2000);
        IAccount fixedDeposit = AccountFactory.createAccount("FIXED_DEPOSIT", 1103, "Amit Kumar", 40, 10000);
        IAccount salary = AccountFactory.createAccount("SALARY", 1104, "Sneha Verma", 28, 3000);

        System.out.printf("Factory created: SAVINGS account for %s%n", savings.getCustomerName());
        System.out.printf("Factory created: CURRENT account for %s%n", current.getCustomerName());
        System.out.printf("Factory created: FIXED_DEPOSIT account for %s%n", fixedDeposit.getCustomerName());
        System.out.printf("Factory created: SALARY account for %s%n", salary.getCustomerName());
        System.out.println("All accounts successfully created through AccountFactory!");
        System.out.println("Run TestActivity12 for the factory-driven transaction suite.");
    }
}