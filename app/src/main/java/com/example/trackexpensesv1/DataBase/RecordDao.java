package com.example.trackexpensesv1.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExpensesRecord(ExpensesRecord record);

    @Query("select SUM(cost) from ExpensesRecordTable")
    LiveData<Integer> getExpensesTotal();

    @Query("select * from ExpensesRecordTable")
    LiveData<List<ExpensesRecord>> getExpensesRecordAll();

    @Query("select SUM(cost) from ExpensesRecordTable where type = :typeValue")
    LiveData<Integer> getTypeExpensesTotal(String typeValue);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIncomeRecord(IncomeRecord record);

    @Query("select SUM(incomeMoney) from IncomeRecordTable")
    LiveData<Integer> getIncomeTotal();

    @Query("select * from IncomeRecordTable")
    LiveData<List<IncomeRecord>> getIncomeRecordsAll();

    @Query("select SUM(incomeMoney) from IncomeRecordTable where type = :typeValue")
    LiveData<Integer> getTypeIncomeTotal(String typeValue);
}
