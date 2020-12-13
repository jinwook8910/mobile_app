package com.example.harujogak;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ImageButton btn1, btn2, btn3, btn4,btn5;
    private TextView date, time;
    private long mNow;
    private Date mDate;
    private MyTimeTable todaysTimeTable;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    //firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference data;
    private ValueEventListener dataListener;
    private static ArrayList<String> goal_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.main_date);
        time = findViewById(R.id.main_time);
        PieChart pieChart = findViewById(R.id.todayPieChart);

        btn1 = (ImageButton) findViewById(R.id.main_btn1);
        btn2 = (ImageButton) findViewById(R.id.main_btn2);
        btn3 = (ImageButton) findViewById(R.id.main_btn3);
        btn4 = (ImageButton) findViewById(R.id.main_btn4);
        btn5 = (ImageButton) findViewById(R.id.main_btn5);

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);

        //date.setText(getDate());
        //time.setText(getTime());
        ShowTimeMethod();

        //Todo : DB에서 현재 날짜에 해당하는 시간표의 MyTimeTable 정보 가져옴
        // 지금은 그냥 setExample 함수로 예시 정보 저장해서 사용했음
        todaysTimeTable = new MyTimeTable();
        setExample(todaysTimeTable); // 나중에 DB로 변경해야할 부분

        pieChart.setUsePercentValues(false);
        pieChart.setRotationEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);

        pieChart.setData(todaysTimeTable.getPieData());

        //firebase - 목표 통계
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference();
        data=myRef.child("UserID").child("목표리스트");
        dataListener = data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                } else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue() != null) {
                            String goal = ds.getKey().toString();
                            goal_list.add(goal);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Firebase error");
            }
        });
    }

    class Listener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                Intent intent = new Intent(MainActivity.this, GoalActivity.class);
                startActivity(intent);
            }
            else if(view==btn2){
                Intent intent =new Intent(MainActivity.this,Rating.class);
                startActivity(intent);
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
            else if(view==btn5){
                Intent intent =new Intent(MainActivity.this,Login2.class);
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
    public void ShowTimeMethod(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                date.setText(getDate());
                time.setText(getTime());
            }
        };
        Runnable task = new Runnable(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){}
                    handler.sendEmptyMessage(1);//핸들러 호출
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void setExample(MyTimeTable exT){
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(60f, "잠"));
        yValues.add(new PieEntry(10f, "아침식사"));
        yValues.add(new PieEntry(35f, "공부"));
        yValues.add(new PieEntry(20f, "휴식"));
        yValues.add(new PieEntry(10f, "점심식사"));
        yValues.add(new PieEntry(35f, "운동"));
        yValues.add(new PieEntry(20f, "휴식"));
        yValues.add(new PieEntry(10f, "저녁식사"));

        PieDataSet dataSet = new PieDataSet(yValues, "Tasks");
        dataSet.setSliceSpace(0.5f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(0f);

        exT.setPieData(data);
        exT.setDate("2020-12-12");
    }
    public static ArrayList<String> getGoal_list(){
        return goal_list;
    }
}