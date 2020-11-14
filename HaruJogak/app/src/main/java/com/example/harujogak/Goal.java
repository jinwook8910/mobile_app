package com.example.harujogak;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Goal extends AppCompatActivity {
    String goal_name;
    int Deadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal);
    }

    public String computeGoalStatic(){
        String result="결과 값 문자열";

        return result;
    }
}
