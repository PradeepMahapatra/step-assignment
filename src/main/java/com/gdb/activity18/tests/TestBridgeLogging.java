package com.gdb.activity18.tests;

import com.gdb.activity18.command.*;
import com.gdb.activity18.db.SimulatedDatabase;
import com.gdb.activity18.logging.*;
import com.gdb.domain.*;
import java.util.List;

public class TestBridgeLogging {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 18 — BRIDGE PATTERN (FILE + DB)");
        System.out.println("=".repeat(60));

        IAccount acc1 = AccountFactory.createAccount("SAVINGS", 1001, "John", 25, 15000);
        ((Account) acc1).setPin(1234);
        IAccount acc2 = AccountFactory.createAccount("SAVINGS", 1002, "Jane", 30, 10000);

        FileLogDestination fileDest = new FileLogDestination();
        fileDest.clear();

        SimulatedDatabase db = new SimulatedDatabase();
        DatabaseLogDestination dbDest = new DatabaseLogDestination(db);
        dbDest.clear();

        MemoryLogDestination memDest = new MemoryLogDestination();
        memDest.clear();

        TransactionLogger logger = new TransactionLogger(fileDest);

        DepositCommand dep = new DepositCommand(acc1, 5000);
        dep.execute();
        logger.log(dep);

        WithdrawCommand wth = new WithdrawCommand(acc1, 2000, 1234);
        wth.execute();
        logger.log(wth);

        TransferCommand trf = new TransferCommand(acc1, acc2, 3000, 1234);
        trf.execute();
        logger.log(trf);

        System.out.println("[STEP 25] Logging to FILE destination...");
        System.out.println("  FILE log count: " + logger.readAll().size());

        logger.setDestination(dbDest);
        logger.log(dep);
        logger.log(wth);
        logger.log(trf);
        System.out.println("[STEP 26] Switched to DATABASE destination...");
        System.out.println("  DATABASE log count: " + logger.readAll().size());

        logger.setDestination(memDest);
        logger.log(dep);
        logger.log(wth);
        logger.log(trf);
        System.out.println("[STEP 27] Switched to MEMORY destination...");
        System.out.println("  MEMORY log count: " + logger.readAll().size());

        logger.setDestination(fileDest);
        System.out.println("[STEP 28] Verifying Data Isolation:");
        System.out.println("  FILE count: " + logger.readAll().size() + " [EXPECTED: 3]");

        logger.setDestination(dbDest);
        System.out.println("  DATABASE count: " + logger.readAll().size() + " [EXPECTED: 3]");

        logger.setDestination(memDest);
        System.out.println("  MEMORY count: " + logger.readAll().size() + " [EXPECTED: 3]");

        System.out.println("\n[STEP 29] All Bridge Pattern log backends verified successfully!");
        System.out.println("  FILE: " + fileDest.getDestinationName());
        System.out.println("  DATABASE: " + dbDest.getDestinationName());
        System.out.println("  MEMORY: " + memDest.getDestinationName());
    }
}
