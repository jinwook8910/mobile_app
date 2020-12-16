package com.example.harujogak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class StatObstructActivity extends AppCompatActivity {
    ImageButton left, right;
    ImageButton btn1, btn2, btn3, btn4, btn5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_obstruct);

        //title bar 제거하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //navigation button
        btn1 = (ImageButton) findViewById(R.id.stat_obstruct_navi_btn1);
        btn2 = (ImageButton) findViewById(R.id.stat_obstruct_navi_btn2);
        btn3 = (ImageButton) findViewById(R.id.stat_obstruct_navi_btn3);
        btn4 = (ImageButton) findViewById(R.id.stat_obstruct_navi_btn4);
        btn5 = (ImageButton) findViewById(R.id.stat_obstruct_navi_btn5);

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);

        //좌우 버튼 클릭
        left = (ImageButton) findViewById(R.id.obstruct_left_btn);
        right = (ImageButton) findViewById(R.id.obstruct_right_btn);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatObstructActivity.this, StatWeekActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_right,R.anim.slide_right);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatObstructActivity.this, StatDayActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_right,R.anim.slide_right);
            }
        });
    }

    //navigation button
    class Listener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                Intent intent = new Intent(StatObstructActivity.this, GoalActivity.class);
                startActivity(intent);
            }
            else if(view==btn2){
                Intent intent =new Intent(StatObstructActivity.this, RatingActivity.class);
                startActivity(intent);
            }
            else if(view==btn3){
                Log.i("MainActivity", "onClickButton");
                Intent intent = new Intent(StatObstructActivity.this, TimeTableListActivity.class);
                startActivity(intent);
            }
            else if(view==btn4) {
                Intent intent = new Intent(StatObstructActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
            else if(view==btn5){
                Intent intent =new Intent(StatObstructActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
    StatObstructActivity.Listener listener = new StatObstructActivity.Listener();
}
