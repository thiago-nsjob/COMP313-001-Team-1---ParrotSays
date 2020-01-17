package com.jvinix.iy4s.Models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Report.class}, version = 1, exportSchema = false)
@TypeConverters({MyTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase
{

    private static AppDatabase instance;
    private static final String DATABASE_NAME = "ParrotSaysApp";

    public abstract ReportDao reportDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null) {
            //Create database object

            instance = Room.databaseBuilder(context,
                    AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
