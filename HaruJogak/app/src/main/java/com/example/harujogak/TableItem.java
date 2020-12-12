package com.example.harujogak;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

import java.io.Serializable;

class TableItemByDate implements Serializable {
    String date;
    MyTimeTable myTimeTable;

    public TableItemByDate(String date, MyTimeTable t) {
        this.date = date;
        this.myTimeTable = t;
    }

    public MyTimeTable getMyTimeTable() {
        return myTimeTable;
    }

    public void setMyTimeTable(MyTimeTable myTimeTable) {
        this.myTimeTable = myTimeTable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

class TableItemByDay implements Serializable{
    String day;
    MyTimeTable myTimeTable;

    public String getDay() {
        return day;
    }

    public TableItemByDay(String day, MyTimeTable t) {
        this.day = day;
        this.myTimeTable = t;
    }

    public MyTimeTable getMyTimeTable() {
        return myTimeTable;
    }

    public void setMyTimeTable(MyTimeTable myTimeTable) {
        this.myTimeTable = myTimeTable;
    }
}