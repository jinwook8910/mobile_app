package com.example.harujogak;

//목표 설정 부분 수정
//        목표 리스트를 보여주는 화면을 먼저 만들고 거기서 다시 추가, 수정, 삭제 가능하도록
//        보여주는 것은 목표 이름, D-Day만

public class Goal {
    String goal_name;
    int Deadline;

    public void setDeadline(int deadline) {
        Deadline = deadline;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public int getDeadline() {
        return Deadline;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public String computeGoalStatic() {
        String result = "결과 값 문자열";

        return result;
    }
}
