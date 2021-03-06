package com.example.harujogak;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class GoalActivity extends AppCompatActivity {
    //firebase 정의
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button btn;
    ImageButton btn1, btn2, btn3, btn4, btn5;
    int i;
    HashMap<String,Integer> goal_stat=MainActivity.getGoal_stat();
    ArrayList<Goal> goal_list=new ArrayList<>();
    MainActivity main=new MainActivity();
    //User user = new User(); //사용자
    //Login2 login=new Login2();
    String UserID=Login2.getUserID();
    private ListViewAdapter adapter =  new ListViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();
        //title bar 제거하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        
       // goal_stat=MainActivity.getGoal_stat();
        goal_list=User.getInstance().getGoalList();
        System.out.println("GoalActivity!! "+goal_list);

//        //목표 리스트 출력
//        System.out.println("목표리스트 출력");
//        User user = User.getInstance();
//        Iterator it = user.getGoalList().iterator();
//        while(it.hasNext()){
//            Goal goal = (Goal)it.next();
//            goal_list.add(goal.getGoal_name() + goal.getDeadline());
//            System.out.println(goal.getGoal_name() + goal.getDeadline());
//        }
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
        //listview
        ListView listview = (ListView) findViewById(R.id.goal_list);
        listview.setAdapter(adapter);
        if(goal_list!=null) {
            for (int i = 0; i < goal_list.size(); i++) {
                adapter.addItem(goal_list.get(i).getGoal_name(), goal_list.get(i).getDeadline(),goal_list.get(i).getStartday());
            }
        }
        //리스트뷰 아이템 추가
//        for(String t:goal_list.keySet()){
//            if(goal_list.get(t)<0) {
//                adapter.addItem(t, "D" + goal_list.get(t));
//            }
//            else adapter.addItem(t, "D+" + goal_list.get(t));
//        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                //listview 객체 클릭할 때 이벤트

            }
        });
    }

    //추가 버튼
    public void onClickAddGoalButton(View v){
        Dialog addGoalDialog = new Dialog(this);

        addGoalDialog.setContentView(R.layout.goal_add_dialog);
        addGoalDialog.setTitle("목표 추가");

        ImageButton goal_exit_btn = addGoalDialog.findViewById(R.id.goal_exit_btn);
        CalendarView goal_calendar = addGoalDialog.findViewById(R.id.goal_add_calendar);
        TextView goal_text = addGoalDialog.findViewById(R.id.goal_add_text);
        EditText goal_input = addGoalDialog.findViewById(R.id.goal_add_input);
        TextView goal_result = addGoalDialog.findViewById(R.id.goal_add_result);
        ImageButton goal_add_btn=addGoalDialog.findViewById(R.id.goal_add_btn);

        final String[] goal_date = new String[1];
        final String[] start_day= new String[1];
        final int[] dday = new int[1];
        goal_text.setText("목표 추가");
        goal_result.setText("D-0");

        goal_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                //선택한 날짜 문자열로 저장
                int tYear, tMonth, tDay;
                long t, d;

                Calendar tcalendar = Calendar.getInstance();
                Calendar dcalendar = Calendar.getInstance();

                //오늘 날짜
                tYear = tcalendar.get(Calendar.YEAR);
                tMonth = tcalendar.get(Calendar.MONTH);
                tDay = tcalendar.get(Calendar.DAY_OF_MONTH);

                //목표 날짜
                dcalendar.set(year, month, dayOfMonth);
                String date=Integer.toString(year)+"년 "+Integer.toString(month+1)+"월 "+Integer.toString(dayOfMonth)+"일";
                goal_date[0] = String.format("%d - %d - %d",year,month+1,dayOfMonth);
                start_day[0]=String.format("%d - %d - %d",tYear,tMonth+1,tDay);

                //날짜 초단위로 변경
                t=tcalendar.getTimeInMillis()/(24*60*60*1000);
                d=dcalendar.getTimeInMillis()/(24*60*60*1000);
                dday[0] =(int)(t-d);

                if(dday[0] >0)
                    goal_result.setText("D+"+Integer.toString(dday[0]));
                else if(dday[0] ==0)
                    goal_result.setText("D-day");
                else
                    goal_result.setText("D"+Integer.toString(dday[0]));

            }
        });

        goal_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //class에 저장->왜 저장이 안되지?????ㅜㅜㅜ
//                Goal new_goal = new Goal(goal_input.getText().toString(), goal_date[0]);//goal class에 생성자 만들기
//                ArrayList<Goal> temp=User.getGoalList();
//                temp.add(new_goal);
//                User.setGoalList(temp);

                //여기서 바로 firebase에 추가하는 경우
                myRef.child(UserID).child("목표리스트").child(goal_input.getText().toString()).child("목표 D-day").setValue(dday[0]);
                myRef.child(UserID).child("목표리스트").child(goal_input.getText().toString()).child("목표 날짜").setValue(goal_date[0]);
                myRef.child(UserID).child("목표리스트").child(goal_input.getText().toString()).child("시작 날짜").setValue(start_day[0]);
                //바로 화면에 추가되려면?
                adapter.notifyDataSetChanged(); //adapter에 데이터 변경 알림

                //firebase에 저장
                Goal new_goal = new Goal(goal_input.getText().toString(), start_day[0], goal_date[0]);
                User.getInstance().addGoal(new_goal); //왜 메인페이지로 돌아가지..
                //이전 Activity로 돌아가기
                addGoalDialog.dismiss();
            }
        });

        goal_exit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addGoalDialog.dismiss();
            }
        });

        addGoalDialog.show();
    }

    //listview Adapter
    public class ListViewAdapter extends BaseAdapter {
        private ArrayList<Goal> listViewItemList = new ArrayList<Goal>();

        @Override
        public int getCount(){
            return listViewItemList.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();
            int percent;
            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.goal_listview_item, parent, false);
            }
            TextView item_name = (TextView)convertView.findViewById(R.id.goal_item_name);
            TextView item_dday = (TextView)convertView.findViewById(R.id.goal_item_dday);
            View item_chart = (View)convertView.findViewById(R.id.goal_item_chart);
            View item_blank = (View)convertView.findViewById(R.id.goal_blank);
            TextView item_percent = (TextView)convertView.findViewById(R.id.goal_item_percent);

            Goal g = listViewItemList.get(position);

            item_name.setText(g.getGoal_name());

            if(g.getDday() >0)
                item_dday.setText("D+"+Integer.toString(g.getDday()));
            else if(g.getDday() ==0)
                item_dday.setText("D-day");
            else
                item_dday.setText("D"+Integer.toString(g.getDday()));

            int d_day=Math.abs(g.getDday());
            if(d_day==0){percent=100;}
            else {
                if (goal_stat.get(g.getGoal_name()) != null) {
                    percent = goal_stat.get(g.getGoal_name()) / d_day * 20;
                    if (percent > 100) percent = 100;
                    item_percent.setText(percent + "%");
                } else {
                    goal_stat.put(g.getGoal_name(), 0);
                    percent = goal_stat.get(g.getGoal_name()) / d_day * 20;
                    if (percent > 100) percent = 100;
                    item_percent.setText(percent + "%");
                }
            }

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(100, 25);
            item_chart.setLayoutParams(param);
            item_chart.getLayoutParams().width=percent*470/100;
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(100, 1);
            item_blank.setLayoutParams(param2);
            item_blank.getLayoutParams().width=percent*430/100;

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
        public void addItem(String name, String deadline,String start) {
            Goal item = new Goal(name, start, deadline);

            item.setGoal_name(name);
            item.setDeadline(deadline);
            item.setStartday(start);

            listViewItemList.add(item);
        }

    }

    //navigation button
    class Listener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                User.loadScheduleList();
                Intent intent = new Intent(GoalActivity.this, GoalActivity.class);
                startActivity(intent);
            }
            else if(view==btn2){
                Intent intent =new Intent(GoalActivity.this,StatDayActivity.class);
                startActivity(intent);
            }
            else if(view==btn3){
                Log.i("MainActivity", "onClickButton");
                Intent intent = new Intent(GoalActivity.this, TimeTableListActivity.class);
                startActivity(intent);
            }
            else if(view==btn4) {
                Intent intent = new Intent(GoalActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
            else if(view==btn5){
                Intent intent =new Intent(GoalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
    GoalActivity.Listener listener = new GoalActivity.Listener();
}
