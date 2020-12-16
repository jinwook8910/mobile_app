package com.example.harujogak;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class TableByDateEditActivity extends AppCompatActivity {
    private PieChart pieChart;
    private MyTimeTable myTimeTable; //PieData, MyTask(이름, 시작시간, 끝시간), MyBackground, OnWeek, OnDate
    float rotate = 0;
    private Button dateButton;
    private TextView startTimeButton, endTimeButton, edit_startTime, edit_endTime;
    String start_times[], end_times[];
    private int flag_time;

    User users = new User();
    private String fb_date, fb_strt, fb_endt, fb_task, fb_long, UserID;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private DateSetListener dateSetListener = new DateSetListener();
    private TimeSetListener timeSetListener = new TimeSetListener();

    MainActivity main = new MainActivity();
    Login2 user = new Login2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit_date);

        //title bar 제거하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //firebase 정의
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Intent intent = getIntent();
        int position = (int) intent.getIntExtra("byDate", -1);

        //Todo: 사용자 dateTable 리스트에서 position(0~ ) 값에 해당하는 시간표 가져옴.
        // position 값이 -1이면 새로 만드는 것임.
        Log.i("intent", position+"");
        myTimeTable = new MyTimeTable();

        dateButton = (Button) findViewById(R.id.date_set_button);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        Button DONE = (Button) findViewById(R.id.add_timeTable_done);

        pieChart.setUsePercentValues(false);
        pieChart.setRotationEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDrawMarkers(true);

        pieChart.setData(myTimeTable.getPieData());

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                try {
                    int x = pieChart.getData().getDataSetForEntry(e).getEntryIndex((PieEntry) e);
                    onClickDecoTaskButton(pieChart, x);
                }catch (NullPointerException nullPointerException){
                    Toast.makeText(getApplicationContext(), "오류 발생. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });

        DONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.addDateTable(myTimeTable);
            }
        });
    }

    //리스너
    class DateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month += 1;
            dateButton.setText(year + " / " + month + " / " + dayOfMonth);
            fb_date = year + "년 " + month + "월 " + dayOfMonth + "일";
            Description description = new Description();
            description.setText(year + " / " + month + " / " + dayOfMonth);
            description.setTextSize(15);
            pieChart.setDescription(description);
        }
    }

    class TimeSetListener implements TimePickerDialog.OnTimeSetListener {
        int mHour, mMinute;

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            mHour = hour;
            mMinute = minute;
            String strHour = hour + "";
            String strMinute = minute + "";

            if (hour < 0)
                strHour = 24 + hour + "";
            else if (hour < 10)
                strHour = "0" + strHour;
            if (minute < 10)
                strMinute = "0" + strMinute;

            if (flag_time == 1)
                startTimeButton.setText(strHour + " : " + strMinute);
            else if (flag_time == 2)
                endTimeButton.setText(strHour + " : " + strMinute);
            else if (flag_time == 3)
                edit_startTime.setText(strHour + " : " + strMinute);
            else if (flag_time == 4)
                edit_endTime.setText(strHour + " : " + strMinute);
        }

        int getmHour() {
            return mHour;
        }

        int getmMinute() {
            return mMinute;
        }
    }


    //버튼 클릭시 add Task 다이얼로그 띄우는 함수
    public void onClickAddTaskButton(View v) {
        EditText taskLabel;
        Log.i("Custom", "onClickAddTaskButton");
        Dialog addTaskDialog = new Dialog(this);

        addTaskDialog.setContentView(R.layout.add_task_dialog);
        addTaskDialog.setTitle("일정 추가");

        ImageButton exit = (ImageButton) addTaskDialog.findViewById(R.id.exit);
        startTimeButton = (TextView) addTaskDialog.findViewById(R.id.start_time_set_button);
        endTimeButton = (TextView) addTaskDialog.findViewById(R.id.end_time_set_button);
        taskLabel = (EditText) addTaskDialog.findViewById(R.id.task_label_set);
        Button add_task_done = (Button) addTaskDialog.findViewById(R.id.add_task_done);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog.dismiss();
            }
        });

        //Todo : 나중에 골 설정한거를 어레이리스트로 가져와야 함
        //골 리스트 가져옴
        ArrayList<String> goal_list=new ArrayList<>();
        goal_list=MainActivity.getGoal_list_1();
//        ArrayList<Goal> GoalList = MainActivity.getGoal_list();
//        for(int i=0;i<GoalList.size();i++){
//            goal_list.add(GoalList.get(i).getGoal_name());
//        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, goal_list);
        Spinner s = (Spinner) addTaskDialog.findViewById(R.id.goalSpinner);
        s.setAdapter(arrayAdapter); //adapter를 spinner에 연결

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("!!position : " + position + parent.getItemAtPosition(position));
                fb_long = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        add_task_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_task_thread(taskLabel);
                Toast.makeText(getApplicationContext(), "" + taskLabel.getText().toString().trim(), Toast.LENGTH_LONG).show();
                addTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐

                //일정 정보 firebase 추가
                UserID = user.getUserID(); //로그인안한 경우 비회원으로 저장
                myRef.child(UserID).child("날짜별 일정").child(fb_date).child(fb_task).child("시작시간").setValue(fb_strt);
                myRef.child(UserID).child("날짜별 일정").child(fb_date).child(fb_task).child("종료시간").setValue(fb_endt);
                myRef.child(UserID).child("날짜별 일정").child(fb_date).child(fb_task).child("방해요소").setValue(0);
                myRef.child(UserID).child("날짜별 일정").child(fb_date).child(fb_task).child("평가").setValue(0);
                myRef.child(UserID).child("날짜별 일정").child(fb_date).child(fb_task).child("장기목표").setValue(fb_long);
            }
        });

        addTaskDialog.show();
    }

    public void add_task_thread(EditText taskLabel) {
        //시간 문자열 => 분으로 계산
        String strt = (String) startTimeButton.getText();
        String endt = (String) endTimeButton.getText();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                PieEntry yValues_entry;
                int background_entry;

                fb_strt = strt;
                fb_endt = endt;
                fb_task = taskLabel.getText().toString().trim();

                start_times = strt.split(" : ");
                int new_str = (int) (Integer.parseInt(start_times[0]) * 60 + Integer.parseInt(start_times[1])) % 1440;

                end_times = endt.split(" : ");
                int new_end = (int) (Integer.parseInt(end_times[0]) * 60 + Integer.parseInt(end_times[1])) % 1440;

                boolean done = false;
                int entry_str = 0, entry_end = 0;

                //기존의 파이차트 정보와 추가할 일정 정보 합치기
                ArrayList<PieEntry> yValues_new = new ArrayList<PieEntry>();
                PieDataSet dataSet = (PieDataSet) myTimeTable.getPieData().getDataSet();
                PieData data = (PieData) myTimeTable.getPieData();
                Iterator<Integer> backgrounds_entries = myTimeTable.getMyBackground().iterator();
                Iterator<PieEntry> yValues_entries = dataSet.getValues().iterator();
                ArrayList<Integer> table_background_new = new ArrayList<>();

                while (yValues_entries.hasNext()) {
                    yValues_entry = yValues_entries.next();
                    background_entry = backgrounds_entries.next();
                    entry_str = entry_end;
                    entry_end += yValues_entry.getValue();

                    //새로운 일정 추가/폐기 이후의 기존 일정 추가
                    if (done) {
                        yValues_new.add(yValues_entry);
                        table_background_new.add(background_entry);
                        continue;
                    }
                    //0시를 낀 일정 추가
                    int index = dataSet.getEntryCount() - 1;
                    if (new_end < new_str) {//맨 첫번째와 마지막의 항목이 모두 빈칸이어야 하고 크기가 맞아야한다
                        if (dataSet.getValues().get(0).getValue() >= new_end
                                && dataSet.getValues().get(index).getValue() >= (1440 - new_str)) {
                            //빈칸 -> 흰색
                            yValues_new.add(new PieEntry(0, " "));
                            table_background_new.add(Color.rgb(250, 250, 250));
                            //추가된 태스크 -> 조이풀
                            yValues_new.add(new PieEntry(1440 - new_str + new_end, taskLabel.getText().toString()));
                            table_background_new.add(ColorTemplate.JOYFUL_COLORS[myTimeTable.getTasksCount()% 5]);
                            myTimeTable.setTasksCount(myTimeTable.getTasksCount()+1);
                            //빈칸 -> 흰색
                            yValues_new.add(new PieEntry(entry_end - new_end, " "));
                            table_background_new.add(Color.rgb(250, 250, 250));
                            done = true;
                            rotate = (1440 - new_str) / 4;
                            pieChart.setRotationAngle(270 - rotate);
                        } else {//기존 일정에 내용이 있을 경우 -> 새로운 일정을 폐기
                            Log.i("add task :", "type 32 0시낀 일정 겹침");
                            Toast.makeText(getApplicationContext(), "이미 존재하는 일정과 시간이 겹칩니다", Toast.LENGTH_LONG).show();
                            yValues_new = (ArrayList)dataSet.getValues();
                            entry_end += yValues_entry.getValue();
                        }
                    }
                    //새로운 일정과 겹치지 않는 이전의 기존 일정 추가
                    else if (entry_end <= new_str && entry_end <= new_end) {
                        Log.i("add task :", "type 1 이전 일정");
                        yValues_new.add(yValues_entry);
                        table_background_new.add(background_entry);
                    }
                    //새로운 일정이 하나의 기존 일정과 겹칠 때
                    else if (entry_str <= new_str && new_end <= entry_end) {
                        if (yValues_entry.getLabel().equals(" ")) {//기존 일정이 빈칸일 경우 -> 해당 일정을 [빈칸, 새로운 일정, 빈칸] 으로 바꿔 추가
                            Log.i("add task :", "type 2 정상적으로 추가");
                            //빈칸 -> 흰색
                            yValues_new.add(new PieEntry(new_str - entry_str, " "));
                            table_background_new.add(Color.rgb(250, 250, 250));
                            //추가된 태스크 -> 조이풀
                            yValues_new.add(new PieEntry(new_end - new_str, taskLabel.getText().toString()));
                            table_background_new.add(ColorTemplate.JOYFUL_COLORS[myTimeTable.getTasksCount()% 5]);
                            myTimeTable.setTasksCount(myTimeTable.getTasksCount()+1);
                            //빈칸 -> 흰색
                            yValues_new.add(new PieEntry(entry_end - new_end, " "));
                            table_background_new.add(Color.rgb(250, 250, 250));
                            done = true;
                        } else {//기존 일정에 내용이 있을 경우 -> 새로운 일정을 폐기
                            Log.i("add task :", "type 3 일정 완전히 겹침");
                            Toast.makeText(getApplicationContext(), "이미 존재하는 일정과 시간이 겹칩니다", Toast.LENGTH_LONG).show();
                            yValues_new.add(yValues_entry);
                            table_background_new.add(background_entry);
                            entry_end += yValues_entry.getValue();
                        }
                    }
                    //새로운 일정이 여러 일정과 겹칠 때 -> 무조건 새로운 일정 폐기
                    else {
                        Log.i("add task :", "type 4 일정 부분 겹침");
                        Toast.makeText(getApplicationContext(), "이미 존재하는 일정과 시간이 겹칩니다", Toast.LENGTH_LONG).show();
                        yValues_new.add(yValues_entry);
                        table_background_new.add(background_entry);
                        entry_end += yValues_entry.getValue();
                    }
                }

                myTimeTable.setMyBackground(table_background_new);
                dataSet = new PieDataSet(yValues_new, "Tasks");
                dataSet.setSliceSpace(0.5f);
                dataSet.setSelectionShift(0f);
                dataSet.setColors(myTimeTable.getMyBackground());

                data.setDataSet(dataSet);
                myTimeTable.setPieData(data);
                data.setValueTextSize(0f);

                pieChart.notifyDataSetChanged();
                pieChart.setData(myTimeTable.getPieData());
                pieChart.invalidate();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    //버튼 클릭시 decorate task 다이얼로그 띄우는 함수
    public void onClickDecoTaskButton(PieChart pieChart, int index) {
        Log.i("Custom", "onClickDecoTaskButton");

        Dialog decoTaskDialog = new Dialog(this);
        decoTaskDialog.setContentView(R.layout.decorate_dialog);
        decoTaskDialog.setTitle("일정 추가");

        ImageButton exit = (ImageButton) decoTaskDialog.findViewById(R.id.exit);
        TextView taskLabelLine = (TextView) decoTaskDialog.findViewById(R.id.task_label_show);
        edit_startTime = (TextView) decoTaskDialog.findViewById(R.id.start_time);
        edit_endTime = (TextView) decoTaskDialog.findViewById(R.id.end_time);
        Button decorate_done = (Button) decoTaskDialog.findViewById(R.id.decorate_done);
        Button template = (Button) decoTaskDialog.findViewById(R.id.show_adapted_task);

        String strHour, strMinute;
        PieDataSet dataSet = (PieDataSet) myTimeTable.getPieData().getDataSet();
        taskLabelLine.setText(dataSet.getValues().get(index).getLabel());
        template.setBackgroundColor(myTimeTable.getMyBackground().get(index));

        List<PieEntry> yValues = ((PieDataSet) myTimeTable.getPieData().getDataSet()).getValues();
        int str_time = (int) (yValues.get(0).getValue() - 4 * rotate);
        int end_time, i;
        for (i = 1; i < index; i++) {
            str_time += (int) yValues.get(i).getValue();
        }
        end_time = str_time + (int) yValues.get(i).getValue();

        if (str_time < 0)
            str_time += 1440;

        if ((str_time / 60) < 10)
            strHour = "0" + (str_time / 60);
        else
            strHour = "" + (str_time / 60);
        if ((str_time % 60) < 10)
            strMinute = "0" + (str_time % 60);
        else
            strMinute = "" + (str_time % 60);

        edit_startTime.setText(strHour + " : " + strMinute);

        if (end_time < 0)
            end_time += 1440;

        if ((end_time / 60) < 10)
            strHour = "0" + (end_time / 60);
        else
            strHour = "" + (end_time / 60);
        if ((end_time % 60) < 10)
            strMinute = "0" + (end_time % 60);
        else
            strMinute = "" + (end_time % 60);

        edit_endTime.setText(strHour + " : " + strMinute);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decoTaskDialog.dismiss();
            }
        });

        int finalStr_time = str_time;
        int finalEnd_time = end_time;
        decorate_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "decorating done", Toast.LENGTH_SHORT).show();
                List<PieEntry> yValues = ((PieDataSet) myTimeTable.getPieData().getDataSet()).getValues();
                int prev_y = (int) yValues.get(index - 1).getY();
                int y = (int) yValues.get(index).getY();
                int next_y = (int) yValues.get(index + 1).getY();
                Log.i("onClickDecoTaskButton", prev_y + "/" + y + "/" + next_y);

                String str[];
                str = edit_startTime.getText().toString().split(" : ");
                int new_str = (int) (Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1])) % 1440;
                if (finalStr_time != new_str) {
                    yValues.get(index - 1).setY(prev_y + (new_str - finalStr_time));
                    y -= (new_str - finalStr_time);
                }

                str = edit_endTime.getText().toString().split(" : ");
                int new_end = (int) (Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1])) % 1440;
                if (finalEnd_time != new_end) {
                    yValues.get(index + 1).setY(next_y + (finalEnd_time - new_end));
                    y -= (finalEnd_time - new_end);
                }

                yValues.get(index).setY(y);
                Log.i("onClickDecoTaskButton", yValues.get(index - 1).getValue() + "/"
                        + yValues.get(index).getValue() + "/" + yValues.get(index + 1).getValue());

                PieDataSet pieDataSet = new PieDataSet(yValues, "Tasks");
                pieDataSet.setSliceSpace(0.5f);
                pieDataSet.setSelectionShift(0f);
                pieDataSet.setColors(myTimeTable.getMyBackground());

                PieData pieData = new PieData(pieDataSet);
                pieData.setDataSet(pieDataSet);
                pieData.setValueTextSize(0f);
                myTimeTable.setPieData(pieData);

                pieChart.notifyDataSetChanged();
                pieChart.setData(myTimeTable.getPieData());
                pieChart.invalidate();
                decoTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
            }
        });

        template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "pieColorButton done", Toast.LENGTH_SHORT).show();
                showColorPicker(template, index);
            }
        });

        decoTaskDialog.show();
    }

    //날짜, 시간 다이얼로그 띄우는 함수
    public void onClickSet(View view) {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        String mTime, times[];

        if (view == dateButton) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, mYear, mMonth, mDay);
            datePickerDialog.show();
            Log.i("date button", mYear + "/" + mMonth + "/" + mDay);
        } else if (view == startTimeButton) {
            flag_time = 1;
            mTime = (String) startTimeButton.getText();
            times = mTime.split(" : ");
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    timeSetListener, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true);
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();

        } else if (view == endTimeButton) {
            flag_time = 2;
            mTime = (String) endTimeButton.getText();
            times = mTime.split(" : ");
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    timeSetListener, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true);
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();
        } else if (view == edit_startTime) {
            flag_time = 3;
            mTime = (String) edit_startTime.getText();
            times = mTime.split(" : ");
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    timeSetListener, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true);
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();
        } else if (view == edit_endTime) {
            flag_time = 4;
            mTime = (String) edit_endTime.getText();
            times = mTime.split(" : ");
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    timeSetListener, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true);
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();
        }
    }

    public void showColorPicker(View view, int index) {
        final ColorPicker colorPicker = new ColorPicker(TableByDateEditActivity.this);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            Button showTemplate = (Button) view.findViewById(R.id.show_adapted_task);

            @Override
            public void onChooseColor(int position, int color) {
                showTemplate.setBackgroundColor(color);
                myTimeTable.getMyBackground().set(index, color);
            }

            @Override
            public void onCancel() {
                colorPicker.dismissDialog();
            }
        })
                .setRoundColorButton(true)
                .setDefaultColorButton(Color.parseColor("#f84c44"))
                .setColumns(5)
                .show();
    }

}