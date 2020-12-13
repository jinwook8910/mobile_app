package com.example.harujogak;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

class MyTimeTable {
    private String date;
    private PieData pieData;
    private ArrayList<int[]> MyBackground = new ArrayList<>();
    private ArrayList<Task> MyTasks = new ArrayList<>();

    public MyTimeTable(){    }

    public MyTimeTable(String date, PieData pieData){
        this.date = date;
        this.pieData = pieData;
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

    void setMyBackground(ArrayList<int[]> background) {
        MyBackground = background;
    }

    ArrayList<int[]> getMyBackground() {
        return MyBackground;
    }

    void setMyTasks(ArrayList<Task> tasks) {
        MyTasks = tasks;
    }

    ArrayList<Task> getMyTasks() {
        return MyTasks;
    }

    //task 추가 함수
    void add_Task(Task e) {
        MyTasks.add(e);
    }

    void add_Task(String label, String startTime, String endTime) {
        MyTasks.add(new Task(label, startTime, endTime));
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

class Task {
    String label;
    String startTime, endTime;
    String relativeGoal;

    public Task() {

    }

    public Task(String label, String startTime, String endTime) {
        this.label = label;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    void setLabel(String label) {
        this.label = label;
    }

    String getLabel() {
        return label;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setRelativeGoal(String relativeGoal) {
        this.relativeGoal = relativeGoal;
    }

    public String getRelativeGoal() {
        return relativeGoal;
    }
}