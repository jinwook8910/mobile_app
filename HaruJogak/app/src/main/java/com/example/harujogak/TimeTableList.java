package com.example.harujogak;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TimeTableList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_list);

        GridView gv = (GridView) findViewById(R.id.gridView1);
        final TableAdapter tableAdapter = new TableAdapter(this);

        tableAdapter.addItem(new TableItem("2020-11-14", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItem("2021-05-24", R.drawable.empty_graph));
        tableAdapter.addItem(new TableItem("2022-12-14", R.drawable.empty_graph));

        gv.setAdapter(tableAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TableItem item = (TableItem) tableAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 :" + item.getDate(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(TimeTableList.this, TimeTableEdit.class);
                startActivity(intent);
            }
        });

    } // end of onCreate

    class TableAdapter extends BaseAdapter {
        Context context;
        ArrayList<TableItem> items = new ArrayList<TableItem>();

        public TableAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(TableItem item) {
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
            TableItem item = items.get(position);

            view.setDate(item.getDate());
            view.setImage(item.getResId());

            return view;
        }
    }

} // end of class

//https://bitsoul.tistory.com/41