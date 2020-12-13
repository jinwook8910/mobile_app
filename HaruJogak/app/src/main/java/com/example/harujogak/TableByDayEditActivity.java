package com.example.harujogak;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import petrov.kristiyan.colorpicker.ColorPicker;

//import com.jaredrummler.android.colorpicker.ColorPanelView;
//import com.jaredrummler.android.colorpicker.ColorPickerView;

public class TableByDayEditActivity extends AppCompatActivity {
    private PieChart pieChart;
    private MyTimeTable myTimeTable; //PieData, MyTask(이름, 시작시간, 끝시간), MyBackground, OnWeek, OnDate
    private PieDataSet dataSet;
    private PieData data;
    private int newTasks = 0;
    private Button dateButton;
    private TextView startTimeButton, endTimeButton;
    private int flag_time, flag_template;

    // yValues -> PieDataSet -> PieData
    private ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
    int[] week = {0, 0, 0, 0, 0, 0, 0};

    private DateSetListener dateSetListener = new DateSetListener();
    private TimeSetListener timeSetListener = new TimeSetListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit_day);

        Intent intent = getIntent();
        int position = (int)intent.getIntExtra("byDate", -1);
        //Todo: 사용자 시간표 어레이리스트에서 position 값에 해당하는 시간표 가져옴.
        // position 값이 -1이면 새로 만드는 것임.
        myTimeTable = new MyTimeTable();

        dateButton = (Button) findViewById(R.id.date_set_button);
        pieChart = (PieChart) findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(false);
        pieChart.setRotationEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDrawMarkers(true);

        //24시간 = 1440분 //TimePicker로 시간을 분으로 받으니까 파이차트를 분단위로 계산
        yValues.add(new PieEntry(1440f, " "));//일정 없는 것

        dataSet = new PieDataSet(yValues, "Tasks");
        dataSet.setSliceSpace(0.5f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(myTimeTable.getMyBackground());

        data = new PieData((dataSet));
        data.setValueTextSize(0f);

        myTimeTable.setPieData(data);
        pieChart.setData(data);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int x = pieChart.getData().getDataSetForEntry(e).getEntryIndex((PieEntry) e);
                onClickDecoTaskButton(pieChart, x);
            }

            @Override
            public void onNothingSelected() {
            }
        });

    }

//////////////////
    public void setExample(MyTimeTable exT){
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(60f, "잠"));
        yValues.add(new PieEntry(10f, "아침식사"));
        yValues.add(new PieEntry(35f, "공부"));
        yValues.add(new PieEntry(20f, "휴식"));
        yValues.add(new PieEntry(10f, "점심식사"));
        yValues.add(new PieEntry(35f, "운동"));
        yValues.add(new PieEntry(20f, "휴식"));
        yValues.add(new PieEntry(10f, "저녁식사"));

        PieDataSet dataSet = new PieDataSet(yValues, "Tasks");
        dataSet.setSliceSpace(0.5f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(0f);

        exT.setPieData(data);
        exT.setDate("2020-12-12");
    }

    //리스너
    class DateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month+=1;
            dateButton.setText(year + " / " + month + " / " + dayOfMonth);
            myTimeTable.setDate("2020-12-12");
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

            if (hour < 10)
                strHour = "0" + strHour;
            if (minute < 10)
                strMinute = "0" + strMinute;

            if (flag_time == 1)
                startTimeButton.setText(strHour + " : " + strMinute);
            else if (flag_time == 2)
                endTimeButton.setText(strHour + " : " + strMinute);
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
        //ArrayList<String> GoalList = user.getGoalList(); ????????
        ArrayList<String> GoalList = new ArrayList<>();
        GoalList.add("토익 시험");
        GoalList.add("다이어트");
        GoalList.add("코딩테스트");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, GoalList);
        Spinner s = (Spinner) addTaskDialog.findViewById(R.id.goalSpinner);
        s.setAdapter(arrayAdapter); //adapter를 spinner에 연결

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                System.out.println("!!position : " + position +
                        parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Todo :
        // 중복되는거 계속 이상하게 됨..

        add_task_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PieEntry yValues_entry;
                int background_entry;
                int order = 0;

                //시간 문자열 => 분으로 계산
                String strt = (String) startTimeButton.getText();
                String endt = (String) endTimeButton.getText();

                String start_times[] = strt.split(" : ");
                int start_time = Integer.parseInt(start_times[0]) * 60 + Integer.parseInt(start_times[1]);

                String end_times[] = endt.split(" : ");
                int end_time = Integer.parseInt(end_times[0]) * 60 + Integer.parseInt(end_times[1]);

                //기존의 파이차트 정보와 추가할 일정 정보 합치기
                boolean done = false;
                ArrayList<PieEntry> yValues_new = new ArrayList<PieEntry>();
                Iterator<PieEntry> yValues_entries = yValues.iterator();
                ArrayList<Integer> table_background_new = new ArrayList<>();
                Iterator<Integer> backgrounds_entries = myTimeTable.getMyBackground().iterator();

                int total_time = 0;

                while (yValues_entries.hasNext()) {
                    yValues_entry = yValues_entries.next();
                    background_entry = backgrounds_entries.next();
                    total_time += yValues_entry.getValue();

                    if (!done && total_time >= start_time && total_time >= end_time) {//선택한 시간
                        if (yValues_entry.getLabel().equals(" ")) {//선택한 시간에 일정이 없는 경우
                            total_time -= yValues_entry.getValue();

                            //빈칸 -> 흰색
                            yValues_new.add(new PieEntry(start_time - total_time, " "));
                            table_background_new.add(Color.rgb(250, 250, 250));
                            //추가된 태스크 -> 조이풀
                            yValues_new.add(new PieEntry(end_time - start_time, taskLabel.getText().toString()));
                            table_background_new.add(ColorTemplate.JOYFUL_COLORS[newTasks%5]);
                            newTasks++;
                            //빈칸 -> 흰색
                            yValues_new.add(new PieEntry(yValues_entry.getValue() - (end_time - total_time), " "));
                            table_background_new.add(Color.rgb(250, 250, 250));

                            total_time += yValues_entry.getValue();
                            done = true; //추가 완료함을 표시
                        } else {//선택한 시간에 일정이 있는 경우
                            Toast.makeText(getApplicationContext(), "schedule already exists", Toast.LENGTH_LONG).show();
                            total_time += yValues_entry.getValue();
                        }
                    } else {//나머지 시간
                        yValues_new.add(yValues_entry);
                        table_background_new.add(background_entry);
                    }
                    order++;
                }
                yValues = yValues_new;
                myTimeTable.setMyBackground(table_background_new);
                PieDataSet dataSet = new PieDataSet(yValues_new, "Tasks");

                dataSet.setSliceSpace(0.5f);
                dataSet.setSelectionShift(0f);
                dataSet.setColors(table_background_new);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(0f);

                pieChart.notifyDataSetChanged();
                pieChart.setData(data);
                pieChart.invalidate();
                myTimeTable.setPieData(data);

//                for(int i=0;i<7;i++){
//                    if(week[i]==1)
//                      user.setWeekTable(i, myTimeTable);
//                }

                Toast.makeText(getApplicationContext(), "" + taskLabel.getText().toString().trim(), Toast.LENGTH_LONG).show();
                addTaskDialog.dismiss();
            }
        });

        addTaskDialog.show();
    }

    //버튼 클릭시 decorate task 다이얼로그 띄우는 함수
    public void onClickDecoTaskButton(PieChart pieChart, int index) {
        Log.i("Custom", "onClickDecoTaskButton");

        Dialog decoTaskDialog = new Dialog(this);
        decoTaskDialog.setContentView(R.layout.decorate_dialog);
        decoTaskDialog.setTitle("일정 추가");

        TextView taskLabelLine = (TextView) decoTaskDialog.findViewById(R.id.task_label_show);
        TextView startTime = (TextView) decoTaskDialog.findViewById(R.id.start_time);
        TextView endTime = (TextView) decoTaskDialog.findViewById(R.id.end_time);
        Button decorate_done = (Button) decoTaskDialog.findViewById(R.id.decorate_done);
        Button showTemplate = (Button) decoTaskDialog.findViewById(R.id.show_adapted_task);
        TextView backgroundColorButton = (TextView) decoTaskDialog.findViewById(R.id.set_background);
        TextView textColorButton = (TextView) decoTaskDialog.findViewById(R.id.set_text_color);

        taskLabelLine.setText(yValues.get(index).getLabel());

        decorate_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "decorating done", Toast.LENGTH_SHORT).show();
                pieChart.setData(myTimeTable.getPieData());
                decoTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
            }
        });

        backgroundColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "pieColorButton done", Toast.LENGTH_SHORT).show();
                flag_template = 1;
                showColorPicker(showTemplate, index);
            }
        });
        textColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "textColorButton done", Toast.LENGTH_SHORT).show();
                flag_template = 2;
                showColorPicker(showTemplate, index);
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
                    timeSetListener, Integer.parseInt(times[0]),Integer.parseInt(times[1]), true);
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();

        } else if (view == endTimeButton) {
            flag_time = 2;
            mTime = (String) endTimeButton.getText();
            times = mTime.split(" : ");
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    timeSetListener, Integer.parseInt(times[0]),Integer.parseInt(times[1]), true);
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();
        }
    }

    public void checkDay(View view) {
        int i=0;
        switch (view.getId()) {
            case R.id.mon_button:
                i=0;                break;
            case R.id.tue_button:
                i=1;                break;
            case R.id.wed_button:
                i=2;                break;
            case R.id.thr_button:
                i=3;                break;
            case R.id.fri_button:
                i=4;                break;
            case R.id.sat_button:
                i=5;                break;
            case R.id.sun_button:
                i=6;                break;
        }
        if (week[i] == 0) {
            week[i] = 1;
            Log.i("Checkbox i", "checked");
            view.setSelected(true);
        } else {
            week[i] = 0;
            Log.i("Checkbox i", "unchecked");
            view.setSelected(false);
        }
//        User user = new User();
//        user.getWeekTable().set(i, nowMyTimeTable);
    }

    public void showColorPicker(View view, int index) {
//        ColorPickerView colorPickerView;
//        ColorPanelView newColorPanelView;

//        Dialog colorPickerDialog = new Dialog(this);
//        colorPickerDialog.setContentView(R.layout.color_picker_dialog);
//        Button colorPickDoneButton = (Button) findViewById(R.id.okButton);
//
//        colorPickDoneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "decorating done", Toast.LENGTH_LONG).show();
//                colorPickerDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
//            }
//        });
//
//        colorPickerDialog.show();

        final ColorPicker colorPicker = new ColorPicker(TableByDayEditActivity.this);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            Button showTemplate = (Button) view.findViewById(R.id.show_adapted_task);

            @Override
            public void onChooseColor(int position, int color) {
                if (flag_template == 1) {
                    showTemplate.setBackgroundColor(color);
                    // TODO : 백그라운드 색상, 텍스트 색상 변경하도록..
                    myTimeTable.getMyBackground().set(index, color);

                } else if (flag_template == 2) {
                    showTemplate.setTextColor(color);
                }
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