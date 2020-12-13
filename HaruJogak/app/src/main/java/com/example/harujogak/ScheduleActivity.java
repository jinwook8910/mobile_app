package com.example.harujogak;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {
    long mNow;
    Date mDate;
    char[] fb_date =new char[20]; //firebase에 키 값으로 들어갈 날짜
    FirebaseDatabase database;
    DatabaseReference myRef;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        //firebase 정의
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();

        CalendarView calendar = findViewById(R.id.calendar);
        TextView calendar_date = findViewById(R.id.calender_date);
        EditText calendar_text = findViewById(R.id.calendar_text);
        Button calendar_btn = findViewById(R.id.calendar_btn);
        //listview
        ListView listview = (ListView) findViewById(R.id.calendar_list);

        calendar_date.setText(getDate());
        calendar_text.setText("");
        //listview설정
        setListview(calendar_date.getText().toString());

        //날짜 변경시
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                calendar_date.setText(String.format("%d / %d / %d",year,month+1,dayOfMonth));
                String date=Integer.toString(year)+"년 "+Integer.toString(month+1)+"월 "+Integer.toString(dayOfMonth)+"일";
                fb_date=null;
                fb_date=date.toCharArray();
                calendar_text.setText("");
                //listview설정
                setListview(calendar_date.getText().toString());

            }
        });
        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //class에 저장
                //Schedule new_schedule = new Schedule(calendar_date.getText().toString() ,calendar_text.getText().toString());
                //User user = User.getInstance();
                //user.getScheduleList().add(new_schedule);

                //firebase
                String input_date=String.valueOf(fb_date);
                String getDayGoal=calendar_text.getText().toString();
                calendar_text.setText("");
                myRef.child("UserID").child("날짜별 일정").child(input_date).child(getDayGoal).child("방해요소").setValue(0);
                myRef.child("UserID").child("날짜별 일정").child(input_date).child(getDayGoal).child("평가").setValue(0);
            }
        });
    }

    private void setListview(String date){
        //listView 불러오기
        ListView listview = (ListView) findViewById(R.id.calendar_list);
        ArrayList<String> calendar_list = new ArrayList<>();
//        //real data (해당 날짜의 일정만 가져옴)
//        User user = User.getInstance();
//        ScheduleList schedulelist = user.getScheduleList();
//        calendar_list = schedulelist.search(date);

        //test data
        calendar_list.add("hello1");
        calendar_list.add("hello2");
        calendar_list.add("hello3");
        calendar_list.add("hello4");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, calendar_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                //listview 객체 클릭할 때 이벤트
            }
        });
    }

    private String getDate(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return dateFormat.format(mDate);
    }
}
