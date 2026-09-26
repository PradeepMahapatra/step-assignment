# Account Management Activities

Maven Java project implementing Activities 1 through 16. Each activity has a runnable entry point, while the shared account domain and service implementation lives under `com.gdb.domain` and `com.gdb.service`.

## Structure

- `com.gdb.activity1`: Boolean-return `Account` and `TestAccount`
- `com.gdb.activity2`: Basic account operation runner
- `com.gdb.activity3`: Validating `AccountEnhanced` and `TestAccountEnhanced`
- `com.gdb.activity4`: Validation and PIN security runner
- `com.gdb.activity5`: Exception hierarchy, exception-based `Account`, and `TestAccountExceptions`
- `com.gdb.activity6`: Exception-handling runner
- `com.gdb.activity7`: Account lifecycle runner
- `com.gdb.domain`: `AbstractAccount` template-method base, `SavingsAccount`, `CurrentAccount`, `SalaryAccount`, and `FixedDepositAccount`
- `com.gdb.domain`: `IAccount` interface and `AccountFactory`
- `com.gdb.exceptions`: Shared exception hierarchy for the subclass model
- `com.gdb.activity8`: `TestAccountSubclasses`
- `com.gdb.activity9`: Abstract account and template-method runner
- `com.gdb.activity10`: Banking operations runner
- `com.gdb.activity11`: Interface and factory runner
- `com.gdb.activity12`: Factory-driven transaction runner
- `com.gdb.activity13`: Account rules engine runners for Activities 13.1 and 13.2
- `com.gdb.activity14`: Properties-driven rules engine runner
- `com.gdb.activity15`: Transfer and daily-limit runner
- `com.gdb.activity16`: Transaction model runner
- `com.gdb.tests`: `TestAbstractAccount` for portfolio transfers and monthly processing
- `com.gdb.tests`: `TestActivity9` for the abstract account template-method demonstration
- `com.gdb.tests`: `TestInterfaceFactory` for Activity 11 and `TestActivity12` for Activity 12
- `com.gdb.tests`: `TestActivity1` through `TestActivity12` and `TestAllActivities` for individual or complete runs
- `com.gdb.tests`: `BankingConsoleApp` for interactive account creation and transactions
- `com.gdb.domain`: `AccountRulesEngine`, external properties loader, transaction model, and transaction type
- `com.gdb.service`: `TransferService` with legacy and transaction-returning APIs

## Build

From the project root:

```text
mvn clean test
```

The project targets Java 17. In IntelliJ IDEA, open the project by selecting `pom.xml` and run any test class with its `main` method.

If Maven is not installed, compile directly with a JDK:

```text
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
New-Item -ItemType Directory -Force target/classes | Out-Null
javac -d target/classes $files
Copy-Item -Recurse -Force src/main/resources/* target/classes
```

The Activity 10 runner demonstrates secure transfers, failed-transfer rollback behavior, and the monthly account cycle.

Activities 11 and 12 use the interface and factory layer:

```text
java -cp target/classes com.gdb.tests.TestInterfaceFactory
```

For interactive use, run `com.gdb.tests.BankingConsoleApp`. The activity test classes intentionally use fixed values so their expected results can be checked consistently; the console app accepts values through `Scanner`.

To run Activity 1 from the project root:

```text
javac -d target/classes src/main/java/com/gdb/activity1/Account.java src/main/java/com/gdb/activity1/TestAccount.java
java -cp target/classes com.gdb.activity1.TestAccount
```

Do not run `javac TestAccount.java` from inside `src/main/java/com/gdb/activity1`; the package root must be the project root with the output directory supplied.
