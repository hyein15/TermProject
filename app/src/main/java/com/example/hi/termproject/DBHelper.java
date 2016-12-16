package com.example.hi.termproject;

import android.content.Context;
import android.database.Cursor;
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

        db.execSQL("CREATE TABLE myDB (_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, time TEXT, exe_time INTEGER , doing TEXT, feel TEXT, memo TEXT, " +
                "address TEXT);");
        Toast.makeText(context, "DB 생성 완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String date, String time, long exe_time, String doing , String feel, String memo, String address) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO myDB VALUES(null, '" + date + "', '"  + time + "', '" + exe_time + "', '"  + doing + "', '"  + feel+ "'" +
                ", '"  + memo + "', '"  + address + "');");
        Toast.makeText(context, "DB 입력 완료", Toast.LENGTH_SHORT).show();
        db.close();
    }


    public String getResult(String year, String month, String day) {

        SQLiteDatabase db = getReadableDatabase();

        String date = year+" "+month+" "+day;
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM myDB", null);
        String tmp="";

        while (cursor.moveToNext()) {

            tmp = cursor.getString(1);
            long tmp_time = cursor.getLong(3);
            long hour = tmp_time/(1000*60*60)%24;
            long min =tmp_time/1000 / 60;
            long sec = (tmp_time/1000)%60; //시,분,초 저장

            boolean same = tmp.equals(date);
            if (same == true) {
                result += "[" + cursor.getString(0) + "]" + "\n"
                        + "위치 : "
                        + cursor.getString(7) + "\n"
                        + "카테고리 : "
                        + cursor.getString(4) + "\n"
                        + "시작시간 : "
                        + cursor.getString(2) + "\n"
                        + "소요시간 : "
                        + hour+"시간"+ min + "분" + sec + "초"+"\n"
                        + "-----------------------------------------------" + "\n";
           }

            else{
                continue;
            }

        }
        return result;
    }


}

