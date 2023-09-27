package com.example.trackexpensesv1.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ExpensesRecord.class, IncomeRecord.class}, version = 1, exportSchema = false)
public abstract class RecordDataBase extends RoomDatabase {
    public abstract RecordDao recordDao();

    private static volatile RecordDataBase instance;

    public static RecordDataBase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RecordDataBase.class, "record.db").build();
        }
        return instance;
    }
}
