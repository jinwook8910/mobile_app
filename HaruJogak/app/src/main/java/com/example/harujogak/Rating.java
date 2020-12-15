package com.example.harujogak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Rating extends AppCompatActivity {
        FirebaseDatabase database;
        DatabaseReference myRef;
        private RatingBar ratingBar;
        char[] fb_today =new char[20]; //firebase
        EditText interrupt;
        Button save;
        Button stat;
        ImageButton right;
        ImageButton left;
        TextView result;
        TextView schedule;
        float sum=0;
        int count=0;
        int now=0;
        String[] arr =new String[100];
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.rating_dialog);

            //firebase 정의
            database = FirebaseDatabase.getInstance();
            myRef=database.getReference();

            //오늘 날짜 받아오기
            Date currentTime = Calendar.getInstance().getTime();
            String today_text=new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault()).format(currentTime);
            fb_today=null;
            fb_today=today_text.toCharArray();
            String input_today=String.valueOf(fb_today);

            ratingBar = findViewById(R.id.ratingbar);
            ratingBar.setOnRatingBarChangeListener(new Listener());
            interrupt=findViewById(R.id.interrupt);
            save=findViewById(R.id.button1);
            stat=findViewById(R.id.button2);
            result=findViewById(R.id.text3);
            right=findViewById(R.id.button_right);
            left=findViewById(R.id.button_left);
            schedule=findViewById(R.id.text1);


            save.setOnClickListener(new View.OnClickListener() {
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

            right.setOnClickListener(new View.OnClickListener() {
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

            left.setOnClickListener(new View.OnClickListener() {
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

            stat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Rating.this,StatActivity.class);
                    startActivity(intent);
                }
            });

        }

        class Listener implements RatingBar.OnRatingBarChangeListener{
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
                if(arr[now]==null){}
                else {
                    String input_today_r = String.valueOf(fb_today);
                    myRef.child("UserID").child("날짜별 일정").child(input_today_r).child(arr[now]).child("평가").setValue(rating);
                }
            }
        }
    }

