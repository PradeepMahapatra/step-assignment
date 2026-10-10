package com.gdb.activity18.logging;

import com.gdb.activity18.command.TransactionCommand;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogger {
    protected LogDestination destination;

    public TransactionLogger(LogDestination destination) {
        this.destination = destination;
    }

    public void setDestination(LogDestination destination) {
        this.destination = destination;
    }

    public void log(TransactionCommand cmd) {
        if (destination != null) {
            destination.write(cmd);
        }
    }

    public List<TransactionCommand> readAll() {
        if (destination == null) {
            return new ArrayList<>();
        }
        return destination.readAll();
    }

    public void clear() {
        if (destination != null) {
            destination.clear();
        }
    }

    public String getDestinationName() {
        return destination == null ? "NONE" : destination.getDestinationName();
    }
}
