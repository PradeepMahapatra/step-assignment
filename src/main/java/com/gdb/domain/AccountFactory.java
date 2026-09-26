package com.gdb.domain;

public final class AccountFactory {
    private AccountFactory() { }

    public static IAccount createAccount(String accountType, int accountNumber, String name,
                                         int age, double initialBalance) {
        if (accountType == null) throw new IllegalArgumentException("Account type is required");
        return switch (accountType.trim().toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(accountNumber, name, age, initialBalance);
            case "CURRENT" -> new CurrentAccount(accountNumber, name, age, initialBalance);
            case "FIXED_DEPOSIT", "FD" ->
                    new FixedDepositAccount(accountNumber, name, age, initialBalance);
            case "SALARY" -> new SalaryAccount(accountNumber, name, age, initialBalance);
            default -> throw new IllegalArgumentException("Unknown account type: " + accountType);
        };
    }

    public static IAccount createAccount(String accountType, int accountNumber, String name,
                                         int age, double initialBalance, String pin) {
        IAccount account = createAccount(accountType, accountNumber, name, age, initialBalance);
        if (!(account instanceof AbstractAccount abstractAccount)) {
            throw new IllegalStateException("Factory created an unsupported account implementation");
        }
        try {
            abstractAccount.setPin(Integer.parseInt(pin));
        } catch (NumberFormatException | NullPointerException exception) {
            throw new IllegalArgumentException("PIN must contain four digits", exception);
        }
        return account;
    }

    public static IAccount createAccount(String accountType, int accountNumber, String name,
                                         int age, double initialBalance, int tenureYears) {
        return createAccount(accountType, accountNumber, name, age, initialBalance, tenureYears, null);
    }

    public static IAccount createAccount(String accountType, int accountNumber, String name,
                                         int age, double initialBalance, int tenureYears, String pin) {
        if (accountType == null) throw new IllegalArgumentException("Account type is required");
        IAccount account = switch (accountType.trim().toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(accountNumber, name, age, initialBalance, tenureYears);
            case "CURRENT" -> new CurrentAccount(accountNumber, name, age, initialBalance, tenureYears);
            case "FIXED_DEPOSIT", "FD" -> new FixedDepositAccount(accountNumber, name, age, initialBalance, tenureYears);
            case "SALARY" -> new SalaryAccount(accountNumber, name, age, initialBalance, tenureYears);
            default -> throw new IllegalArgumentException("Unknown account type: " + accountType);
        };
        if (pin != null) ((AbstractAccount) account).setPin(parsePin(pin));
        return account;
    }

    private static int parsePin(String pin) {
        try {
            return Integer.parseInt(pin);
        } catch (NumberFormatException | NullPointerException exception) {
            throw new IllegalArgumentException("PIN must contain four digits", exception);
        }
    }
}