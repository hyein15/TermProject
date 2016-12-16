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

    public String getResult_note(String year, String month, String day){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM myDB", null);
        String date = year+" "+month+" "+day;
        String result = "";

        while (cursor.moveToNext()) {
            String tmp="";
            tmp = cursor.getString(1);

            boolean same = tmp.equals(date);
            if (same == true) {
                result += "[" + cursor.getString(0) + "]" + "\n"
                        + "위치 : "
                        + cursor.getString(7) + "\n"
                        + "카테고리 : "
                        + cursor.getString(4) + "\n"
                        + "기분 : "
                        + cursor.getString(5) + "\n"
                        + "메모 : "
                        + cursor.getString(6) + "\n"
                        + "-----------------------------------------------" + "\n";
            } else {
                continue;
            }
        }
        return result;
        }

    public String getResult_log(String year, String month, String day) {

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

    public long [] time (String year, String month, String day){
        SQLiteDatabase db = getReadableDatabase();
        long time [] =new long[6];
        long time_tem=0;
        String date = year+" "+month+" "+day;
        Cursor cursor = db.rawQuery("SELECT * FROM myDB", null);
        String tmp="";
        while (cursor.moveToNext()) {
            tmp = cursor.getString(1);
            boolean same = tmp.equals(date);
            if (same == true) {
                if(cursor.getString(4).equals("eat")){
                    time_tem = cursor.getLong(3);
                    time[1] = time[1] + time_tem;
                }
                else if(cursor.getString(4).equals("move")){
                    time_tem = cursor.getLong(3);
                    time[2] = time[2] + time_tem;
                }
                else if(cursor.getString(4).equals("study")){
                    time_tem = cursor.getLong(3);
                    time[3] = time[3] + time_tem;
                }
                else if(cursor.getString(4).equals("work")){
                    time_tem = cursor.getLong(3);
                    time[4] = time[4] + time_tem;
                }
                else if(cursor.getString(4).equals("game")){
                    time_tem = cursor.getLong(3);
                    time[5] = time[5] + time_tem;
                }
            }
            else{
                continue;
            }
        }
        return time;
    }

    public int [] doing (String year, String month, String day){
        SQLiteDatabase db = getReadableDatabase();
        int doing [] =new int[6];
        String date = year+" "+month+" "+day;
        Cursor cursor = db.rawQuery("SELECT * FROM myDB", null);
        String tmp="";
        while (cursor.moveToNext()) {
            tmp = cursor.getString(1);
            boolean same = tmp.equals(date);
            if (same == true) {
                if(cursor.getString(4).equals("eat")){
                   doing[1]++;
                }
                else if(cursor.getString(4).equals("move")){
                   doing[2]++;
                }
                else if(cursor.getString(4).equals("study")){
                  doing[3]++;
                }
                else if(cursor.getString(4).equals("work")){
                  doing[4]++;
                }
                else if(cursor.getString(4).equals("game")){
                    doing[5]++;
                }
            }
            else{
                continue;
            }
        }
        return doing;

    }


    public void delete(String number) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM myDB WHERE _id='" + number + "'");

        db.close();
    }


}

