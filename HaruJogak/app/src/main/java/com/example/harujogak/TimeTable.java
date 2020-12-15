package com.example.harujogak;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

class MyTimeTable {
    private String date;
    private PieData pieData;
    private int TasksCount;
    private ArrayList<Integer> MyBackground = new ArrayList<>();
    private ArrayList<Integer[]> rating = new ArrayList<>();

    public MyTimeTable(){
        PieDataSet dataSet;
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        TasksCount=0;
        yValues.add(new PieEntry(1440f, " "));
        dataSet = new PieDataSet(yValues, "TasksCount");

        dataSet.setSliceSpace(0.5f);
        dataSet.setSelectionShift(0f);
        this.MyBackground.add(Color.rgb(250,250,250));
        dataSet.setColors(this.MyBackground);

        pieData = new PieData((dataSet));
        pieData.setValueTextSize(0f);
    }

    public MyTimeTable(String date){
        this.date = date;
        PieDataSet dataSet;
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        TasksCount=0;
        yValues.add(new PieEntry(1440f, " "));
        dataSet = new PieDataSet(yValues, "TasksCount");

        dataSet.setSliceSpace(0.5f);
        dataSet.setSelectionShift(0f);
        this.MyBackground.add(Color.rgb(250,250,250));
        dataSet.setColors(this.MyBackground);

        pieData = new PieData((dataSet));
        pieData.setValueTextSize(0f);
    }

    public int getTasksCount() {
        return TasksCount;
    }

    public void setTasksCount(int tasksCount) {
        TasksCount = tasksCount;
    }

    public void setRating(ArrayList<Integer[]> rating) {
        this.rating = rating;
    }

    public ArrayList<Integer[]> getRating() {
        return rating;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    void setPieData(PieData p) {
        pieData = p;
    }

    PieData getPieData() {
        return pieData;
    }

    void setMyBackground(ArrayList<Integer> background) {
        this.MyBackground = background;
        PieDataSet dataSet = (PieDataSet)this.getPieData().getDataSet();
        dataSet.setColors(this.MyBackground);
        this.pieData.setDataSet(dataSet);
    }

    ArrayList<Integer> getMyBackground() {
        return MyBackground;
    }

    //백그라운드 리스트에 새로운 색상 추가
    void add_Background(Integer color) {
        MyBackground.add(color);
    }

    void add_Background(int index, Integer color) {
        MyBackground.add(index, color);
    }

    //MyBackground[index]의 컬러를 변경
    void set_Background(int index, Integer color) {
        MyBackground.add(index, color);
        MyBackground.remove(index);
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
//        PieDataSet dataSet = new PieDataSet(MyTasks, "TasksCount");
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