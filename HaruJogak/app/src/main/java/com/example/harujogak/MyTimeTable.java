package com.example.harujogak;

import android.graphics.Color;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;

public class MyTimeTable {
    private PieData pieData;
    private ArrayList<int[]> MyBackground = new ArrayList<>();
    private ArrayList<PieEntry> MyTasks = new ArrayList<>();
    private int[] OnWeek = {0, 0, 0, 0, 0, 0, 0};    // MON ~ SUN
    private String OnDate = new String();


    void setPieData(PieData p) {
        pieData = p;
    }

    PieData getPieData() {
        return pieData;
    }

    void setMyBackground(ArrayList<int[]> background) {
        MyBackground = background;
    }

    ArrayList<int[]> getMyBackground() {
        return MyBackground;
    }

    void setMyTasks(ArrayList<PieEntry> tasks) {
        MyTasks = tasks;
    }

    ArrayList<PieEntry> getMyTasks() {
        return MyTasks;
    }

    //task 추가 함수
    void add_Task(PieEntry e) {
        MyTasks.add(e);
    }

    void add_Task(float f, String label) {
        MyTasks.add(new PieEntry(f, label));
    }

    //백그라운드 리스트에 새로운 색상 추가
    void add_Background(int[] color) {
        MyBackground.add(color);
    }

    void add_Background(int index, int[] color) {
        MyBackground.add(index, color);
    }

    //MyBackground[index]의 컬러를 변경
    void set_Background(int index, int[] color) {
        MyBackground.add(index, color);
        MyBackground.remove(index);
    }

    void setOnWeek(int[] week) {
        for (int i = 0; i < 7; i++)
            OnWeek[i] = week[i];
    }
    int[] getOnWeek(){
        return OnWeek;
    }

    void setOnDate(String date) {
        OnDate = date;
    }

    String getOnDate() {
        return OnDate;
    }

    //*수정필요*
//    void Table_setting() {
//        pieChart.setRotationEnabled(false);
//
//        pieChart.setDragDecelerationFrictionCoef(0f);
//        pieChart.setDrawHoleEnabled(false);
//        pieChart.setHoleColor(Color.WHITE);
//        pieChart.setTransparentCircleRadius(61f);
//
//        Description description = new Description();
//        description.setText("새로운 시간표"); //라벨
//        description.setTextSize(15);
//        pieChart.setDescription(description);
//
//        PieDataSet dataSet = new PieDataSet(MyTasks, "Tasks");
//        dataSet.setSliceSpace(1f);
//        dataSet.setSelectionShift(1f);
////        dataSet.setColors(MyBackground);
//
//        PieData data = new PieData((dataSet));
//        data.setValueTextSize(10f);
//        data.setValueTextColor(Color.YELLOW);
//
//        pieChart.setData(data);
//    }
}
