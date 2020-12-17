package com.example.harujogak;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class User {
    private static volatile User instance = null;
    private String id, passWord, eMail;
    private static ArrayList<MyTimeTable> weekTable = new ArrayList<MyTimeTable>();   //주간 시간표 저장하는 리스트
    private static ArrayList<MyTimeTable> dateTable = new ArrayList<MyTimeTable>();   //일일 시간표 저장하는 리스트
    private static ArrayList<Goal> goalList = new ArrayList<Goal>();  //목표 저장하는 리스트
    private static ArrayList<Obstruct> obstructList = new ArrayList<Obstruct>();   //방해요소 저장하는 리스트
    private static ScheduleList scheduleList = new ScheduleList();  //캘린더에 일정 저장하는 리스트

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReferenceFromUrl("https://team4-cab1f.firebaseio.com/");;
    private static Login2 user2=new Login2();
    private static String UserID=user2.getUserID();

    public User(){
        this.weekTable = new ArrayList<>(7);
        weekTable.add(new MyTimeTable("월요일"));
        weekTable.add(new MyTimeTable("화요일"));
        weekTable.add(new MyTimeTable("수요일"));
        weekTable.add(new MyTimeTable("목요일"));
        weekTable.add(new MyTimeTable("금요일"));
        weekTable.add(new MyTimeTable("토요일"));
        weekTable.add(new MyTimeTable("일요일"));

        this.dateTable = new ArrayList<>();
        this.goalList = new ArrayList<>();
        this.obstructList = new ArrayList<>();
        this.scheduleList = new ScheduleList();
    } //임시 테스트용

    public User(String user_id, String user_pw){
        this.id = user_id;
        this.passWord = user_pw;
        init();
    }

    private void init(){
        //객체 생성하면서
        //Todo : firebase에 이 사용자 정보가 있으면 불러와서 저장
        // (...)
        // 아니면 초기화
        this.weekTable = new ArrayList<>(7);
        weekTable.add(new MyTimeTable("월요일"));
        weekTable.add(new MyTimeTable("화요일"));
        weekTable.add(new MyTimeTable("수요일"));
        weekTable.add(new MyTimeTable("목요일"));
        weekTable.add(new MyTimeTable("금요일"));
        weekTable.add(new MyTimeTable("토요일"));
        weekTable.add(new MyTimeTable("일요일"));

        this.dateTable = new ArrayList<>();
        this.goalList = new ArrayList<>();
        this.obstructList = new ArrayList<>();
        this.scheduleList = new ScheduleList();
    }

    public static User getInstance(String user_id, String user_pw){
        if(instance == null){
            instance = new User(user_id, user_pw);
        }
        return instance;
    }

    public static User getInstance(){ //객체 하나만 존재하도록 함
        return instance;
    }

    public String getId() {
        return id;
    }

    public static void load(){//database에서 정보 가져오기
        loadGoalList();
        loadObstructList();
        loadScheduleList();
    }

    public ArrayList<MyTimeTable> getWeekTable() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        return weekTable;
    }

    public void addWeekTable(int i, MyTimeTable table){
        //Todo : firebase에 동일하게 저장
        this.weekTable.set(i, table);
    }

    public ArrayList<MyTimeTable> getDateTable() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        return dateTable;
    }

    public void addDateTable(MyTimeTable table){
        boolean isExist = false;
        for(int i=0; i<dateTable.size(); i++){
            if(table.getDate().equals(dateTable.get(i).getDate())){
                //Todo : firebase에 동일하게 저장
                dateTable.set(i, table);
                isExist = true;
            }
        }
        // 없으면 리스트에 새로 추가함
        if(!isExist){
            //Todo : firebase에 동일하게 저장
            this.dateTable.add(table);
        }
    }

    public static void loadDateTable(){
        //firebase
        DatabaseReference data;
        ValueEventListener dataListener;
        ArrayList<MyTimeTable> time_table_list = new ArrayList<>();

//        //firebase - 날짜별 일정
//        data = myRef.child(UserID).child("날짜별 일정");
//        dataListener = data.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() == null) {
//                } else {
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        if (ds.getValue() != null) {
//                            String date = ds.getKey();
//                            ds.child("파이크기").getValue().toString(); //{"32f/510f/12f"} 형태로 저장
//                            ds.child("파이라벨").getValue().toString(); //{"라벨1/라벨2/라벨3"} 형태로 저장
//                            ds.child("일정갯수").getValue().toString();
//                            ds.child("배경색").getValue().toString(); //{"1321/510/12231"} 형태로 저장
//
////                            String dday = ds.child("목표 날짜").getValue().toString();
////                            String start = ds.child("시작 날짜").getValue().toString();
//
//                            Goal goal_1 = new Goal(goal, start, dday);
//                            System.out.println("waaaa! " + goal_1.getGoal_name());
//                            //User.getGoalList().add(goal_1);
//                            goal_list.add(goal_1);
//                        }
//                    }
//                    System.out.println("yaaaaaaaaaaa! " + goal_list);
//                    User.setGoalList(goal_list);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("TAG", "Firebase error");
//            }
//        });
    }

    public static ArrayList<Goal> getGoalList() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        User.loadGoalList();
        return goalList;
    }

    public static void setGoalList(ArrayList<Goal> goalList_r) {
        goalList = goalList_r;
    }

    public static void addGoal(Goal new_goal){
        User.getGoalList().add(new_goal);
        DatabaseReference data;
        data = myRef.child(UserID).child("목표리스트");
        data.child(new_goal.getGoal_name()).child("목표 날짜").setValue(new_goal.getDeadline());
        data.child(new_goal.getGoal_name()).child("시작 날짜").setValue(new_goal.getStartday());
    }

    public static void loadGoalList() {
        //Todo : firebase에 동일하게 저장
        //firebase
        DatabaseReference data;
        ValueEventListener dataListener;
        ArrayList<Goal> goal_list = new ArrayList<>();

        //firebase - 목표 통계
        data = myRef.child(UserID).child("목표리스트");
        dataListener = data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                } else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue() != null) {
                            String goal = ds.getKey();
                            String dday = ds.child("목표 날짜").getValue().toString();
                            String start = ds.child("시작 날짜").getValue().toString();

                            Goal goal_1 = new Goal(goal, start, dday);
                            System.out.println("waaaa! " + goal_1.getGoal_name());
                            //User.getGoalList().add(goal_1);
                            goal_list.add(goal_1);
                        }
                    }
                    System.out.println("yaaaaaaaaaaa! " + goal_list);
                    User.setGoalList(goal_list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Firebase error");
            }
        });
    }

    public static ArrayList<Obstruct> getObstructList() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        User.loadObstructList();
        return obstructList;
    }

    public static void setObstructList(ArrayList<Obstruct> obstructlist) {
        //Todo : firebase에 동일하게 저장
        obstructList = obstructlist;
    }

    public static void addObstructList(String str) {
        DatabaseReference data;
        boolean isReduplication = false;
        ArrayList<Obstruct> obstruct_list = User.getObstructList();

        data = myRef.child(UserID).child("방해요소 리스트");
        for (int i = 0; i < obstruct_list.size(); i++) {
            if (obstruct_list.get(i).getObstruction().equals(str)) {
                isReduplication = true;
                //Todo : firebase에 동일하게 저장
                obstruct_list.get(i).setFrequency(obstruct_list.get(i).getFrequency() + 1);
                data.child(obstruct_list.get(i).getObstruction()).child("빈도수").setValue(obstruct_list.get(i).getFrequency());
                break;
            }
        }
        if (!isReduplication){
            //Todo : firebase에 동일하게 저장
            //obstructList.add(new Obstruct(str, 1));
            User.getObstructList().add(new Obstruct(str, 1));
            data.child(str).child("빈도수").setValue("1");
        }
    }

    public static void loadObstructList(){
        //firebase
        DatabaseReference data;
        ValueEventListener dataListener;

        ArrayList<Obstruct> obstruct_list = new ArrayList<Obstruct>();

        //firebase - 방해요소
        data = myRef.child(UserID).child("방해요소 리스트");
        dataListener = data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                } else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue() != null) {
                            String obstruction = ds.getKey();
                            String frequency = ds.child("빈도수").getValue().toString();

                            Obstruct obstruct = new Obstruct(obstruction, Integer.parseInt(frequency));

                            System.out.println("waaaa! " + obstruct.getObstruction());
                            obstruct_list.add(obstruct);
                        }
                    }
                    System.out.println("yaaaaaaaaaaa! " + obstruct_list);
                    User.setObstructList(obstruct_list);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Firebase error");
            }
        });
    }

    public static ScheduleList getScheduleList() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        User.loadScheduleList();
        return scheduleList;
    }

    public static void setScheduleList(ScheduleList schedulelist) {
        //Todo : firebase에 동일하게 저장
        scheduleList = schedulelist;
    }

    public static void addScheduleList(Schedule new_schedule){//추가된 후 schedule 객체 넘겨받음
        DatabaseReference data;
        ScheduleList sl = User.getScheduleList();
        Boolean start = false;
//
        ArrayList<String> labels = new_schedule.getLabel();
        String label = new String();
        Iterator it = labels.iterator();
        while(it.hasNext()){
            String l = (String)it.next();
            if(!start)
                label=l;
            else
                label.concat("/"+l);
            start = true;
        }

        data = myRef.child(UserID).child("일정리스트");
        data.child(new_schedule.getDate()).child("일정").setValue(label);
    }

    public static void loadScheduleList(){
        //firebase
        DatabaseReference data;
        ValueEventListener dataListener;
        ScheduleList schedule_list = new ScheduleList();

        //firebase - 캘린더 일정 로드
        data = myRef.child(UserID).child("일정리스트");
        dataListener = data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                } else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue() != null) {
                            String date = ds.getKey();
                            ArrayList<String> label = new ArrayList<>();
                            String temp = ds.child("일정").getValue().toString(); //{"일정1/일정2/일정3"}
                            String[] temps = temp.split("/");
                            int i;
                            for(i=0;i<temps.length;i++){
                                label.add(temps[i]);
                            }

                            Schedule new_schedule = new Schedule(date, label);
                            System.out.println("waaaa! " + new_schedule.getDate());
                            //User.getGoalList().add(goal_1);
                            schedule_list.add(new_schedule);
                        }
                    }
                    System.out.println("yaaaaaaaaaaa! " + schedule_list);
                    User.setScheduleList(schedule_list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Firebase error");
            }
        });
    }

}