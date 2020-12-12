package com.example.harujogak;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Member;
import java.util.ArrayList;

public class TimeTableListActivity extends AppCompatActivity {
    // 리사이클러뷰에 표시할 데이터 리스트 생성.
    // 나중에 사용자 정보에 저장된 ArrayList를 가져와서 동작시켜야함
    private ArrayList<TableItemByDay> week = new ArrayList<>(7);
    private TableAdapter tableAdapter;

    MyTimeTable exT = new MyTimeTable();
    MyTimeTable exTable = new MyTimeTable();

    public void setdata1(){
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(34f, "Japan"));
        yValues.add(new PieEntry(23f, "USA"));
        yValues.add(new PieEntry(14f, "UK"));
        yValues.add(new PieEntry(35f, "India"));
        yValues.add(new PieEntry(40f, "Russia"));
        yValues.add(new PieEntry(40f, "Korea"));

        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(1f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(0f);
        data.setValueTextColor(Color.YELLOW);
        exTable.setPieData(data);

    }

    public void setdata2(){
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(40f, "Jan"));
        yValues.add(new PieEntry(13f, "S"));
        yValues.add(new PieEntry(17f, "U"));
        yValues.add(new PieEntry(35f, "I"));
        yValues.add(new PieEntry(20f, "R"));
        yValues.add(new PieEntry(40f, "K"));

        PieDataSet dataSet = new PieDataSet(yValues, "temp");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(1f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(0f);
        data.setValueTextColor(Color.YELLOW);

        exT.setPieData(data);

    }

    public void addToAdapter(Context c){
        tableAdapter = new TableAdapter(c);
        tableAdapter.addItem(new TableItemByDate("2018-11-23", exTable));
        tableAdapter.addItem(new TableItemByDate("2018-01-02", exTable));
        tableAdapter.addItem(new TableItemByDate("2023-01-13", exTable));
        tableAdapter.addItem(new TableItemByDate("2013-06-16", exT));
        tableAdapter.addItem(new TableItemByDate("2017-12-03", exTable));
        tableAdapter.addItem(new TableItemByDate("2010-09-21", exT));
    }
    public void addToList(){
        week.add(new TableItemByDay("월", exT));
        week.add(new TableItemByDay("화", exTable));
        week.add(new TableItemByDay("수", exT));
        week.add(new TableItemByDay("목", exTable));
        week.add(new TableItemByDay("금", exT));
        week.add(new TableItemByDay("토", exT));
        week.add(new TableItemByDay("일", exTable));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_list);

        GridView gv = (GridView) findViewById(R.id.gridView1);
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;

        setdata1();
        setdata2();
        addToAdapter(this);
        addToList();

        // 그리드뷰 어댑터, 리스너
        gv.setAdapter(tableAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TableItemByDate item = (TableItemByDate) tableAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 :" + item.getDate(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(TimeTableListActivity.this, TimeTableEditActivity.class);
                startActivity(intent);
            }
        });

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SimpleTextAdapter adapter = new SimpleTextAdapter(week) ;
        recyclerView.setAdapter(adapter) ;

    } // end of onCreate


    // 그리드뷰 어댑터
    class TableAdapter extends BaseAdapter {
        Context context;
        ArrayList<TableItemByDate> items = new ArrayList<TableItemByDate>();

        public TableAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(TableItemByDate item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TableItemView view;
            if (convertView == null) {
                view = new TableItemView(context);
                view.setLayoutParams(new GridView.LayoutParams(320, 390));
                view.setPadding(1, 1, 1, 1);
            } else {
                view = (TableItemView) convertView;
            }
            TableItemByDate item = items.get(position);

            view.setDate(item.getDate());
            view.setPieChart(item.getMyTimeTable().getPieData()).setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    Intent intent = new Intent(TimeTableListActivity.this, TimeTableEditActivity.class);
//                    intent.putExtra("TableItemByDate", item);
                    startActivity(intent);
                }

                @Override
                public void onNothingSelected() {
                }
            });

            return view;
        }
    }


    // 리사이클러뷰 어댑터
    class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {
        private ArrayList<TableItemByDay> weekSchedule = null;

        // 아이템 뷰를 저장하는 뷰홀더 클래스.
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView1;
            PieChart pieChart;

            ViewHolder(View itemView) {
                super(itemView);
                // 뷰 객체에 대한 참조. (hold strong reference)
                textView1 = itemView.findViewById(R.id.dateView);
                pieChart = itemView.findViewById(R.id.pieChartView);

                pieChart.setRotationEnabled(false);
                pieChart.getLegend().setEnabled(false);
                pieChart.getDescription().setEnabled(false);

                pieChart.setUsePercentValues(false);

                pieChart.setDrawHoleEnabled(false);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            TableItemByDate item = (TableItemByDate) tableAdapter.getItem(pos);
                            Toast.makeText(getApplicationContext(), "선택 :" + item.getDate(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(TimeTableListActivity.this, TimeTableEditActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        Log.i("Onclick", "goEditPage");
                        Intent intent = new Intent(TimeTableListActivity.this, TimeTableEditActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onNothingSelected() {
                    }
                });
            }
        }

        // 생성자에서 데이터 리스트 객체를 전달받음.
        SimpleTextAdapter(ArrayList<TableItemByDay> list) {
            weekSchedule = list;
        }

        // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
        @Override
        public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.table_item, parent, false);
            SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view);

            return vh;
        }

        // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
        @Override
        public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position) {
            TableItemByDay data = weekSchedule.get(position);
            holder.textView1.setText(data.getDay());
            PieData piedata;
            piedata = data.getMyTimeTable().getPieData();
            holder.pieChart.setData(piedata);
        }

        // getItemCount() - 전체 데이터 갯수 리턴.
        @Override
        public int getItemCount() {
            return weekSchedule.size();
        }
    }
} // end of class

//https://bitsoul.tistory.com/41