package com.example.harujogak;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class User {
    private static volatile User instance = null;
    private String id, passWord, eMail;
    private ArrayList<MyTimeTable> weekTable;
    private ArrayList<MyTimeTable> dateTable;
    private ArrayList<Goal> goalList;
    private ArrayList<Obstruct> obstructList;
    private ScheduleList scheduleList;

    public User(){
        init();
        this.weekTable = new ArrayList<>(7);
    } //임시 테스트용
    public User(String user_id, String user_pw){
        this.id = user_id;
        this.passWord = user_pw;

        //firebase에서 데이터 로드한 걸 add해야 함.
        this.weekTable = new ArrayList<>(7);
        this.dateTable = new ArrayList<>();
        this.goalList = new ArrayList<>();
        this.obstructList = new ArrayList<>();
        this.scheduleList = new ScheduleList();
        init();
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

    public ArrayList<MyTimeTable> getWeekTable() {
        return weekTable;
    }

    public void setWeekTable(ArrayList<MyTimeTable> weekTable) {
        this.weekTable = weekTable;
    }

    public ArrayList<MyTimeTable> getDateTable() {
        return dateTable;
    }

    public void setDateTable(ArrayList<MyTimeTable> dateTable) {
        this.dateTable = dateTable;
    }

    public ArrayList<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(ArrayList<Goal> goalList) {
        this.goalList = goalList;
    }

    public ArrayList<Obstruct> getObstructList() {
        return obstructList;
    }

    public void setObstructList(ArrayList<Obstruct> obstructList) {
        this.obstructList = obstructList;
    }

    public void addObstructList(String str) {
        boolean isReduplication = false;
        for (int i = 0; i < obstructList.size(); i++) {
            if (obstructList.get(i).getObstruction().equals(str)) {
                isReduplication = true;
                obstructList.get(i).setFrequency(obstructList.get(i).getFrequency() + 1);
                break;
            }
        }
        if (!isReduplication)
            obstructList.add(new Obstruct(str, 1));
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ScheduleList scheduleList) {
        this.scheduleList = scheduleList;
    }

    private void init(){
        ArrayList<PieEntry> temp = new ArrayList<>();
        temp.add(new PieEntry(1440f, " "));

        PieDataSet dataSet = new PieDataSet(temp, "Tasks");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(1f);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);
    }
}

class Obstruct{
    private String obstruction;
    private int frequency;

    public Obstruct(){

    }
    public Obstruct(String ob, int fre){
        this.obstruction = ob;
        this.frequency = fre;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setObstruction(String obstruction) {
        this.obstruction = obstruction;
    }

    public String getObstruction() {
        return obstruction;
    }
}