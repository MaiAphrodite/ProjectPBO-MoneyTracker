package com.lemai.moneytracker;

import java.util.ArrayList;

public class MoneyTracker {
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    static ArrayList<Category> categories = new ArrayList<>();
    private static Plan currentPlan = null;

    public static void main(String[] args) {
        // Uncomment the one you want to use:
        // launchCLI();
        launchGUI();
    }

    private static void launchGUI() {
        new MoneyTrackerGUI().setVisible(true);
    }

    public static Plan getCurrentPlan() {
        return currentPlan;
    }

    public static void setCurrentPlan(Plan currentPlan) {
        MoneyTracker.currentPlan = currentPlan;
    }

    public static ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public static void setTransactions(ArrayList<Transaction> transactions) {
        MoneyTracker.transactions = transactions;
    }
}
