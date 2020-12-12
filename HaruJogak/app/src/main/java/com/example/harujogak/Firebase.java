package com.example.harujogak;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Firebase extends AppCompatActivity {
    //???????
    User user=new User();
    private String userID;
    private String temp;
    private ArrayList<String> temp_list;
    private String temp_date;
    private ArrayList<Goal> goalList;
    private ArrayList<Schedule> ScheduleList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int i,j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userID = user.getId();
        goalList = user.getGoalList();
        ScheduleList=user.getScheduleList();
    }
    public void addSchedule(ArrayList<Schedule> scheduleList){
        for(i=0;i<ScheduleList.size();i++){
            if(ScheduleList.get(i)!=null&&userID!=null){
                temp_date=ScheduleList.get(i).date.toString();
                temp_list=ScheduleList.get(i).label;
                for(j=0;j<temp_list.size();j++) {
                    temp=temp_list.get(j).toString();
                    myRef.child(userID).child(temp_date).setValue(temp);
                }
            }
        }
    }
    public void addGoal(ArrayList<Goal> goalList){
        for (i = 0; i < goalList.size(); i++) {
            if (goalList.get(i) != null && userID != null) {
                temp = goalList.get(i).toString();
                myRef.child(userID).child("목표리스트").setValue(temp);
            } else break;
        }
    }
}
