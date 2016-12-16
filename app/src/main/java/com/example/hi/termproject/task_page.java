package com.example.hi.termproject;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hi on 2016-11-27.
 */
public class task_page extends AppCompatActivity {

    private GpsInfo gps;

    TextView myOutput;
    Button myBtnStart;
    Button myBtnReset;
    Button save;
    RadioGroup rg;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;
    static double longitude ;
    static double latitude ;
    static String address="";
    static long exe_time =0;
    static String doing = "";
    static  String feel = "";
    static String memo_str= "";
    int cur_Status = Init;
    int myCount=1;
    long myBaseTime;
    long myPauseTime;
    DBHelper helper;
    SQLiteDatabase db;
    long outTime=0;
    long now=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_page);

        helper = new DBHelper(this, "mylogger.db" ,null, 1); //db생성
        db = helper.getWritableDatabase();

        myOutput = (TextView) findViewById(R.id.time_out);
        myBtnStart = (Button) findViewById(R.id.btn_start);
        myBtnReset = (Button) findViewById(R.id.btn_reset);
        save = (Button) findViewById(R.id.save);
        rg= (RadioGroup) findViewById(R.id.doing);


        EditText etDate = (EditText) findViewById(R.id.date);
        EditText etTime = (EditText) findViewById(R.id.time);
        final EditText memo = (EditText)findViewById(R.id.memo);

        final long[] now = {System.currentTimeMillis()};
        final Date date = new Date(now[0]);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH시 mm분");

        final String str_date = simpleDateFormat.format(date); //2016년12월03일
        final String str_time = simpleTimeFormat.format(date);
        etDate.setText(simpleDateFormat.format(date));
        etTime.setText(simpleTimeFormat.format(date));


        findViewById(R.id.eat).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                printChecked_1(v);
            }
        });

        findViewById(R.id.move).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                printChecked_1(v);
            }
        });

        findViewById(R.id.study).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                printChecked_1(v);
            }
        });

        findViewById(R.id.work).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                printChecked_1(v);
            }
        });

        findViewById(R.id.game).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                printChecked_1(v);
            }
        });
        //--------------------------------------------
        findViewById(R.id.good).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                printChecked_2(v);
            }
        });
        findViewById(R.id.soso).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                printChecked_2(v);
            }
        });
        findViewById(R.id.bad).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                printChecked_2(v);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DBHelper dbHelper = new DBHelper(getApplicationContext(), "myDB.db", null, 1);

                memo_str = memo.getText().toString(); //메모 저장

                gps = new GpsInfo(task_page.this);

                if (gps.isGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude(); //경도위도저장
                    address = gps.getAddress();


                } else {
                    gps.showSettingsAlert();
                }


                Toast.makeText(task_page.this, "날짜: " + str_date+"\n" +"시간: " + str_time+"\n"+
                        "수행시간: " + exe_time+"\n"+ "한 일: " +doing+"\n"+ "기분: "+ feel+"\n"+ "메모:" + memo_str+"\n"+
                        "위치: " + address, Toast.LENGTH_LONG).show();

                dbHelper.insert(str_date, str_time , exe_time , doing, feel, memo_str,address);

                myOutput.setText("00:00:00:00");
               memo.setText(null);



            }
        });

    }

    public void printChecked_1(View v){
        RadioButton ra = (RadioButton) v;

        if (ra.isChecked()){
            doing = ra.getText().toString(); // 어떤 일 했는지 저장
        }
    }

    public void printChecked_2(View v){
        RadioButton ra = (RadioButton) v;

        if (ra.isChecked()){
            feel = ra.getText().toString(); // 어떤 기분인지 저장
        }
    }

    public void myOnClick(View v){
        switch(v.getId()) {
            case R.id.btn_start:
                switch (cur_Status) {
                    case Init:


                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);

                        myTimer.sendEmptyMessage(0);
                        myBtnStart.setText("멈춤");
                        myBtnReset.setEnabled(true);
                        cur_Status = Run;
                        break;
                    case Run:
                        myTimer.removeMessages(0);
                        myPauseTime = SystemClock.elapsedRealtime();
                        myBtnStart.setText("시작");
                        cur_Status = Pause;
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now - myPauseTime);
                        myBtnStart.setText("멈춤");
                        cur_Status = Run;
                        break;
                }

                break;

            case R.id.btn_reset:
                switch (cur_Status) {

                    case Pause:
                        myTimer.removeMessages(0);
                        myBtnStart.setText("시작");
                        myOutput.setText("00:00:00:00");
                        cur_Status = Init;
                        myCount = 1;
                        break;
                }
                break;

            case R.id.save:
                Toast.makeText(getApplicationContext(), "선택됨", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


    Handler myTimer = new Handler(){
        public void handleMessage(Message msg){
            myOutput.setText(getTimeOut());

            myTimer.sendEmptyMessage(0);
        }
    };

    String getTimeOut(){
        now = SystemClock.elapsedRealtime();
        outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d:%02d:%02d:%02d", outTime/(1000*60*60)%24, outTime/1000 / 60, (outTime/1000)%60,(outTime%1000)/10);

        long hour = outTime/(1000*60*60)%24;
        long min = outTime/1000 / 60;
        long sec = (outTime/1000)%60; //시,분,초 저장

        String s_hour = String.valueOf(hour);
        String s_min = String.valueOf(min);
        String s_sec = String.valueOf(sec);

        exe_time = outTime;

        return easy_outTime;
    }



}

