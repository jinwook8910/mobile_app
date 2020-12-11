package com.example.harujogak;

import java.util.ArrayList;

//calender 클래스가 원래 있는거랑 헷갈릴까봐 변경
public class Schedule {
    String date;
    ArrayList<String> label; //같은 날에 여러 일정

    public Schedule(String d, String l){
        this.date = d;
        this.label.add(l);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<String> label) {
        this.label = label;
    }

    //Search(Date)
    //addScheduleByDate
    //Compute Day Static
    //Compute Week Static
}
