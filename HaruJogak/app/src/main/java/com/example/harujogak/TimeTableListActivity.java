package com.example.harujogak;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Member;
import java.util.ArrayList;

// Todo : 이미지 대신에 piechart 넣기. 근데 piechart를 어떻게 resID로 나타낼 수 있을까..

public class TimeTableListActivity extends AppCompatActivity {
    // 리사이클러뷰에 표시할 데이터 리스트 생성.
    // 나중에 사용자 정보에 저장된 ArrayList를 가져와서 동작시켜야함
    private ArrayList<TableItemByDay> week = new ArrayList<>(7);
    private TableAdapter tableAdapter;

    private PieChart pieChart;

    public void setTemp(){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(34f, "Japan"));
        yValues.add(new PieEntry(23f, "USA"));
        yValues.add(new PieEntry(14f, "UK"));
        yValues.add(new PieEntry(35f, "India"));
        yValues.add(new PieEntry(40f, "Russia"));
        yValues.add(new PieEntry(40f, "Korea"));

        Description description = new Description();
        description.setText("세계 국가"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(1f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

    }

    public void addToAdapter(Context c){
        tableAdapter = new TableAdapter(c);
        tableAdapter.addItem(new TableItemByDate("2020-11-14", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("<label>", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("날짜설정하면", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("2020-11-14", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("<label>", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("보여주는곳", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("2020-11-14", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("<label>", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("날짜", R.drawable.empty_graph));
    }
    public void addToList(){
        week.add(new TableItemByDay("월요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("화요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("수요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("목요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("금요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("토요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("일요일", R.drawable.empty_graph));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_list);

        GridView gv = (GridView) findViewById(R.id.gridView1);
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;

        addToAdapter(this);
        addToList();

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
            Log.d("Adapter", "pos: " + position);
            if (convertView == null) {
                view = new TableItemView(context);
                view.setLayoutParams(new GridView.LayoutParams(320, 390));
                view.setPadding(1, 1, 1, 1);
            } else {
                view = (TableItemView) convertView;
            }
            TableItemByDate item = items.get(position);

            view.setDate(item.getDate());
            view.setImage(item.getResId());

            return view;
        }
    }

    class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {
        private ArrayList<TableItemByDay> weekSchedule = null;

        // 아이템 뷰를 저장하는 뷰홀더 클래스.
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView1;
            ImageView imageView;
            //PieChart pieChar;

            ViewHolder(View itemView) {
                super(itemView);
                // 뷰 객체에 대한 참조. (hold strong reference)
                textView1 = itemView.findViewById(R.id.dateView);
                imageView = itemView.findViewById(R.id.imageView);
//                pieChar = itemView.findViewById(R.id.imageView) ;
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
            holder.textView1.setText(data.getDate());
            holder.imageView.setImageResource(data.getResId());
        }

        // getItemCount() - 전체 데이터 갯수 리턴.
        @Override
        public int getItemCount() {
            return weekSchedule.size();
        }
    }
} // end of class

//https://bitsoul.tistory.com/41