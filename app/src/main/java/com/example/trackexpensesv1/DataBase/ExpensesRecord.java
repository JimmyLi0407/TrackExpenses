package com.example.trackexpensesv1.DataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ExpensesRecordTable")
public class ExpensesRecord {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String yearmonth;
    private String type;
    private String cost;
    private String date;

    public ExpensesRecord(String yearmonth, String type, String cost, String date) {
        this.yearmonth = yearmonth;
        this.type = type;
        this.cost = cost;
        this.date = date;
    }

    @Ignore
    public ExpensesRecord(int id, String yearmonth, String type, String cost, String date) {
        this.id = id;
        this.yearmonth = yearmonth;
        this.type = type;
        this.cost = cost;
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

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCost() {
        return cost;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
