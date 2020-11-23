package com.example.harujogak;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;

public class TableItemView extends LinearLayout {

    TextView textView;
    ImageView imageView;

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
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setDate(String date) {
        textView.setText(date);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
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