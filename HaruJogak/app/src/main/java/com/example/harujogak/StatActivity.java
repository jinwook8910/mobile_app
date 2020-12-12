package com.example.harujogak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    float sum,count,sum_week,count_week,day_result;
    int i,j;
    TextView result1,result2,result3,result4;
    Button btn1,btn2,btn3,btn4;
    char[] fb_today =new char[20]; //firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat);
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference();
        result1=findViewById(R.id.text2);
        result2=findViewById(R.id.text4);
        result3=findViewById(R.id.text6);
        result4=findViewById(R.id.text8);
        btn1=findViewById(R.id.Button1);
        btn2=findViewById(R.id.Button3);
        btn3=findViewById(R.id.Button5);
        btn4=findViewById(R.id.Button7);



        //오늘 날짜 받아오기
        Calendar cal=Calendar.getInstance();
        Date currentTime = cal.getTime();
        String today_text=new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault()).format(currentTime);
        fb_today=null;
        fb_today=today_text.toCharArray();
        String input_today=String.valueOf(fb_today);
        int dayNum=cal.get(Calendar.DAY_OF_WEEK);
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DATE);
        day_result=0;


        //일일통계
        DatabaseReference data;
        data=myRef.child("UserID").child("날짜별 일정").child(input_today);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sum = 0;
                count = 0;
                if (snapshot.getValue() == null) {
                } else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue() != null) {
                            String rat = ds.child("평가").getValue().toString();
                            float temp = Float.parseFloat(rat);
                            sum += temp;
                            count++;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Firebase error");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count!=0) {
                    float re1 = (sum / count) * 20;
                    result1.setText(input_today + "의 일일 달성률은" + re1 + "%입니다.");
                }
            }
        });

        //주간통계
        if(dayNum==1) dayNum=8; //일요일인 경우
        for(i=0;i<dayNum-1;i++) {
            cal.add(Calendar.DAY_OF_MONTH, -i);
            Date week_ago_date = cal.getTime();
            String weekago_text = new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault()).format(week_ago_date);
            DatabaseReference week_data;
            week_data = myRef.child("UserID").child("날짜별 일정").child(weekago_text);
            week_data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    sum_week = 0;
                    count_week = 0;
                    if (snapshot.getValue() == null) {
                    } else {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (ds.getValue() != null) {
                                String rat = ds.child("평가").getValue().toString();
                                float temp = Float.parseFloat(rat);
                                sum_week += temp;
                                count_week++;
                            }
                        }
                        day_result += (sum_week / count_week) * 20;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("TAG", "Firebase error");
                }
            });
        }
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dayNum2=cal.get(Calendar.DAY_OF_WEEK);
                if(dayNum2==1) dayNum2=8;
                float re2=day_result/(dayNum2-1);
                result2.setText("이번 주 달성률은 "+re2+"%입니다.");
            }
        });
        //방해요소 통계
        
        //목표통계


    }



}
