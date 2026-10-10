package com.gdb.activity20.ui;

import com.gdb.activity19.command.TransactionCommand;
import com.gdb.activity19.service.AccountService;
import com.gdb.domain.AbstractAccount;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;
import com.gdb.exceptions.AccountException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class AccountUI {
    private final AccountService service;
    private final Scanner scanner;

    public AccountUI(AccountService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            displayMainMenu();
            int choice = readInt("Enter your choice: ");
            try {
                switch (choice) {
                    case 1 -> handleOpenAccount();
                    case 2 -> handleDeposit();
                    case 3 -> handleWithdraw();
                    case 4 -> handleTransfer();
                    case 5 -> handleCloseAccount();
                    case 6 -> handleViewAccount();
                    case 7 -> handleViewTransactions();
                    case 8 -> {
                        System.out.println("Thank you! Goodbye.");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please enter 1-8.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("        GLOBAL DIGITAL BANK - MENU");
        System.out.println("========================================");
        System.out.println("1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Close Account");
        System.out.println("6. View Account");
        System.out.println("7. View Transactions");
        System.out.println("8. Exit");
        System.out.println("========================================");
    }

    private void handleOpenAccount() throws Exception {
        String type = readString("Account Type (SAVINGS/CURRENT/FIXED_DEPOSIT/SALARY): ");
        String name = readString("Name: ");
        int age = readInt("Age: ");
        double amount = readDouble("Initial Balance: ");

        IAccount account = service.openAccount(type, name, age, amount);
        System.out.println("SUCCESS: Account created.");
        account.displayAccountInfo();

        int pin = readInt("Set 4-digit PIN: ");
        ((AbstractAccount) account).setPin(pin);
        System.out.println("SUCCESS: PIN configured for account #" + account.getAccountNumber());
    }

    private void handleDeposit() throws Exception {
        int accountNumber = readInt("Account Number: ");
        double amount = readDouble("Amount to deposit: ");
        Transaction txn = service.deposit(accountNumber, amount);
        System.out.println("SUCCESS: " + txn);
    }

    private void handleWithdraw() throws Exception {
        int accountNumber = readInt("Account Number: ");
        double amount = readDouble("Amount to withdraw: ");
        int pin = readInt("PIN: ");
        Transaction txn = service.withdraw(accountNumber, amount, pin);
        System.out.println("SUCCESS: " + txn);
    }

    private void handleTransfer() throws Exception {
        int fromAccount = readInt("From Account: ");
        int toAccount = readInt("To Account: ");
        double amount = readDouble("Amount: ");
        int pin = readInt("PIN: ");
        Transaction txn = service.transfer(fromAccount, toAccount, amount, pin);
        System.out.println("SUCCESS: " + txn);
    }

    private void handleCloseAccount() throws Exception {
        int accountNumber = readInt("Account Number: ");
        int pin = readInt("PIN: ");
        service.closeAccount(accountNumber, pin);
        System.out.println("SUCCESS: Account #" + accountNumber + " has been closed.");
    }

    private void handleViewAccount() throws AccountException {
        int accountNumber = readInt("Account Number: ");
        IAccount account = service.getAccount(accountNumber);
        account.displayAccountInfo();
    }

    private void handleViewTransactions() {
        List<TransactionCommand> history = service.getTransactionHistory();
        if (history == null || history.isEmpty()) {
            System.out.println("No transactions logged.");
            return;
        }

        System.out.println("Transaction History:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i).getTransaction());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                return Double.parseDouble(raw);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Value cannot be empty.");
        }
    }
}
