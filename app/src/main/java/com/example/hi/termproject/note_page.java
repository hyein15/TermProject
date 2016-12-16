package com.example.hi.termproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hi on 2016-11-27.
 */
public class note_page extends AppCompatActivity {


    static String year="";
    static String month="";
   static String day="";

    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_page);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "myDB.db", null, 1);

        final Spinner spinner1 = (Spinner) findViewById(R.id.mySpinner1);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        final Spinner spinner2 = (Spinner) findViewById(R.id.mySpinner2);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        final Spinner spinner3 = (Spinner) findViewById(R.id.mySpinner3);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        Button btn = (Button) findViewById(R.id.myButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(note_page.this,
                        spinner1.getSelectedItem() + " " + spinner2.getSelectedItem() + " " + spinner3.getSelectedItem() + " 을 선택하셨습니다.",
                        Toast.LENGTH_SHORT).show();
                year = (String) spinner1.getSelectedItem();
                month = (String) spinner2.getSelectedItem();
                day = (String) spinner3.getSelectedItem();




                final TextView result = (TextView) findViewById(R.id.DBview);

                result.setText(dbHelper.getResult(year,month,day));
            }

        });


    }


}




