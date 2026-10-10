package com.gdb.activity18.logging;

import com.gdb.activity18.command.TransactionCommand;
import java.util.List;

public interface LogDestination {
    void write(TransactionCommand cmd);
    List<TransactionCommand> readAll();
    void clear();
    String getDestinationName();
}
