package com.example.trackexpensesv1.Data;

public class VariousIncomeData {
    private String type;
    private int totalVariousIncome;

    public VariousIncomeData(String type, int totalVariousIncome) {
        this.type = type;
        this.totalVariousIncome = totalVariousIncome;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTotalVariousIncome(int totalVariousIncome) {
        this.totalVariousIncome = totalVariousIncome;
    }

    public int getTotalVariousIncome() {
        return totalVariousIncome;
    }
}
