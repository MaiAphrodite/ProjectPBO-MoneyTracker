/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lemai.moneytracker;

/**
 *
 * @author Mai
 */
import java.util.ArrayList;

public class Report {
    public static void generateReport(ArrayList<Transaction> transactions) {
        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Income")) {
                totalIncome += t.getAmount();
            } else if (t.getType().equalsIgnoreCase("Expense")) {
                totalExpense += t.getAmount();
            }
        }

        System.out.println("----- Monthly Report -----");
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Balance: " + (totalIncome - totalExpense));
    }
}

