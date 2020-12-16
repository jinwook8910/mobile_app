package com.example.harujogak;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

class MyTimeTable {
    private String date;    //해당 시간표가 적용될 날짜. (주간 시간표면 월~일, 일일 시간표면 yyyy-mm-dd)
    private PieData pieData;    //파이 차트 그리는데 사용되는 정보. 내부에 일정 정보(이름, 시간)가 들어있음
    private int TasksCount;     //이 시간표에 들어가는 일정 개수
    private ArrayList<Integer> MyBackground;    //파이차트 그리는데 사용되는 정보. 파이 배경색을 저장함
    private ArrayList<Integer[]> rating;        //해당 시간표의 일정을 저장해 놓는 integer 배열. 그 배열에 대한 어레이리스트

    public MyTimeTable(){
        date = " ";
        init();
    }

    public MyTimeTable(String date){
        this.date = date;
        init();
    }

    private void init(){
        TasksCount=0;
        MyBackground = new ArrayList<>();
        this.MyBackground.add(Color.rgb(250,250,250));
        rating = new ArrayList<>();

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        yValues.add(new PieEntry(1440f, " "));
        PieDataSet dataSet = new PieDataSet(yValues, "TasksCount");

        dataSet.setSliceSpace(0.5f);
        dataSet.setSelectionShift(0f);
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

}