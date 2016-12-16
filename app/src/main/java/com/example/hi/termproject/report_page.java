package com.example.hi.termproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by hi on 2016-11-27.
 */
public class report_page extends AppCompatActivity {

    static String year="";
    static String month="";
    static String day="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "myDB.db", null, 1);

        final Spinner spinner1 = (Spinner)findViewById(R.id.mySpinner1);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.year, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        final Spinner spinner2 = (Spinner)findViewById(R.id.mySpinner2);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.month, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        final Spinner spinner3 = (Spinner)findViewById(R.id.mySpinner3);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.day, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        Button btn = (Button)findViewById(R.id.myButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(report_page.this,
                        spinner1.getSelectedItem() + " " + spinner2.getSelectedItem()+" " +spinner3.getSelectedItem()+" 을 선택하셨습니다.",
                        Toast.LENGTH_SHORT).show();

                year = (String) spinner1.getSelectedItem();
                month = (String) spinner2.getSelectedItem();
                day = (String) spinner3.getSelectedItem();

                BarChart chart = (BarChart) findViewById(R.id.chart);
                BarChart chart_doing = (BarChart) findViewById(R.id.chart_doing);

                BarData data = new BarData(getXAxisValues(), getDataSet_time());
                BarData data_doing = new BarData(getXAxisValues(), getDataSet_doing());


                chart.setData(data);
                chart.setDescription(" ");
                chart.getXAxis().setDrawGridLines(false);
                XAxis xAxis = chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                chart.animateXY(1000, 1000);
                chart.invalidate();

                chart_doing.setData(data_doing);
                chart_doing.setDescription(" ");
                chart_doing.getXAxis().setDrawGridLines(false);
                XAxis xAxis_doing = chart_doing.getXAxis();
                xAxis_doing.setPosition(XAxis.XAxisPosition.BOTTOM);
                chart_doing.animateXY(1000, 1000);
                chart_doing.invalidate();

            }
        });
    }

    private ArrayList<BarDataSet> getDataSet_time() {
        ArrayList<BarDataSet> dataSets = null;
        long time [] = new long[6];
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "myDB.db", null, 1);
        time = dbHelper.time(year,month,day);

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry e1 = new BarEntry(time[1]/1000/60, 0);
        valueSet1.add(e1);
        BarEntry e2 = new BarEntry(time[2]/1000/60, 1);
        valueSet1.add(e2);
        BarEntry e3 = new BarEntry(time[3]/1000/60, 2);
        valueSet1.add(e3);
        BarEntry e4 = new BarEntry(time[4]/1000/60, 3);
        valueSet1.add(e4);
        BarEntry e5 = new BarEntry(time[5]/1000/60, 4);
        valueSet1.add(e5);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "분 단위");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<BarDataSet> getDataSet_doing() {
        ArrayList<BarDataSet> dataSets = null;
        int doing [] = new int[6];
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "myDB.db", null, 1);
        doing = dbHelper.doing(year,month,day);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry e1 = new BarEntry(doing[1], 0);
        valueSet2.add(e1);
        BarEntry e2 = new BarEntry(doing[2], 1);
        valueSet2.add(e2);
        BarEntry e3 = new BarEntry(doing[3], 2);
        valueSet2.add(e3);
        BarEntry e4 = new BarEntry(doing[4], 3);
        valueSet2.add(e4);
        BarEntry e5 = new BarEntry(doing[5], 4);
        valueSet2.add(e5);

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "횟수");
        barDataSet2.setColor(Color.rgb(0, 155, 0));
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet2);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Eat");
        xAxis.add("Move");
        xAxis.add("Study");
        xAxis.add("Work");
        xAxis.add("Game");
        return xAxis;
    }


}