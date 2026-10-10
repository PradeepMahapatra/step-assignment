package com.gdb.activity19.logging;

import com.gdb.activity19.command.TransactionCommand;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionLog {
    private static final String FILE_PATH = "data/transactions.ser";

    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
        }
    }

    public synchronized void log(TransactionCommand cmd) throws IOException {
        File logFile = new File(FILE_PATH);
        File parentDir = logFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream out = (!logFile.exists() || logFile.length() == 0)
                ? new ObjectOutputStream(new FileOutputStream(logFile))
                : new AppendableObjectOutputStream(new FileOutputStream(logFile, true))) {
            out.writeObject(cmd);
            out.flush();
        }
    }

    public synchronized List<TransactionCommand> readAll() throws IOException, ClassNotFoundException {
        File logFile = new File(FILE_PATH);
        if (!logFile.exists() || logFile.length() == 0) {
            return new ArrayList<>();
        }

        List<TransactionCommand> commands = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(logFile))) {
            while (true) {
                try {
                    Object obj = in.readObject();
                    if (obj instanceof TransactionCommand) {
                        commands.add((TransactionCommand) obj);
                    }
                } catch (EOFException eof) {
                    break;
                }
            }
        }
        return commands;
    }

    public synchronized void clear() {
        File logFile = new File(FILE_PATH);
        if (logFile.exists()) {
            logFile.delete();
        }
    }
}
