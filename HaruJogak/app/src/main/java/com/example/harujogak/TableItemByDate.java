package com.example.harujogak;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

public class TableItemByDate {
    String date;
    private PieData pieData;

    public TableItemByDate(MyTimeTable t) {
        date = t.getOnDate();
        pieData = t.getPieData();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PieData getPieData() {
        return pieData;
    }

    public void setPieData(PieData pieData) {
        this.pieData = pieData;
    }

}

class TableItemByDay {
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