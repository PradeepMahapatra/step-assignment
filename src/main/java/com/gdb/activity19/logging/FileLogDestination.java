package com.gdb.activity19.logging;

import com.gdb.activity19.command.TransactionCommand;
import java.util.ArrayList;
import java.util.List;

public class FileLogDestination implements LogDestination {
    private final TransactionLog log;

    public FileLogDestination() {
        this.log = new TransactionLog();
    }

    @Override
    public void write(TransactionCommand cmd) {
        try {
            log.log(cmd);
        } catch (Exception e) {
            throw new RuntimeException("Failed to log to file destination", e);
        }
    }

    @Override
    public List<TransactionCommand> readAll() {
        try {
            return log.readAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void clear() {
        log.clear();
    }

    @Override
    public String getDestinationName() {
        return "FILE";
    }
}
