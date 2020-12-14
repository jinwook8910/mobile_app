package com.example.harujogak;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

class MyTimeTable {
    private String date;
    private PieData pieData;
    private ArrayList<Integer> MyBackground = new ArrayList<>();

    public MyTimeTable(){
        PieDataSet dataSet;
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(1440f, " "));
        dataSet = new PieDataSet(yValues, "Tasks");

        dataSet.setSliceSpace(0.5f);
        dataSet.setSelectionShift(0f);
        this.MyBackground.add(Color.rgb(250,250,250));
        dataSet.setColors(this.MyBackground);

        pieData = new PieData((dataSet));
        pieData.setValueTextSize(0f);
    }

    public MyTimeTable(String date, PieData pieData){
        this.date = date;
        this.pieData = pieData;
        MyBackground.add(Color.rgb(250,250,250));
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