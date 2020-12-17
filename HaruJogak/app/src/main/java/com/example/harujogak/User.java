package com.example.harujogak;

import android.util.Log;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        loadWeekTable();
        loadDateTable();
        loadGoalList();
        loadObstructList();
        loadScheduleList();
    }

    public static ArrayList<MyTimeTable> getWeekTable() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        loadWeekTable();
        return weekTable;
    }

    public static void setWeekTable(ArrayList<MyTimeTable> weekTable) {
        User.weekTable = weekTable;
    }

    public static void addWeekTable(int i, MyTimeTable table){
        //Todo : firebase에 동일하게 저장


    }

    public static void loadWeekTable(){
        //firebase
        DatabaseReference data;
        ValueEventListener dataListener;
        ArrayList<MyTimeTable> time_table_list = new ArrayList<>();

        //firebase - 날짜별 일정
        data = myRef.child(UserID).child("요일별 일정리스트");
        dataListener = data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                } else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue() != null) {
                            String date = ds.getKey();
                            String[] pie_size_temp = ds.child("파이크기").getValue().toString().split("<>"); //{"32f<>510f<>12f"} 형태로 저장
                            String[] pie_label_temp = ds.child("파이라벨").getValue().toString().split("<>"); //{"라벨1<>라벨2<>라벨3"} 형태로 저장
                            String count_temp = ds.child("일정갯수").getValue().toString();
                            String[] color_temp = ds.child("배경색").getValue().toString().split("<>"); //{"1321<>510<>12231"} 형태로 저장
                            String[] rating_temp = ds.child("평가").getValue().toString().split("<>");//{"3<>2<>5<>0"}형태로 저장

                            ArrayList<PieEntry> piedatas = new ArrayList<PieEntry>();
                            int i;
                            for(i=0;i<pie_size_temp.length;i++){
                                piedatas.add(new PieEntry(Float.parseFloat(pie_size_temp[i]), pie_label_temp[i]));
                            }
                            PieDataSet dataSet = new PieDataSet(piedatas, "DB_weekTable");
                            PieData piedata = new PieData(dataSet);

                            Integer count = Integer.parseInt(count_temp);

                            ArrayList<Integer> color = new ArrayList<>();
                            for(i=0;i<color_temp.length;i++){
                                color.add(Integer.parseInt(color_temp[i]));
                            }

                            ArrayList<Integer> rating = new ArrayList<>();
                            for(i=0;i<rating_temp.length;i++){
                                rating.add(Integer.parseInt(rating_temp[i]));
                            }

                            MyTimeTable new_time_table = new MyTimeTable();
                            new_time_table.setDate(date);
                            new_time_table.setPieData(piedata);
                            new_time_table.setTasksCount(count);
                            new_time_table.setMyBackground(color);
                            new_time_table.setRating(rating);

                            System.out.println("waaaa! " + new_time_table.getDate());
                            time_table_list.add(new_time_table);
                        }
                    }
                    System.out.println("yaaaaaaaaaaa! " + time_table_list);
                    User.setWeekTable(time_table_list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Firebase error");
            }
        });
    }

    public static ArrayList<MyTimeTable> getDateTable() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        loadDateTable();
        return dateTable;
    }

    public static void setDateTable(ArrayList<MyTimeTable> dateTable) {
        User.dateTable = dateTable;
    }

    public static void addDateTable(MyTimeTable table){
        DatabaseReference data;
        data = myRef.child(UserID).child("날짜별 일정리스트");

        boolean isExist = false;
        for(int i=0; i<dateTable.size(); i++){
            if(table.getDate().equals(dateTable.get(i).getDate())){//있으면 가져와서 setting
                //Todo : firebase에 동일하게 저장
                dateTable.set(i, table);
                isExist = true;
            }
        }
        // 없으면 리스트에 새로 추가함
        if(!isExist){
            User.getDateTable().add(table); //user객체에 저장
            //Todo : firebase에 동일하게 저장
            //파이크기, 라벨
            PieDataSet ds = (PieDataSet) table.getPieData().getDataSet();
            List<PieEntry> pieEntries = ds.getValues();
            String pie_sizes = new String();
            String pie_labels = new String();
            int i;
            Boolean done=false;
            for(i=0;i<pieEntries.size();i++){
                Float pie_size_temp = pieEntries.get(i).getValue();
                if(!done){
                    pie_sizes = pie_size_temp.toString();
                    pie_labels = pieEntries.get(i).getLabel();
                    done = true;
                }
                else {
                    pie_sizes.concat("<>" + pie_size_temp.toString());
                    pie_labels.concat("<>" + pieEntries.get(i).getLabel());
                }
            }
            data.child(table.getDate()).child("파이크기").setValue(pie_sizes);
            data.child(table.getDate()).child("파이라벨").setValue(pie_labels);
            //일정 갯수
            Integer count_temp = new Integer(table.getTasksCount());
            data.child(table.getDate()).child("일정갯수").setValue(count_temp.toString());
            //배경색
            ArrayList<Integer> colors = table.getMyBackground();
            String color = new String();
            int j;
            Boolean done2=false;
            for(j=0;j<colors.size();j++){
                if(!done2){
                    color = colors.get(j).toString();
                    done2=true;
                }
                else{
                    color.concat("<>"+colors.get(j).toString());
                }
            }
            //평가
            ArrayList<Integer> ratings = table.getRating();
            String rating = new String();
            int k;
            Boolean done3=false;
            for(k=0;k<ratings.size();k++){
                if(!done3){
                    rating=ratings.get(k).toString();
                    done3=true;
                }
                else{
                    rating.concat(ratings.get(k).toString());
                }
            }
        }
    }

    public static void loadDateTable(){
        //firebase
        DatabaseReference data;
        ValueEventListener dataListener;
        ArrayList<MyTimeTable> time_table_list = new ArrayList<>();

        //firebase - 날짜별 일정
        data = myRef.child(UserID).child("날짜별 일정리스트");
        dataListener = data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                } else {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue() != null) {
                            String date = ds.getKey();
                            String[] pie_size_temp = ds.child("파이크기").getValue().toString().split("<>"); //{"32f<>510f<>12f"} 형태로 저장
                            String[] pie_label_temp = ds.child("파이라벨").getValue().toString().split("<>"); //{"라벨1<>라벨2<>라벨3"} 형태로 저장
                            String count_temp = ds.child("일정갯수").getValue().toString();
                            String[] color_temp = ds.child("배경색").getValue().toString().split("<>"); //{"1321<>510<>12231"} 형태로 저장
                            String[] rating_temp = ds.child("평가").getValue().toString().split("<>");//{"3<>2<>5<>0"}형태로 저장

                            ArrayList<PieEntry> piedatas = new ArrayList<PieEntry>();
                            int i;
                            for(i=0;i<pie_size_temp.length;i++){
                                piedatas.add(new PieEntry(Float.parseFloat(pie_size_temp[i]), pie_label_temp[i]));
                            }
                            PieDataSet dataSet = new PieDataSet(piedatas, "DB_dateTable");
                            PieData piedata = new PieData(dataSet);

                            Integer count = Integer.parseInt(count_temp);

                            ArrayList<Integer> color = new ArrayList<>();
                            for(i=0;i<color_temp.length;i++){
                                color.add(Integer.parseInt(color_temp[i]));
                            }

                            ArrayList<Integer> rating = new ArrayList<>();
                            for(i=0;i<rating_temp.length;i++){
                                rating.add(Integer.parseInt(rating_temp[i]));
                            }

                            MyTimeTable new_time_table = new MyTimeTable();
                            new_time_table.setDate(date);
                            new_time_table.setPieData(piedata);
                            new_time_table.setTasksCount(count);
                            new_time_table.setMyBackground(color);
                            new_time_table.setRating(rating);

                            System.out.println("waaaa! " + new_time_table.getDate());
                            time_table_list.add(new_time_table);
                        }
                    }
                    System.out.println("yaaaaaaaaaaa! " + time_table_list);
                    User.setDateTable(time_table_list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Firebase error");
            }
        });
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
                label.concat("<>"+l);
            start = true;
        }

        String[] date = new String[1];
        String[] label_list = new String[1];
        date[0] = new_schedule.getDate();
        label_list[0] = label;
        System.out.println("umm... "+new_schedule.getDate());
        data = myRef.child(UserID).child("일정리스트");
        data.child(date[0]).child("일정").setValue(label_list[0]);
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
                            String temp = ds.child("일정").getValue().toString(); //{"일정1.일정2.일정3"}
                            String[] temps = temp.split("<>");
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