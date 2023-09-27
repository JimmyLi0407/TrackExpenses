package com.example.trackexpensesv1.ViewModel;

import android.os.AsyncTask;
import android.util.Log;

import com.example.trackexpensesv1.DataBase.ExpensesRecord;
import com.example.trackexpensesv1.DataBase.IncomeRecord;
import com.example.trackexpensesv1.DataBase.RecordDataBase;

public class InsertAsyncTask extends AsyncTask<String, Integer, String> {
    private RecordDataBase db;
    private IncomeRecord incomeRecord = null;
    private ExpensesRecord expensesRecord = null;

    InsertAsyncTask(RecordDataBase DB, IncomeRecord record) {
        db = DB;
        incomeRecord = record;
    }

    InsertAsyncTask(RecordDataBase DB, ExpensesRecord record) {
        db = DB;
        expensesRecord = record;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (incomeRecord != null) {
            db.recordDao().insertIncomeRecord(incomeRecord);
        } else if (expensesRecord != null) {
            db.recordDao().insertExpensesRecord(expensesRecord);
        } else {
            Log.i("InsertAsyncTask", "error");
        }
        return null;
    }
}
