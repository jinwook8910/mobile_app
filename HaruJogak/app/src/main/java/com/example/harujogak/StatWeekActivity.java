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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StatWeekActivity extends AppCompatActivity {
    ImageButton left, right;
    ImageButton btn1, btn2, btn3, btn4, btn5;
    private StatWeekActivity.ListViewAdapter adapter =  new StatWeekActivity.ListViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_week);

        //title bar 제거하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //navigation button
        btn1 = (ImageButton) findViewById(R.id.stat_week_navi_btn1);
        btn2 = (ImageButton) findViewById(R.id.stat_week_navi_btn2);
        btn3 = (ImageButton) findViewById(R.id.stat_week_navi_btn3);
        btn4 = (ImageButton) findViewById(R.id.stat_week_navi_btn4);
        btn5 = (ImageButton) findViewById(R.id.stat_week_navi_btn5);

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        //listview
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, goal_list);
        ListView listview = (ListView) findViewById(R.id.stat_week_list);
        listview.setAdapter(adapter);
        //리스트뷰 아이템 추가
        adapter.addItem("토익시험", new Integer(100));
        adapter.addItem("발표", new Integer(35));
        adapter.addItem("테스트", new Integer(15));
        adapter.addItem("테스트2", new Integer(0));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                //listview 객체 클릭할 때 이벤트

            }
        });

        //좌우 버튼 클릭
        left = (ImageButton) findViewById(R.id.week_left_btn);
        right = (ImageButton) findViewById(R.id.week_right_btn);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatWeekActivity.this, StatDayActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_right,R.anim.slide_right);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatWeekActivity.this, StatObstructActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_right,R.anim.slide_right);
            }
        });

    }
    //listview Item
    public class StatWeek {
        String name;
        Integer percent;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPercent() {
            return percent;
        }

        public void setPercent(Integer percent) {
            this.percent = percent;
        }
    }

    //listview Adapter
    public class ListViewAdapter extends BaseAdapter {
        private ArrayList<StatWeek> listViewItemList = new ArrayList<StatWeek>();

        @Override
        public int getCount(){
            return listViewItemList.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.stat_week_listview_item, parent, false);
            }
            TextView item_name = (TextView)convertView.findViewById(R.id.stat_week_item_name);
            View item_chart = (View)convertView.findViewById(R.id.stat_week_item_chart);
            TextView item_percent = (TextView)convertView.findViewById(R.id.stat_week_item_percent);
            View item_blank = (View)convertView.findViewById(R.id.stat_week_blank);

            StatWeek g = listViewItemList.get(position);
            item_name.setText(g.getName());
            item_percent.setText(g.getPercent().toString()+"%");
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(100, 25);
            item_chart.setLayoutParams(param);
            item_chart.getLayoutParams().width=g.getPercent()*730/100;
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(100, 1);
            item_blank.setLayoutParams(param2);
            item_blank.getLayoutParams().width=g.getPercent()*570/100;
            //나누기 100후 *600(scaling) //정수라서 /100부터하면 0 => 순서바꿈

            return convertView;
        }
        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            return position ;
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            return listViewItemList.get(position) ;
        }

        // 아이템 데이터 추가를 위한 함수.
        public void addItem(String name, Integer percent) {
            StatWeek item = new StatWeek();

            item.setName(name);
            item.setPercent(percent);

            listViewItemList.add(item);
        }

    }

    //navigation button
    class Listener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                Intent intent = new Intent(StatWeekActivity.this, GoalActivity.class);
                startActivity(intent);
            }
            else if(view==btn2){
                Intent intent =new Intent(StatWeekActivity.this,Rating.class);
                startActivity(intent);
            }
            else if(view==btn3){
                Log.i("MainActivity", "onClickButton");
                Intent intent = new Intent(StatWeekActivity.this, TimeTableListActivity.class);
                startActivity(intent);
            }
            else if(view==btn4) {
                Intent intent = new Intent(StatWeekActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
            else if(view==btn5){
                Intent intent =new Intent(StatWeekActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
    StatWeekActivity.Listener listener = new StatWeekActivity.Listener();
}
