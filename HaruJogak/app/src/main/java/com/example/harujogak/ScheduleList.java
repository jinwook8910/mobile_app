package com.example.harujogak;

import java.util.ArrayList;
import java.util.Iterator;

public class ScheduleList extends ArrayList<Schedule> {

    //해당 날짜 일정 찾기
    public ArrayList<String> search(String date){
        User user = User.getInstance();
        ScheduleList sl = user.getScheduleList();
        Iterator<Schedule> sl_iter = sl.iterator();
        ArrayList<String> result_label = null;

        while(sl_iter.hasNext()){
            Schedule schedule = sl_iter.next();
            if(schedule.date.equals(date)){//해당 날짜 label 찾기
                result_label = schedule.label;
                break;
            }
        }
        return result_label;
    }
}
