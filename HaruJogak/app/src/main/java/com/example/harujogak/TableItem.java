package com.example.harujogak;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

import java.io.Serializable;

class TableItemByDate implements Serializable {
    String date;
    MyTimeTable myTimeTable;
//    private PieData pieData;

    public TableItemByDate(MyTimeTable t) {
        this.myTimeTable = t;
        date = t.getOnDate();
//        pieData = t.getPieData();
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

//    public PieData getPieData() {
//        return pieData;
//    }
//
//    public void setPieData(PieData pieData) {
//        this.pieData = pieData;
    }

}

class TableItemByDay implements Serializable{
    String day;
    //    private PieChart pieChart;
    private PieData pieData;

    public TableItemByDay(MyTimeTable t) {
        this.day = day;
        pieData = t.getPieData();
    }

    public String getDate() {
        return day;
    }

    public void setDate(String day) {
        this.day = day;
    }

    public PieData getPieData() {
        return pieData;
    }

    public void setPieData(PieData pieData) {
        this.pieData = pieData;
    }

}