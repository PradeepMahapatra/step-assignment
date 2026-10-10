package com.gdb.activity18.logging;

import com.gdb.activity18.command.TransactionCommand;
import com.gdb.activity18.db.SimulatedDatabase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseLogDestination implements LogDestination {
    private static final String TABLE = "transaction_log";

    private final SimulatedDatabase db;

    public DatabaseLogDestination(SimulatedDatabase db) {
        this.db = db;
    }

    @Override
    public void write(TransactionCommand cmd) {
        db.insert(TABLE, cmd);
    }

    @Override
    public List<TransactionCommand> readAll() {
        List<TransactionCommand> commands = new ArrayList<>();
        for (Object obj : db.selectAll(TABLE)) {
            if (obj instanceof TransactionCommand) {
                commands.add((TransactionCommand) obj);
            }
        }
        return commands;
    }

    @Override
    public void clear() {
        db.deleteAll(TABLE);
    }

    @Override
    public String getDestinationName() {
        return "DATABASE";
    }
}
