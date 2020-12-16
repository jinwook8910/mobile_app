package com.example.harujogak;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        weekTable.add(new MyTimeTable("월"));
        weekTable.add(new MyTimeTable("화"));
        weekTable.add(new MyTimeTable("수"));
        weekTable.add(new MyTimeTable("목"));
        weekTable.add(new MyTimeTable("금"));
        weekTable.add(new MyTimeTable("토"));
        weekTable.add(new MyTimeTable("일"));

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
        weekTable.add(new MyTimeTable("월"));
        weekTable.add(new MyTimeTable("화"));
        weekTable.add(new MyTimeTable("수"));
        weekTable.add(new MyTimeTable("목"));
        weekTable.add(new MyTimeTable("금"));
        weekTable.add(new MyTimeTable("토"));
        weekTable.add(new MyTimeTable("일"));
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

    public static ArrayList<Goal> getGoalList() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        User.loadGoalList();
        return goalList;
    }

    public static void setGoalList(ArrayList<Goal> goalList_r) {
        //Todo : firebase에 동일하게 저장
        goalList = goalList_r;
    }

    public static void loadGoalList() {
        //firebase
        DatabaseReference data;
        ValueEventListener dataListener;
        //private static ArrayList<String> goal_list = new ArrayList<>();
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

    public ArrayList<Obstruct> getObstructList() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        return obstructList;
    }

    public void setObstructList(ArrayList<Obstruct> obstructList) {
        //Todo : firebase에 동일하게 저장
        this.obstructList = obstructList;
    }

    public void addObstructList(String str) {
        boolean isReduplication = false;
        for (int i = 0; i < obstructList.size(); i++) {
            if (obstructList.get(i).getObstruction().equals(str)) {
                isReduplication = true;
                //Todo : firebase에 동일하게 저장
                obstructList.get(i).setFrequency(obstructList.get(i).getFrequency() + 1);
                break;
            }
        }
        if (!isReduplication){
            //Todo : firebase에 동일하게 저장
            obstructList.add(new Obstruct(str, 1));
        }
    }

    public ScheduleList getScheduleList() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        return scheduleList;
    }

    public void setScheduleList(ScheduleList scheduleList) {
        //Todo : firebase에 동일하게 저장
        this.scheduleList = scheduleList;
    }

}