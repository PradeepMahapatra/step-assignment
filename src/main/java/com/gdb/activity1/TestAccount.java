package com.gdb.activity1;

public class TestAccount {

    public void withdrawSuccessTEst(){
        Account first = new Account(1001, "Aarav Sharma", 25, 5000.00, "Savings");
        System.out.printf("Withdraw 1000.00: %s%n", first.withdraw(1000.00));
    }

    public void withdrawFailureTest(){
        Account second = new Account(1002, "Meera Patel", 31, 2500.00, "Current");
        System.out.printf("Withdraw 10000.00: %s%n", second.withdraw(10000.00));
    }

    public static void main(String[] args) {
        Account first = new Account(1001, "Aarav Sharma", 25, 5000.00, "Savings");
        Account second = new Account(1002, "Meera Patel", 31, 2500.00, "Current");

        System.out.println("Activity 1: Account Testing");
        printAccount(first);
        printAccount(second);
        System.out.printf("Deposit 750.00: %s%n", first.deposit(750.00));
        System.out.printf("Deposit -50.00: %s%n", first.deposit(-50.00));
        System.out.printf("Withdraw 1000.00: %s%n", first.withdraw(1000.00));
        System.out.printf("Withdraw 10000.00: %s%n", first.withdraw(10000.00));
        printAccount(first);
    }

    private static void printAccount(Account account) {
        System.out.printf("Account #%d | Name: %s | Age: %d | Type: %s | Status: %s | Balance: Rs %.2f%n",
                account.getAccountNumber(), account.getName(), account.getAge(), account.getAccountType(),
                account.getStatus(), account.getBalance());
    }
}
