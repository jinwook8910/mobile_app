package com.example.harujogak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Calendar extends AppCompatActivity {
    //Search(Date)
    //addScheduleByDate
    //Compute Day Static
    //Compute Week Static
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        CalendarView calendar = findViewById(R.id.calendar);
        TextView calendar_date = findViewById(R.id.calender_date);
        EditText calendar_text = findViewById(R.id.calendar_text);
        Button calendar_btn = findViewById(R.id.calendar_btn);

        calendar_date.setText("날짜를 선택하세요");

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                calendar_date.setVisibility(View.VISIBLE);
                calendar_text.setVisibility(View.VISIBLE);
                calendar_btn.setVisibility(View.VISIBLE);

                calendar_date.setText(String.format("%d / %d / %d",year,month+1,dayOfMonth));
                calendar_text.setText("");
            }
        });

    }

    public void checkDay(int cYear, int cMonth, int cDay){

    }
}
