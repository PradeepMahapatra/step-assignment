package com.gdb.domain;

import java.util.Locale;
import java.util.Map;

public final class AccountRulesEngine {
    private static final AccountRulesEngine INSTANCE = new AccountRulesEngine();
    private static final Map<String, Double> SAVINGS_MIN = Map.of(
            "new", 10000.0, "standard", 7500.0, "premium", 5000.0, "privilege", 2500.0);
    private static final Map<String, Double> SAVINGS_RATE = Map.of(
            "new", 2.70, "standard", 3.00, "premium", 3.50, "privilege", 4.00);
    private final Map<String, AccountRulesPropertiesLoader> loaders;

    private AccountRulesEngine() {
        loaders = Map.of(
                "savings", new AccountRulesPropertiesLoader("config/rules/savings.properties"),
                "current", new AccountRulesPropertiesLoader("config/rules/current.properties"),
                "fixeddeposit", new AccountRulesPropertiesLoader("config/rules/fixeddeposit.properties"),
                "salary", new AccountRulesPropertiesLoader("config/rules/salary.properties"));
    }

    public static AccountRulesEngine getInstance() { return INSTANCE; }

    public static double getSavingsMinBalance(int tenureYears) {
        String tier = bucket(tenureYears);
        Object configured = getInstance().getAdditionalFeature("Savings", tenureYears, "minimumBalance");
        return configured == null ? SAVINGS_MIN.get(tier) : (Double) configured;
    }

    public static double getSavingsInterestRate(int tenureYears) {
        String tier = bucket(tenureYears);
        Object configured = getInstance().getAdditionalFeature("Savings", tenureYears, "interestRate");
        return configured == null ? SAVINGS_RATE.get(tier) : (Double) configured;
    }

    public static double getCurrentOverdraftLimit(double monthlyTurnover) {
        return Math.max(25000.0, monthlyTurnover * 2.5);
    }

    public static double getFDInterestRate(int months) {
        if (months >= 12) return 6.5;
        if (months >= 6) return 6.0;
        return 5.5;
    }

    public static double getMinimumBalance(String accountType, int tenureYears, double defaultValue) {
        Object value = getInstance().getAdditionalFeature(accountType, tenureYears, "minimumBalance");
        return value == null ? defaultValue : (Double) value;
    }

    public double getDailyTransferLimit(String accountType, int tenureYears) {
        Object value = getAdditionalFeature(accountType, tenureYears, "dailyTransferLimit");
        return value == null ? 0.0 : (Double) value;
    }

    public Object getAdditionalFeature(String accountType, int tenureYears, String feature) {
        String normalized = accountType.toLowerCase(Locale.ROOT).replace(" ", "").replace("_", "");
        AccountRulesPropertiesLoader loader = loaders.get(normalized);
        if (loader == null) return null;
        String key = feature + "." + bucket(tenureYears);
        String value = loader.getProperty(key, null);
        return value == null ? null : loader.getDouble(key, 0.0);
    }

    private static String bucket(int tenureYears) {
        if (tenureYears >= 5) return "privilege";
        if (tenureYears >= 3) return "premium";
        if (tenureYears >= 1) return "standard";
        return "new";
    }
}
