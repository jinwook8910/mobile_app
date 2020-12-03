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

import java.util.Calendar;

public class GoalAddActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    char[] fb_dday=new char[20]; //firebase
    int dday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_add);

        //firebase 정의
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference();

        CalendarView goal_calendar = findViewById(R.id.goal_add_calendar);
        TextView goal_text = findViewById(R.id.goal_add_text);
        EditText goal_input = findViewById(R.id.goal_add_input);
        TextView goal_result = findViewById(R.id.goal_add_result);
        Button goal_btn = findViewById(R.id.calendar_btn);
        Button goal_add_btn=findViewById(R.id.goal_add_btn);

        goal_text.setText("나의 목표는");
        goal_result.setText("D-day");

        goal_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                int tYear, tMonth, tDay;
                long t, d;

                Calendar tcalendar = Calendar.getInstance();
                Calendar dcalendar = Calendar.getInstance();

                //오늘 날짜
                tYear = tcalendar.get(Calendar.YEAR);
                tMonth = tcalendar.get(Calendar.MONTH);
                tDay = tcalendar.get(Calendar.DAY_OF_MONTH);

                //목표 날짜
                dcalendar.set(year, month, dayOfMonth);
                String date=Integer.toString(year)+"년 "+Integer.toString(month+1)+"월 "+Integer.toString(dayOfMonth)+"일";
                fb_dday=null;
                fb_dday=date.toCharArray();

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

        goal_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getGoal =goal_input.getText().toString();
                String input_dday=String.valueOf(fb_dday);
                myRef.child("UserID").child("목표리스트").child(getGoal).setValue("");
                myRef.child("UserID").child("목표리스트").child(getGoal).child("목표 날짜").setValue(input_dday);
                myRef.child("UserID").child("목표리스트").child(getGoal).child("목표 D-day").setValue(dday);
                goal_input.setText("");
            }
        });
    }


}
