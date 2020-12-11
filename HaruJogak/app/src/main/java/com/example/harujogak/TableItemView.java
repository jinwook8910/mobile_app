package com.example.harujogak;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class TableItemView extends LinearLayout {
    private TextView textView;
    private PieChart pieChartView;

    // Generate > Constructor
    public TableItemView(Context context) {
        super(context);
        init(context);
    }

    public TableItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // singer_item.xml을 inflation
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.table_item, this, true);

        textView = (TextView) findViewById(R.id.dateView);
        pieChartView = (PieChart) findViewById(R.id.pieChartView);
    }

    public void setDate(String date) {
        textView.setText(date);
    }

    public PieChart setPieChart(PieData pieData) {
        pieChartView.setData(pieData);

        pieChartView.setRotationEnabled(false);
        pieChartView.getLegend().setEnabled(false);
        pieChartView.getDescription().setEnabled(false);

        pieChartView.setUsePercentValues(false);

        pieChartView.setDrawHoleEnabled(false);
        pieChartView.setHoleColor(Color.WHITE);
        pieChartView.setTransparentCircleRadius(61f);

        return pieChartView;
    }
}

//    TextView textView;
//    PieChart pieChartView;
//
//    // Generate > Constructor
//    public TableItemView(Context context) {
//        super(context);
//        init(context);
//    }
//
//    public TableItemView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    // singer_item.xml을 inflation
//    private void init(Context context) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.table_item, this, true);
//
//        textView = (TextView) findViewById(R.id.dateView);
//        pieChartView = (PieChart) findViewById(R.id.pieChartView);
//    }
//
//    public void setDate(String date) {
//        textView.setText(date);
//    }
//
//    public void setPieChart(int resId) {
//        pieChartView.setBackgroundResource(resId);
//    }
//
//}