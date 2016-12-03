package com.example.hi.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by hi on 2016-12-03.
 */

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE mylogger (_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, time TEXT, exe_time INTEGER , doing TEXT, feel TEXT, memo TEXT, " +
                "lat INTEGER, long INTEGER);");
        Toast.makeText(context, "DB 생성 완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}


