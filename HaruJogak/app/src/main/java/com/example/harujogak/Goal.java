package com.example.harujogak;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Goal extends AppCompatActivity {
    String goal_name;
    int Deadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal);

        CalendarView goal_calendar = findViewById(R.id.goal_calendar);
        TextView goal_text = findViewById(R.id.goal_text);
        EditText goal_input = findViewById(R.id.goal_input);
        TextView goal_result = findViewById(R.id.goal_result);
        Button goal_btn = findViewById(R.id.calendar_btn);

        goal_text.setText("나의 목표는");
        goal_result.setText("D-day");

        goal_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){

            }
        });
    }

    public String computeGoalStatic(){
        String result="결과 값 문자열";

        return result;
    }
}
