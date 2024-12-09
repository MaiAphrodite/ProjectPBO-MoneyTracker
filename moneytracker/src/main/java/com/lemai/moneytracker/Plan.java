package com.lemai.moneytracker;

import java.io.Serializable;

public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;
    private String month; // Format: "YYYY-MM"
    private double targetAmount;
    private double progress;

    public Plan(String month, double targetAmount) {
        this.month = month;
        this.targetAmount = targetAmount;
        this.progress = 0;
    }

    public String getMonth() {
        return month;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getProgress() {
        return progress;
    }

    public void addExpense(double amount) {
        progress += amount;
    }

    public boolean isExceeded() {
        return progress > targetAmount;
    }

    public void showPlan() {
        System.out.println("Plan for Month: " + month);
        System.out.println("Target Amount: " + targetAmount);
        System.out.println("Current Progress (Expenses): " + progress);
        System.out.println("Remaining Budget: " + (targetAmount - progress));
        if (isExceeded()) {
            System.out.println("WARNING: Expenses have exceeded the target plan!");
        }
    }
}
