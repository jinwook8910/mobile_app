package com.example.harujogak;

import java.util.ArrayList;

public class User {
    private static volatile User instance = null;
    private String id, passWord, eMail;
    private ArrayList<MyTimeTable> dateTable;
    private ArrayList<MyTimeTable> weekTable;
    private ArrayList<Goal> goalList;
    //private ArrayList<Obstruction> obstruction;
    private ScheduleList scheduleList;

    public User(String user_id, String user_pw){
        this.id = user_id;
        this.passWord = user_pw;

        //firebase에서 데이터 로드한 걸 add해야 함.
        this.dateTable = new ArrayList<>();
        this.weekTable = new ArrayList<>(7);
        this.goalList = new ArrayList<>();
        this.scheduleList = new ScheduleList();
    }

    public static User getInstance(String user_id, String user_pw){
        if(instance == null){
            instance = new User(user_id, user_pw);
        }
        return instance;
    }

    public static User getInstance(){ //객체 하나만 존재하도록 함
        return instance;
    }

    public String getId() {
        return id;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getEMail() {
        return eMail;
    }

    public void setDayTable(ArrayList<MyTimeTable> dayTable) {
        this.dateTable = dayTable;
    }

    public ArrayList<MyTimeTable> getDayTable() {
        return dateTable;
    }

    public void setWeekTable(ArrayList<MyTimeTable> weekTable) {
        this.weekTable = weekTable;
    }

    public ArrayList<MyTimeTable> getWeekTable() {
        return weekTable;
    }

    public ArrayList<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(ArrayList<Goal> goalList) {
        this.goalList = goalList;
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ScheduleList scheduleList) {
        this.scheduleList = scheduleList;
    }
}