package com.example.harujogak;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;
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

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);

        date.setText(getDate());
        time.setText(getTime());

        PieChart pieChart = findViewById(R.id.todayPieChart);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        //pieChart 고정
        pieChart.setRotationEnabled(false);

        pieChart.setDragDecelerationFrictionCoef(0f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(60f, "잠"));
        yValues.add(new PieEntry(10f, "아침식사"));
        yValues.add(new PieEntry(35f, "공부"));
        yValues.add(new PieEntry(20f, "휴식"));
        yValues.add(new PieEntry(10f, "점심식사"));
        yValues.add(new PieEntry(35f, "운동"));
        yValues.add(new PieEntry(20f, "휴식"));
        yValues.add(new PieEntry(10f, "저녁식사"));

        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(1f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
//        data.
//        data.setValueTextSize(10f);
//        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
    }

    class Listener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                Intent intent = new Intent(MainActivity.this, GoalActivity.class);
                startActivity(intent);
            }
            else if(view==btn2){
                System.out.println("btn2");
            }
            else if(view==btn3){
                Log.i("MainActivity", "onClickButton");
                Intent intent = new Intent(MainActivity.this, TimeTableListActivity.class);
                startActivity(intent);
            }
            else if(view==btn4) {
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(intent);
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