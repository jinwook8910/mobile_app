package com.example.harujogak;

import java.sql.Time;
import java.util.ArrayList;

public class User {
    private String id, passWord, eMail;
    private ArrayList<MyTimeTable> dateTable = new ArrayList<>();
    private ArrayList<MyTimeTable> weekTable = new ArrayList<>(7);
    private ArrayList<Goal> goal = new ArrayList<>();
    //private ArrayList<Obstruction> obstruction;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public void setGoal(ArrayList<Goal> goal) {
        this.goal = goal;
    }

    public ArrayList<Goal> getGoal() {
        return goal;
    }
}