package com.example.harujogak;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageButton btn1, btn2, btn3, btn4,btn5;
    private TextView date, time;
    private long mNow;
    private Date mDate;
    private MyTimeTable todaysTimeTable;
    Integer[] todaysRate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    //firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference data;
    private ValueEventListener dataListener;
    //private static ArrayList<String> goal_list = new ArrayList<>();
    public static Map<String,Integer> goal_list=new HashMap<String,Integer>();
    public static Map<String,Float> goal_stat=new HashMap<String,Float>();
    Login2 user=new Login2();
    String UserID=user.getUserID();

    float sum=0;
    int count=0;
    int now=0,i;
    char[] fb_today =new char[20]; //firebase
    String[] arr =new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //title bar 제거하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        date = findViewById(R.id.main_date);
        time = findViewById(R.id.main_time);
        PieChart pieChart = findViewById(R.id.todayPieChart);

        btn1 = (ImageButton) findViewById(R.id.main_navi_btn1);
        btn2 = (ImageButton) findViewById(R.id.main_navi_btn2);
        btn3 = (ImageButton) findViewById(R.id.main_navi_btn3);
        btn4 = (ImageButton) findViewById(R.id.main_navi_btn4);
        btn5 = (ImageButton) findViewById(R.id.main_navi_btn5);

        HomeListener listener = new HomeListener();
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
        // user.dateTable 중에서 오늘 날짜에 해당하는 시간표가 있으면 그거 쓰고 없으면 오늘 요일 시간표 불러옴
        todaysTimeTable = new MyTimeTable();
        setExample(todaysTimeTable); // 나중에 DB로 변경해야할 부분

        todaysRate = new Integer[todaysTimeTable.getTasksCount()];

        pieChart.setUsePercentValues(false);
        pieChart.setRotationEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);

        pieChart.setData(todaysTimeTable.getPieData());
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int x = pieChart.getData().getDataSetForEntry(e).getEntryIndex((PieEntry) e);
                show_rating(x);
            }

            @Override
            public void onNothingSelected() {
            }
        });

//        //firebase - 목표 통계
//        database = FirebaseDatabase.getInstance();
//        myRef=database.getReference();
//        data=myRef.child(UserID).child("목표리스트");
//        dataListener = data.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() == null) {
//                } else {
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        if (ds.getValue() != null) {
//                            String goal = ds.getKey();
//                            String dday=ds.child("목표 D-day").getValue().toString();
//                            int day=Integer.parseInt(dday);
//                            goal_list.put(goal,day);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("TAG", "Firebase error");
//            }
//        });

        //for(String key:goal_stat.keySet()){ //goal_stat value값으로 장기 목표 통계치 저
        //    if(goal_list.get(key)>=0) goal_stat.put(key,(float)100);
        //    else goal_stat.put(key,goal_stat.get(key)/Math.abs(goal_list.get(key)));
        //}
    }

    class HomeListener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                Intent intent = new Intent(MainActivity.this, GoalActivity.class);
                startActivity(intent);
            }
            else if(view==btn2){
                Intent intent =new Intent(MainActivity.this, RatingActivity.class);
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

    public void show_rating(int index){
        Dialog ratingDialog = new Dialog(this);
        ratingDialog.setContentView(R.layout.rating_dialog);

        FirebaseDatabase database;
        DatabaseReference myRef;

        //firebase 정의
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference();

        //오늘 날짜 받아오기
        Date currentTime = Calendar.getInstance().getTime();
        String today_text=new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault()).format(currentTime);
        fb_today=null;
        fb_today=today_text.toCharArray();
        String input_today=String.valueOf(fb_today);

        ImageButton exit = (ImageButton) ratingDialog.findViewById(R.id.exit);
        TextView schedule=findViewById(R.id.text1);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RateListener());
        EditText interrupt = (EditText) findViewById(R.id.interrupt);
        ImageButton left_button = (ImageButton) findViewById(R.id.button_left);
        ImageButton right_button = (ImageButton) findViewById(R.id.button_right);
        Button rating_done = (Button) findViewById(R.id.rating_done);
        Button static_button = (Button) findViewById(R.id.static_button);



        rating_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arr[now]==null){}
                else {
                    String getInterrupt = interrupt.getText().toString();
                    myRef.child("UserID").child("날짜별 일정").child(input_today).child(arr[now]).child("방해요소").setValue(getInterrupt);
                    interrupt.setText("");
                }
            }
        });

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arr[now]==null){}
                else {
                    if ((now + 1) < count) {
                        now = now + 1;
                    } else {
                        now = 0;
                    }
                    schedule.setText(arr[now]);
                }
            }
        });

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arr[now]==null){}
                else {
                    if ((now - 1) >= 0) {
                        now = now - 1;
                    } else {
                        now = count - 1;
                    }
                    schedule.setText(arr[now]);
                }
            }
        });

        //Read data
        DatabaseReference data;
        data=myRef.child("UserID").child("날짜별 일정").child(input_today);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sum = 0;
                count = 0;
                if(snapshot.getValue()==null){
                    schedule.setText("오늘의 일정이 없습니다.");
                }
                else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue() != null) {
                            String rat = ds.child("평가").getValue().toString();
                            String sche = ds.getKey().toString();
                            arr[count++] = sche;
                            float temp = Float.parseFloat(rat);
                            sum += temp;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Firebase error");
            }
        });

        static_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),StatActivity.class);
                startActivity(intent);
            }
        });

        ratingDialog.show();
    }

    class RateListener implements RatingBar.OnRatingBarChangeListener{
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
            if(arr[now]==null){}
            else {
                String input_today_r = String.valueOf(fb_today);
                myRef.child("UserID").child("날짜별 일정").child(input_today_r).child(arr[now]).child("평가").setValue(rating);
            }
        }
    }

//    public void rate(){
//
//        todaysRate[index] = (int) ratingBar.getRating();
//        todaysTimeTable.getRating().add(todaysRate);
//
//        User user = new User(); //Todo 디비
//        String str = interrupt.getText().toString();
//        user.addObstructList(str); //Todo 디비
//        ratingDialog.dismiss();
//    }

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
    public static HashMap<String,Integer> getGoal_list(){
        return (HashMap<String, Integer>) goal_list;
    }
    public static HashMap<String,Float> getGoal_stat(){
        return (HashMap<String, Float>) goal_stat;
    }
}