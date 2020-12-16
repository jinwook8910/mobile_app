package com.example.harujogak;

import java.util.ArrayList;

public class User {
    private static volatile User instance = null;
    private String id, passWord, eMail;
    private ArrayList<MyTimeTable> weekTable;   //주간 시간표 저장하는 리스트
    private ArrayList<MyTimeTable> dateTable;   //일일 시간표 저장하는 리스트
    private static ArrayList<Goal> goalList;  //목표 저장하는 리스트
    private ArrayList<Obstruct> obstructList;   //방해요소 저장하는 리스트
    private ScheduleList scheduleList;  //캘린더에 일정 저장하는 리스트

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

    public ArrayList<Goal> getGoalList() {
        //Todo : firebase에서 받아온 데이터 class에 담아서 return
        return goalList;
    }

    public void setGoalList(ArrayList<Goal> goalList) {
        //Todo : firebase에 동일하게 저장
        this.goalList = goalList;
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