package com.example.financebudgetingapp.model;

public class Budget {

    private String category;
    private double amount;
    private double budgetLeft;

    public Budget(String category, double amount, double budgetLeft) {
        this.category = category;
        this.amount = amount;
        this.budgetLeft = budgetLeft;
    }

    public double getBudgetLeft() {
        return budgetLeft;
    }

    public void setBudgetLeft(double budgetLeft) {
        this.budgetLeft = budgetLeft;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
