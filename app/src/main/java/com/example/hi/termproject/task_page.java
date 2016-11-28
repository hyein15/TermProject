package com.example.hi.termproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hi on 2016-11-27.
 */
public class task_page extends AppCompatActivity {

    TextView myOutput;
    TextView myRec;
    TextView test;

    Button myBtnStart;
    Button myBtnReset;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    int myCount=1;
    long myBaseTime;
    long myPauseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);

        myOutput = (TextView) findViewById(R.id.time_out);
        //myRec = (TextView) findViewById(R.id.record);
      //  test =  (TextView) findViewById(R.id.test);
       // myRec.setVisibility(View.GONE);
        myBtnStart = (Button) findViewById(R.id.btn_start);
        myBtnReset = (Button) findViewById(R.id.btn_reset);
        EditText etDate = (EditText) findViewById(R.id.date);
        EditText etTime = (EditText) findViewById(R.id.time);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH시 mm분 ss초");

        etDate.setText(simpleDateFormat.format(date));
        etTime.setText(simpleTimeFormat.format(date));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void myOnClick(View v){
        switch(v.getId()){
            case R.id.btn_start: //시작버튼을 클릭했을때 현재 상태값에 따라 다른 동작을 할수있게끔 구현.
                switch(cur_Status){
                    case Init:
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);
                        //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                        myTimer.sendEmptyMessage(0);
                        myBtnStart.setText("멈춤"); //버튼의 문자"시작"을 "멈춤"으로 변경
                        myBtnReset.setEnabled(true); //기록버튼 활성
                        cur_Status = Run; //현재상태를 런상태로 변경
                        break;
                    case Run:
                        myTimer.removeMessages(0); //핸들러 메세지 제거
                        myPauseTime = SystemClock.elapsedRealtime();
                        myBtnStart.setText("시작");
                        cur_Status = Pause;
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now- myPauseTime);
                        myBtnStart.setText("멈춤");
/*
                        long now_ = SystemClock.elapsedRealtime(); //출력용
                        long outTime = now_ - myBaseTime;
                        long hour = outTime/3600 ;
                        long min = outTime/1000 / 60;
                        long sec =(outTime/1000)%60;
                        long mil = (outTime%1000)/10;

                        test.setText(String.valueOf(sec));*/

                        cur_Status = Run;
                        break;
                }
                break;

            case R.id.btn_reset:
                switch(cur_Status){

                    case Pause:
                        myTimer.removeMessages(0);
                        myBtnStart.setText("시작");
                        myOutput.setText("00:00:00:00");
                        cur_Status = Init;
                        myCount = 1;
                     //   myRec.setText("");
                        break;
                }
                break;
        }
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
        return easy_outTime;
       // milliseconds / (1000*60*60)) % 24)
    }

}

