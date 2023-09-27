package com.example.trackexpensesv1.DataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "IncomeRecordTable")
public class IncomeRecord {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String yearmonth;
    private String type;
    private String incomeMoney;
    private String date;

    public IncomeRecord(String yearmonth, String type, String incomeMoney, String date) {
        this.yearmonth = yearmonth;
        this.type = type;
        this.incomeMoney = incomeMoney;
        this.date = date;
    }

    @Ignore
    public IncomeRecord(int id, String yearmonth, String type, String incomeMoney, String date) {
        this.id = id;
        this.yearmonth = yearmonth;
        this.type = type;
        this.incomeMoney = incomeMoney;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setIncomeMoney(String incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public String getIncomeMoney() {
        return incomeMoney;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
