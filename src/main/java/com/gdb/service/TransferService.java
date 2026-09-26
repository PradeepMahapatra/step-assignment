package com.gdb.service;

import com.gdb.domain.Account;
import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;
import com.gdb.domain.TransactionType;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.exceptions.InvalidPinException;

public final class TransferService {
    private TransferService() { }

    public static void transfer(IAccount from, IAccount to, double amount, int pin) throws AccountException {
        if (from == null || to == null) {
            throw new AccountException("Source and destination accounts are required");
        }
        if (!(from instanceof Account) || !(to instanceof Account)) {
            throw new InactiveAccountException("Both accounts must be active to transfer funds");
        }
        Account source = (Account) from;
        Account destination = (Account) to;
        if (!source.isActive() || !destination.isActive()) {
            throw new InactiveAccountException("Both accounts must be active to transfer funds");
        }
        if (!source.verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }
        if (!source.canWithdraw(amount)) {
            throw new InsufficientBalanceException("Insufficient balance for transfer of Rs. " + amount);
        }
        source.resetDailyTransferIfNeeded();
        if (!source.canTransfer(amount)) {
            throw new AccountException("Daily transfer limit exceeded. Remaining today: Rs. "
                    + source.getRemainingDailyTransferLimit());
        }
        source.withdraw(amount, pin);
        destination.deposit(amount);
        source.updateDailyTransferTotal(amount);
    }

    public static Transaction transferWithTransaction(IAccount from, IAccount to,
                                                       double amount, int pin) throws AccountException {
        transfer(from, to, amount, pin);
        Account source = (Account) from;
        Account destination = (Account) to;
        return new Transaction(Transaction.generateId(), java.time.LocalDateTime.now(),
                source.getAccountNumber(), TransactionType.TRANSFER, amount, source.getBalance(),
                "SUCCESS", "Transfer of " + amount + " to Account #" + destination.getAccountNumber(),
                source.getAccountNumber(), destination.getAccountNumber());
    }
}
