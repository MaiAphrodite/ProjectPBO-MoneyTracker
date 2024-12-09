/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lemai.moneytracker;

import java.io.Serializable;

/**
 *
 * @author Mai
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String date;
    private double amount;
    private String type; // "Income" or "Expense"
    private String description;
    private String category;

    public Transaction(int id, String date, double amount, String type, String category, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String toString() {
        return "ID: " + id + " | Date: " + date + " | Type: " + type + " | Amount: " + amount + 
               " | Category: " + category + " | Description: " + description;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
    return description;
    }
}

