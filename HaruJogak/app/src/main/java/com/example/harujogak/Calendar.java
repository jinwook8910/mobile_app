package com.example.harujogak;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar extends AppCompatActivity {
    //Search(Date)
    //addScheduleByDate
    //Compute Day Static
    //Compute Week Static
    long mNow;
    Date mDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        CalendarView calendar = findViewById(R.id.calendar);
        TextView calendar_date = findViewById(R.id.calender_date);
        EditText calendar_text = findViewById(R.id.calendar_text);
        Button calendar_btn = findViewById(R.id.calendar_btn);

        calendar_date.setText(getDate());
        calendar_text.setText("");

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                calendar_date.setText(String.format("%d / %d / %d",year,month+1,dayOfMonth));
                calendar_text.setText("");
            }
        });

    }

    private String getDate(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return dateFormat.format(mDate);
    }

}
