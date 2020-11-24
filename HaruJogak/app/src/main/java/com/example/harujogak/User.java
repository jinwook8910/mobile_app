package com.example.harujogak;

import java.sql.Time;
import java.util.ArrayList;

public class User {
    private String id, passWord, eMail;
    private ArrayList<TimeTable> dayTable = new ArrayList<>();
    private ArrayList<TimeTable> weekTable = new ArrayList<>(7);
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

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String geteMail() {
        return eMail;
    }

    public void setDayTable(ArrayList<TimeTable> dayTable) {
        this.dayTable = dayTable;
    }

    public ArrayList<TimeTable> getDayTable() {
        return dayTable;
    }

    public void setWeekTable(ArrayList<TimeTable> weekTable) {
        this.weekTable = weekTable;
    }

    public ArrayList<TimeTable> getWeekTable() {
        return weekTable;
    }

    public void setGoal(ArrayList<Goal> goal) {
        this.goal = goal;
    }

    public ArrayList<Goal> getGoal() {
        return goal;
    }
}