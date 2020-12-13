package com.example.harujogak;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
<<<<<<< HEAD
=======
import java.util.Date;
import java.util.Locale;
>>>>>>> 98b60534f93159b0743fed05f5b1ad4d8350f7e6

public class GoalActivity extends AppCompatActivity {
    Button btn;
    ArrayList<String> goal_list=new ArrayList<>();
    MainActivity main=new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal);
        goal_list=MainActivity.getGoal_list();
//        //목표 리스트 출력
//        System.out.println("목표리스트 출력");
//        User user = User.getInstance();
//        Iterator it = user.getGoalList().iterator();
//        while(it.hasNext()){
//            Goal goal = (Goal)it.next();
//            goal_list.add(goal.getGoal_name() + goal.getDeadline());
//            System.out.println(goal.getGoal_name() + goal.getDeadline());
//        }
        //listview
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, goal_list);
        ListView listview = (ListView) findViewById(R.id.goal_list);
        listview.setAdapter(adapter);

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

        addGoalDialog.setContentView(R.layout.goal_add);
        addGoalDialog.setTitle("일정 추가");

        CalendarView goal_calendar = addGoalDialog.findViewById(R.id.goal_add_calendar);
        TextView goal_text = addGoalDialog.findViewById(R.id.goal_add_text);
        EditText goal_input = addGoalDialog.findViewById(R.id.goal_add_input);
        TextView goal_result = addGoalDialog.findViewById(R.id.goal_add_result);
        Button goal_btn = findViewById(R.id.calendar_btn);
        Button goal_add_btn=addGoalDialog.findViewById(R.id.goal_add_btn);

        goal_text.setText("나의 목표는");
        goal_result.setText("D-day");

        goal_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                String goal_date; //선택한 날짜 문자열로 저장
                int dday;
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
                goal_date= String.format("%d / %d / %d",year,month+1,dayOfMonth);

                //날짜 초단위로 변경
                t=tcalendar.getTimeInMillis()/(24*60*60*1000);
                d=dcalendar.getTimeInMillis()/(24*60*60*1000);
                dday=(int)(t-d);
                if(dday>0)
                    goal_result.setText("D+"+Integer.toString(dday));
                else if(dday==0)
                    goal_result.setText("D-day");
                else
                    goal_result.setText("D"+Integer.toString(dday));
            }
        });

        goal_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //class에 저장
                //Goal new_goal = new Goal(goal_input.getText().toString(), goal_date); //goal class에 생성자 만들기
                //User user = User.getInstance(); //현재 사용중인 사용자
                //user.getGoalList().add(new_goal);

                //이전 Activity로 돌아가기
                addGoalDialog.dismiss();
            }
        });

        addGoalDialog.show();
    }
}
