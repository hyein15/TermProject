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
    TextView test,test2;
    EditText memo;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    int cur_Status = Init;
    int myCount=1;
    long myBaseTime;
    long myPauseTime;


    DBHelper helper;
    SQLiteDatabase db;


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
        test = (TextView) findViewById(R.id.test);
        test2 = (TextView) findViewById(R.id.test2);

        EditText etDate = (EditText) findViewById(R.id.date);
        EditText etTime = (EditText) findViewById(R.id.time);
        final EditText memo = (EditText)findViewById(R.id.memo);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH시 mm분 ss초");

        //String strNow = simpleDateFormat.format(date); //2016년12월03일

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
                final DBHelper dbHelper = new DBHelper(getApplicationContext(), "mylogger.db", null, 1);

                String memo_str = memo.getText().toString(); //메모 저장

                gps = new GpsInfo(task_page.this);

                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude(); //경도위도저장

                   // String a = "위도="+ String.valueOf(latitude)+ " 경도="+String.valueOf(longitude);

                   // test.setText(a);

                } else {
                    gps.showSettingsAlert();
                }

            }
        });

    }

    public void printChecked_1(View v){
        RadioButton ra = (RadioButton) v;
        String resultText_1 = "";
        if (ra.isChecked()){
            resultText_1 = ra.getText().toString(); // 어떤 일 했는지 저장
        }
    }

    public void printChecked_2(View v){
        RadioButton ra = (RadioButton) v;
        String resultText_2 = "";
        if (ra.isChecked()){
            resultText_2 = ra.getText().toString(); // 어떤 기분인지 저장
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
        long now = SystemClock.elapsedRealtime();
        long outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d:%02d:%02d:%02d", outTime/(1000*60*60)%24, outTime/1000 / 60, (outTime/1000)%60,(outTime%1000)/10);

        long hour = outTime/(1000*60*60)%24;
        long min = outTime/1000 / 60;
        long sec = (outTime/1000)%60; //시,분,초 저장

        return easy_outTime;
    }

}

