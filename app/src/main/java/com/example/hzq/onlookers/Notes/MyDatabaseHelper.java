package com.example.hzq.onlookers.Notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE = "notes";
    public static final String ID = "_id";
    public static final String TITLE ="title";
    public static final String CONTENT = "content";
    public static final String TIME = "time";
    public MyDatabaseHelper(Context context) {
        super(context,"notepad.db",null,1);
    }

    public static final String CREATE_BOOK =
            "CREATE TABLE "+TABLE+ "( "+ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    TITLE +" VARCHAR(30) ,"+
                    CONTENT + " TEXT , "+
                    TIME + " DATETIME NOT NULL )";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

    }
}

