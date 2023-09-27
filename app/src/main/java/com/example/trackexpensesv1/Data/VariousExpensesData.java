package com.example.trackexpensesv1.Data;

public class VariousExpensesData {
    private String type;
    private int totalVariousExpenses;

    public VariousExpensesData(String type, int totalVariousExpenses) {
        this.type = type;
        this.totalVariousExpenses = totalVariousExpenses;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTotalVariousExpenses(int totalVariousExpenses) {
        this.totalVariousExpenses = totalVariousExpenses;
    }

    public int getTotalVariousExpenses() {
        return totalVariousExpenses;
    }
}
