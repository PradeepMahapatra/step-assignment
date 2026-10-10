package com.gdb.activity17.tests;

import com.gdb.activity17.command.*;
import com.gdb.activity17.logging.TransactionLog;
import com.gdb.domain.*;
import java.util.List;

public class TestCommandLogging {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 17 — COMMAND PATTERN + FILE LOGGING");
        System.out.println("=".repeat(60));

        TransactionLog log = new TransactionLog();
        log.clear();

        IAccount acc1 = AccountFactory.createAccount("SAVINGS", 1001, "John Doe", 25, 15000);
        ((Account) acc1).setPin(1234);
        IAccount acc2 = AccountFactory.createAccount("SAVINGS", 1002, "Jane Smith", 30, 10000);

        DepositCommand depCmd = new DepositCommand(acc1, 5000);
        depCmd.execute();
        log.log(depCmd);
        System.out.println("[STEP 11] Logged: " + depCmd.getTransaction());

        WithdrawCommand wthCmd = new WithdrawCommand(acc1, 2000, 1234);
        wthCmd.execute();
        log.log(wthCmd);
        System.out.println("[STEP 12] Logged: " + wthCmd.getTransaction());

        TransferCommand trfCmd = new TransferCommand(acc1, acc2, 3000, 1234);
        trfCmd.execute();
        log.log(trfCmd);
        System.out.println("[STEP 13] Logged: " + trfCmd.getTransaction());

        List<TransactionCommand> history = log.readAll();
        System.out.println("\n[STEP 14] Read " + history.size() + " commands from transaction log:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + history.get(i).getTransaction());
        }

        TransactionLog freshLog = new TransactionLog();
        List<TransactionCommand> persisted = freshLog.readAll();
        if (persisted.size() == history.size()) {
            System.out.println("\n[STEP 15] Fresh reader verified " + persisted.size() + " persisted commands [PASS]");
        } else {
            throw new IllegalStateException("Persisted count mismatch: expected " + history.size() + " but found " + persisted.size());
        }
    }
}
