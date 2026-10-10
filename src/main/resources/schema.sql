CREATE TABLE IF NOT EXISTS accounts (
    account_number INTEGER PRIMARY KEY,
    customer_name TEXT NOT NULL,
    age INTEGER NOT NULL,
    account_type TEXT NOT NULL,
    balance REAL NOT NULL,
    status TEXT NOT NULL,
    minimum_balance REAL NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id TEXT PRIMARY KEY,
    account_number INTEGER NOT NULL,
    type TEXT NOT NULL,
    amount REAL NOT NULL,
    balance_after REAL NOT NULL,
    status TEXT NOT NULL,
    description TEXT,
    from_account INTEGER,
    to_account INTEGER,
    created_at TEXT NOT NULL
);
