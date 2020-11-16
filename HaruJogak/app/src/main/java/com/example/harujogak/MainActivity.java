package com.example.harujogak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5;
    TextView date, time;
    long mNow;
    Date mDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.main_date);
        time = findViewById(R.id.main_time);

        btn1 = (Button) findViewById(R.id.main_btn1);
        btn2 = (Button) findViewById(R.id.main_btn2);
        btn3 = (Button) findViewById(R.id.main_btn3);
        btn4 = (Button) findViewById(R.id.main_btn4);
        btn5 = (Button) findViewById(R.id.main_btn5);

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);

        date.setText(getDate());
        time.setText(getTime());
    }

    class Listener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                Intent intent = new Intent(MainActivity.this, Goal.class);
                startActivity(intent);
            }
            else if(view==btn2){
                System.out.println("btn2");
            }
            else if(view==btn3){
                Log.i("MainActivity", "onClickButton");
                Intent intent = new Intent(MainActivity.this, TimeTableList.class);
                startActivity(intent);
            }
            else if(view==btn4){
                Intent intent = new Intent(MainActivity.this, Calendar.class);
                startActivity(intent);
            }
            else if(view==btn5){
                System.out.println("btn5");
            }
        }
    }
    Listener listener = new Listener();

    private String getDate(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return dateFormat.format(mDate);
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return timeFormat.format(mDate);
    }
}