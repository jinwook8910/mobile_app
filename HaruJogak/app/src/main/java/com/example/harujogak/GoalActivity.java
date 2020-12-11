package com.example.harujogak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GoalActivity extends AppCompatActivity {
    Button btn;
    ArrayList<String> goal_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal);

//        //목표 리스트 출력
//        System.out.println("목표리스트 출력");
//        User user = User.getInstance();
//        Iterator it = user.getGoalList().iterator();
//        while(it.hasNext()){
//            Goal goal = (Goal)it.next();
//            goal_list.add(goal.getGoal_name() + goal.getDeadline());
//            System.out.println(goal.getGoal_name() + goal.getDeadline());
//        }

        //test data
        ArrayList<String> goal_list1 = new ArrayList<>();
        goal_list1.add("hello1");
        goal_list1.add("hello2");
        //listview
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, goal_list1);
        ListView listview = (ListView) findViewById(R.id.goal_list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                //listview 객체 클릭할 때 이벤트
            }
        });

        //'추가'버튼
        btn=(Button) findViewById(R.id.goal_btn);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(GoalActivity.this, GoalAddActivity.class);
                startActivity(intent);
            }
        });
    }

}
