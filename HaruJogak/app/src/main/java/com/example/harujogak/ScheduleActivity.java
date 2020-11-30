package com.example.harujogak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {
    long mNow;
    Date mDate;
    char[] fb_date =new char[10]; //firebase에 키 값으로 들어갈 날짜
    FirebaseDatabase database;
    DatabaseReference myRef;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        //firebase 정의
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();

        CalendarView calendar = findViewById(R.id.calendar);
        TextView calendar_date = findViewById(R.id.calender_date);
        EditText calendar_text = findViewById(R.id.calendar_text);
        Button calendar_btn = findViewById(R.id.calendar_btn);

        calendar_date.setText(getDate());
        calendar_text.setText("");

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                calendar_date.setText(String.format("%d / %d / %d",year,month+1,dayOfMonth));
                String date=Integer.toString(year)+"년 "+Integer.toString(month+1)+"월 "+Integer.toString(dayOfMonth)+"일";
                fb_date=null;
                fb_date=date.toCharArray();
                calendar_text.setText("");
            }
        });
        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_date=String.valueOf(fb_date);
                String getDayGoal=calendar_text.getText().toString();
                calendar_text.setText("");
                myRef.child("UserID").child(input_date).child(getDayGoal).setValue("");
            }
        });
    }

    private String getDate(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return dateFormat.format(mDate);
    }
}
