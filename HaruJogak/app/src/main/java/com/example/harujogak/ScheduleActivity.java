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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {
    ImageButton btn1, btn2, btn3, btn4, btn5;
    private ScheduleActivity.ListViewAdapter adapter =  new ScheduleActivity.ListViewAdapter();
    long mNow;
    Date mDate;
    char[] fb_date =new char[20]; //firebase에 키 값으로 들어갈 날짜
    FirebaseDatabase database;
    DatabaseReference myRef;
    Login2 user=new Login2();
    String UserID=user.getUserID();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");
    ScheduleList new_schedule_list;

    protected void onCreate(Bundle savedInstanceState) {
        new_schedule_list= User.getScheduleList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        //title bar 제거하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //navigation button
        btn1 = (ImageButton) findViewById(R.id.goal_navi_btn1);
        btn2 = (ImageButton) findViewById(R.id.goal_navi_btn2);
        btn3 = (ImageButton) findViewById(R.id.goal_navi_btn3);
        btn4 = (ImageButton) findViewById(R.id.goal_navi_btn4);
        btn5 = (ImageButton) findViewById(R.id.goal_navi_btn5);

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);

        //firebase 정의
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();

        CalendarView calendar = findViewById(R.id.calendar);
        TextView calendar_date = findViewById(R.id.calender_date);
        EditText calendar_text = findViewById(R.id.calendar_text);
        ImageButton calendar_btn = findViewById(R.id.calendar_btn);

        //오늘 날짜
        calendar_date.setText(getDate());

        //listview
        ListView listview = (ListView) findViewById(R.id.calendar_list);
        listview.setAdapter(adapter);
        //리스트뷰 아이템 추가
        //adapter.addItem("2020/12/20", "2학기 종강");
        //adapter.addItem("2020/12/31", "연말");
        Schedule new_schedule = (Schedule)new_schedule_list.searchObject(calendar_date.getText().toString());//해당 날짜에 대한 schedule객체 가져오기
        System.out.println("here! "+new_schedule);
        int i;
        if(new_schedule!=null){
            for(i=0;i<new_schedule.getLabel().size();i++){
                adapter.addItem(new_schedule.getDate(), new_schedule.getLabel().get(i));
            }
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                //listview 객체 클릭할 때 이벤트

            }
        });

        //날짜 변경시
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                calendar_date.setText(String.format("%d - %d - %d",year,month+1,dayOfMonth));
                String date=Integer.toString(year)+"년 "+Integer.toString(month+1)+"월 "+Integer.toString(dayOfMonth)+"일";
                fb_date=null;
                fb_date=date.toCharArray();
                calendar_text.setText("");
                //listview설정
                //리스트뷰 아이템 추가
                Schedule new_schedule = (Schedule)new_schedule_list.searchObject(calendar_date.getText().toString());//해당 날짜에 대한 schedule객체 가져오기
                //System.out.println("here! "+new_schedule);
                int i;
                adapter.deleteItem();//listview item 초기화
                if(new_schedule!=null){
                    for(i=0;i<new_schedule.getLabel().size();i++){
                        adapter.addItem(new_schedule.getDate(), new_schedule.getLabel().get(i));
                    }
                }
                adapter.notifyDataSetChanged();

            }
        });
        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //class에 저장
                //Schedule new_schedule = new Schedule(calendar_date.getText().toString() ,calendar_text.getText().toString());
                //User user = User.getInstance();
                //user.getScheduleList().add(new_schedule);

                //firebase
//                String input_date=String.valueOf(fb_date);
//                String getDayGoal=calendar_text.getText().toString();
//                calendar_text.setText("");
//                myRef.child(UserID).child("날짜별 일정").child(input_date).child(getDayGoal).child("방해요소").setValue(0);
//                myRef.child(UserID).child("날짜별 일정").child(input_date).child(getDayGoal).child("평가").setValue(0);
//                myRef.child(UserID).child("날짜별 일정").child(input_date).child(getDayGoal).child("시작시간").setValue("00 : 00");
//                myRef.child(UserID).child("날짜별 일정").child(input_date).child(getDayGoal).child("종료시간").setValue("00 : 00");
//                myRef.child(UserID).child("날짜별 일정").child(input_date).child(getDayGoal).child("장기목표").setValue(0);
                Schedule new_schedule = User.getScheduleList().searchObject(calendar_date.getText().toString());
                if(new_schedule==null){
                    ArrayList<String> labels = new ArrayList<>();
                    labels.add(calendar_text.getText().toString());
                    Schedule schedule = new Schedule(calendar_date.getText().toString() ,labels);
                    User.addScheduleList(schedule);
                }
                else{

                    User.addScheduleList(new_schedule);
                }

                calendar_text.setText("");//추가 후 editText비우기
            }
        });
    }

//    //listview에 data setting
//    private boolean setListview(String date){
//        //new_schedule_list = User.getScheduleList();
//        Schedule new_schedule = (Schedule)new_schedule_list.searchObject(date);
//        System.out.println("here! "+new_schedule);
//        int i;
//        adapter.deleteItem();
//        if(new_schedule!=null){
//            for(i=0;i<new_schedule.getLabel().size();i++){
//                adapter.addItem(new_schedule.getDate(), new_schedule.getLabel().get(i));
//            }
//        }
//
//        return true;
//    }

    private String getDate(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return dateFormat.format(mDate);
    }

    //listview Adapter
    public class ListViewAdapter extends BaseAdapter {
        private ArrayList<String> listViewItemList = new ArrayList<String>();

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
                convertView = inflater.inflate(R.layout.calendar_listview_item, parent, false);
            }
            TextView item_name = (TextView)convertView.findViewById(R.id.calendar_item_text);

            String g = listViewItemList.get(position);
            item_name.setText(g);

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
        public void addItem(String date, String label) {
            //같은 날짜의 schedule 객체를 찾아서 대입해야함

            listViewItemList.add(label);
        }
        public void deleteItem(){
            listViewItemList = new ArrayList<String>();
        }

    }

    //navigation button
    class Listener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                User.getInstance().getGoalList();
                Intent intent = new Intent(ScheduleActivity.this, GoalActivity.class);
                startActivity(intent);
            }
            else if(view==btn2){
                Intent intent =new Intent(ScheduleActivity.this, RatingActivity.class);
                startActivity(intent);
            }
            else if(view==btn3){
                Log.i("MainActivity", "onClickButton");
                Intent intent = new Intent(ScheduleActivity.this, TimeTableListActivity.class);
                startActivity(intent);
            }
            else if(view==btn4) {
                Intent intent = new Intent(ScheduleActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
            else if(view==btn5){
                Intent intent =new Intent(ScheduleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
    ScheduleActivity.Listener listener = new ScheduleActivity.Listener();
}
