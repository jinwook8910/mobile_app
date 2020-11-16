package com.example.harujogak;

import android.content.Context;
import android.content.Intent;
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

import java.lang.reflect.Member;
import java.util.ArrayList;

public class TimeTableList extends AppCompatActivity {

    // 리사이클러뷰에 표시할 데이터 리스트 생성.
    ArrayList<TableItemByDay> week = new ArrayList<>(7);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_list);

        GridView gv = (GridView) findViewById(R.id.gridView1);
        TableAdapter tableAdapter = new TableAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;

        tableAdapter.addItem(new TableItemByDate("2020-11-14", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("<label>", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("날짜설정하면", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("2020-11-14", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("<label>", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("보여주는곳", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("2020-11-14", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("<label>", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItemByDate("날짜", R.drawable.empty_graph));

        week.add(new TableItemByDay("월요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("화요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("수요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("목요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("금요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("토요일", R.drawable.empty_graph));
        week.add(new TableItemByDay("일요일", R.drawable.empty_graph));

        gv.setAdapter(tableAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TableItemByDate item = (TableItemByDate) tableAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 :" + item.getDate(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(TimeTableList.this, TimeTableEdit.class);
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