package com.gdb.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gdb.domain.AbstractAccount;
import com.gdb.domain.CurrentAccount;
import com.gdb.domain.FixedDepositAccount;
import com.gdb.domain.SalaryAccount;
import com.gdb.domain.SavingsAccount;
import com.gdb.exceptions.AccountException;

public class BankingConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<AbstractAccount> accounts = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Banking Console Application ===");
        boolean running = true;
        while (running) {
            printMenu();
            switch (readInt("Choose an option: ")) {
                case 1 -> createAccount();
                case 2 -> showAccounts();
                case 3 -> deposit();
                case 4 -> withdraw();
                case 5 -> transfer();
                case 6 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
        System.out.println("Thank you for using the Banking Console Application.");
    }

    private static void printMenu() {
        System.out.println("\n1. Create account");
        System.out.println("2. Show accounts");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer");
        System.out.println("6. Exit");
    }

    private static void createAccount() {
        int number = readInt("Account number: ");
        String name = readText("Customer name: ");
        int age = readInt("Age: ");
        double balance = readDouble("Initial balance: ");
        System.out.println("Account type: 1-Savings  2-Current  3-Salary  4-Fixed Deposit");
        int type = readInt("Choose account type: ");

        try {
            AbstractAccount account = switch (type) {
                case 1 -> new SavingsAccount(number, name, age, balance);
                case 2 -> new CurrentAccount(number, name, age, balance);
                case 3 -> new SalaryAccount(number, name, age, balance);
                case 4 -> new FixedDepositAccount(number, name, age, balance);
                default -> null;
            };
            if (account == null) {
                System.out.println("Invalid account type.");
                return;
            }
            account.setPin(readInt("Create a four-digit PIN: "));
            accounts.add(account);
            System.out.println("Account created successfully.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Could not create account: " + exception.getMessage());
        }
    }

    private static void showAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts created.");
            return;
        }
        for (AbstractAccount account : accounts) account.displayAccountInfo();
    }

    private static void deposit() {
        AbstractAccount account = findAccount();
        if (account == null) return;
        try {
            account.deposit(readDouble("Deposit amount: "));
            System.out.println("Deposit successful.");
        } catch (AccountException exception) {
            System.out.println("Deposit failed: " + exception.getMessage());
        }
    }

    private static void withdraw() {
        AbstractAccount account = findAccount();
        if (account == null) return;
        try {
            account.withdraw(readDouble("Withdrawal amount: "), readInt("PIN: "));
            System.out.println("Withdrawal successful.");
        } catch (AccountException exception) {
            System.out.println("Withdrawal failed: " + exception.getMessage());
        }
    }

    private static void transfer() {
        System.out.println("Source account");
        AbstractAccount source = findAccount();
        if (source == null) return;
        System.out.println("Destination account");
        AbstractAccount destination = findAccount();
        if (destination == null) return;
        try {
            double amount = readDouble("Transfer amount: ");
            source.withdraw(amount, readInt("Source PIN: "));
            destination.deposit(amount);
            System.out.println("Transfer successful.");
        } catch (AccountException exception) {
            System.out.println("Transfer failed: " + exception.getMessage());
        }
    }

    private static AbstractAccount findAccount() {
        int number = readInt("Account number: ");
        for (AbstractAccount account : accounts) {
            if (account.getAccountNumber() == number) return account;
        }
        System.out.println("Account not found.");
        return null;
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readText(prompt));
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid amount.");
            }
        }
    }
}