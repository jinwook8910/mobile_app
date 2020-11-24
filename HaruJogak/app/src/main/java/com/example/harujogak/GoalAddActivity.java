package com.example.harujogak;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class GoalAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_add);

        CalendarView goal_calendar = findViewById(R.id.goal_add_calendar);
        TextView goal_text = findViewById(R.id.goal_add_text);
        EditText goal_input = findViewById(R.id.goal_add_input);
        TextView goal_result = findViewById(R.id.goal_add_result);
        Button goal_btn = findViewById(R.id.calendar_btn);

        goal_text.setText("나의 목표는");
        goal_result.setText("D-day");

        goal_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                int tYear, tMonth, tDay;
                long t, d;
                int dday;

                Calendar tcalendar = Calendar.getInstance();
                Calendar dcalendar = Calendar.getInstance();
                //오늘 날짜
                tYear = tcalendar.get(Calendar.YEAR);
                tMonth = tcalendar.get(Calendar.MONTH);
                tDay = tcalendar.get(Calendar.DAY_OF_MONTH);
                //목표 날짜
                dcalendar.set(year, month, dayOfMonth);
                //날짜 초단위로 변경
                t=tcalendar.getTimeInMillis()/(24*60*60*1000);
                d=dcalendar.getTimeInMillis()/(24*60*60*1000);
                dday=(int)(t-d);
                if(dday>0)
                    goal_result.setText("D+"+Integer.toString(dday));
                else if(dday==0)
                    goal_result.setText("D-day");
                else
                    goal_result.setText("D"+Integer.toString(dday));
            }
        });
    }


}
