package com.example.harujogak;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

//TimeTableEdit.java와 합쳐야함
public class TimeTableActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit);
    }
//
//    PieChart pieChart = findViewById(R.id.pieChart);
//    ArrayList NoOfEmp = new ArrayList();
//
////    NoOfEmp.add(new    Entry(945f,0));
////    NoOfEmp.add(new    Entry(1040f,1));
////    NoOfEmp.add(new    Entry(1133f,2));
////    NoOfEmp.add(new    Entry(1240f,3));
////    NoOfEmp.add(new    Entry(1369f,4));
////    NoOfEmp.add(new    Entry(1487f,5));
////    NoOfEmp.add(new    Entry(1501f,6));
////    NoOfEmp.add(new    Entry(1645f,7));
////    NoOfEmp.add(new    Entry(1578f,8));
////    NoOfEmp.add(new    Entry(1695f,9));
//
//    PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
//    ArrayList year = new ArrayList();
//
////    year.add("2008"); year.add("2009");
////    year.add("2010"); year.add("2011");
////    year.add("2012"); year.add("2013");
////    year.add("2014"); year.add("2015");
////    year.add("2016"); year.add("2017");
////
//    PieData data = new PieData(year, dataSet);
    // MPAndroidChart v3.X 오류 발생 pieChart.setData(data); dataSet.setColors(ColorTemplate.COLORFUL_COLORS); pieChart.animateXY(5000, 5000); } }
}