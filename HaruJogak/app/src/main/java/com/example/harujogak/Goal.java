package com.example.harujogak;

//목표 설정 부분 수정
//        목표 리스트를 보여주는 화면을 먼저 만들고 거기서 다시 추가, 수정, 삭제 가능하도록
//        보여주는 것은 목표 이름, D-Day만

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class Goal {
    FirebaseDatabase database;
    DatabaseReference myRef;
    int sum=0;
    int count1=0,count2=0; //for percent
    private String goal_name; //목표 이름
    private String startday; //"2020 / 00 / 00" 형태 날짜로 저장 - 시작일(통계를 위한 값)
    private String deadline; //"2020 / 00 / 00" 형태 날짜로 저장 - 마감일
    private int percent;

    public Goal(String gn, String dl){
        this.goal_name = gn;
        this.deadline = dl;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public String getStartday() {
        return startday;
    }

    public void setStartday(String startday) {
        this.startday = startday;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getdday(){ //dday값 반환 //D-12형태
        String dday;
        int tYear, tMonth, tDay;
        int temp;
        long t, d;
        Calendar tcalendar = Calendar.getInstance();
        Calendar dcalendar = Calendar.getInstance();

        //오늘 날짜
        tYear = tcalendar.get(Calendar.YEAR);
        tMonth = tcalendar.get(Calendar.MONTH);
        tDay = tcalendar.get(Calendar.DAY_OF_MONTH);
        tcalendar.set(tYear, tMonth+1, tDay);

        //목표 날짜
        String[] goal_date = this.getDeadline().split(" / ");
        dcalendar.set(Integer.parseInt(goal_date[0]), Integer.parseInt(goal_date[1]), Integer.parseInt(goal_date[2]));

        //날짜 초단위로 변경
        t=tcalendar.getTimeInMillis()/(24*60*60*1000);
        d=dcalendar.getTimeInMillis()/(24*60*60*1000);
        temp =(int)(t-d);
        if(temp >0)
            dday = "D+"+Integer.toString(temp);
        else if(temp ==0)
            dday = "D-day";
        else
            dday = "D"+Integer.toString(temp);

        return dday;
    }
    public void setPercent(int percent){
        this.percent=percent;
    }
    public Integer getDday(){ //목표 통계 결과
        int result;
        int dday;
        long s, e;
        sum=0;
        String UserID=Login2.getUserID();
//        database = FirebaseDatabase.getInstance();
//        myRef=database.getReference();

        Calendar scalendar = Calendar.getInstance();//시작일
        Calendar ecalendar = Calendar.getInstance();//마감일

        //시작 날짜
        String[] start_date = this.getStartday().split(" / ");
        scalendar.set(Integer.parseInt(start_date[0]), Integer.parseInt(start_date[1]), Integer.parseInt(start_date[2]));

        //마감 날짜
        String[] end_date = this.getDeadline().split(" / ");
        ecalendar.set(Integer.parseInt(end_date[0]), Integer.parseInt(end_date[1]), Integer.parseInt(end_date[2]));

        //날짜 초단위로 변경
        s=scalendar.getTimeInMillis()/(24*60*60*1000);
        e=ecalendar.getTimeInMillis()/(24*60*60*1000);
        dday =(int)(s-e);
        return dday;

//        //통계 계산
//        //목표에 해당하는 별점 모두 가져오기
//        //날짜마다 목표에 대한 별점의 평균 * (1/dday)해서 오늘 날짜까지 더하기
//        DatabaseReference data;
//        data = myRef.child(UserID).child("날짜별 일정");
//        data.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //long count=snapshot.getChildrenCount();
//                for(DataSnapshot ds:snapshot.getChildren()){
//                    for(DataSnapshot ds1:ds.getChildren()){
//                        String key=ds1.child("장기목표").getValue().toString();
//                        if(getGoal_name().equals(key)) {
//                            String value = ds1.child("평가").getValue().toString();
//                            float temp = Float.parseFloat(value);
//                            sum += temp;
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("TAG", "Firebase error");
//            }
//        });
//        result=sum/Math.abs(dday)*20;
//        return result;
    }
}
