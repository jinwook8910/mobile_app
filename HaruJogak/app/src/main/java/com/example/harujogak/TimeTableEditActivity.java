package com.example.harujogak;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jaredrummler.android.colorpicker.ColorPanelView;
import com.jaredrummler.android.colorpicker.ColorPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import petrov.kristiyan.colorpicker.ColorPicker;

//import com.jaredrummler.android.colorpicker.ColorPanelView;
//import com.jaredrummler.android.colorpicker.ColorPickerView;

public class TimeTableEditActivity extends AppCompatActivity {
    private EditText taskLabel;
    private Button dateButton;
    private TextView startTimeButton, endTimeButton;
    private ColorPickerView colorPickerView;
    private ColorPanelView newColorPanelView;
    private int flag_time, flag_template;
    ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

    DateSetListener dateSetListener = new DateSetListener();
    TimeSetListener timeSetListener = new TimeSetListener();

    //리스너
    class DateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateButton.setText(year + " / " + month + " / " + dayOfMonth);
        }
    }

    class TimeSetListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (flag_time == 1)
                startTimeButton.setText(hourOfDay + " : " + minute);
            if (flag_time == 2)
                endTimeButton.setText(hourOfDay + " : " + minute);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit);

        dateButton = (Button) findViewById(R.id.date_set_button);

        /*
         * 파이차트 동작
         * 1. 파이차트 객체 생성
         * 2. 파이차트에 들어갈 태스크리스트 == PieEntry arraylist 생성
         * 3. PieEntry arraylist에 PieEntry를 여러개 만들어서 넣음 => add
         * 4. 해당 어래이리스트를 라벨링함 => PieDataSet
         * 5. 그 PieData를 파이차트 객체에 넣어줌 => setData
         * */

        PieChart pieChart = findViewById(R.id.pieChart);
//        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

//        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setRotationEnabled(false);

        //전역으로 사용
        //ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        //24시간 = 1440분 //TimePicker로 시간을 분으로 받으니까 파이차트를 분단위로 계산
        yValues.add(new PieEntry(200f, " "));//일정 없는 것
        yValues.add(new PieEntry(150f, "USA"));
        yValues.add(new PieEntry(150f, "UK"));
        yValues.add(new PieEntry(300f, "India"));
        yValues.add(new PieEntry(400f, "Russia"));
        yValues.add(new PieEntry(240f, "Korea"));

        Description description = new Description();
        description.setText("세계 국가"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(1f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
    }

    //버튼 클릭시 add Task 다이얼로그 띄우는 함수
    public void onClickAddTaskButton(View v) {
        Log.i("Custom", "onClickAddTaskButton");
        Dialog addTaskDialog = new Dialog(this);
        addTaskDialog.setContentView(R.layout.add_task_dialog);
        addTaskDialog.setTitle("일정 추가");

        Button add_task_done = (Button) addTaskDialog.findViewById(R.id.add_task_done);
        startTimeButton = (TextView) addTaskDialog.findViewById(R.id.start_time_set_button);
        endTimeButton = (TextView) addTaskDialog.findViewById(R.id.end_time_set_button);
        taskLabel = (EditText) addTaskDialog.findViewById(R.id.task_label_set);

        add_task_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파이차트 객체 생성
                PieChart pieChart = findViewById(R.id.pieChart);

                pieChart.getDescription().setEnabled(false);
                pieChart.setExtraOffsets(5, 10, 5, 5);

                pieChart.setDrawHoleEnabled(false);
                pieChart.setRotationEnabled(false);

                //시간 문자열 => 분으로 계산
                String startTime = (String)startTimeButton.getText();
                String endTime = (String)endTimeButton.getText();

                String start_times[] = startTime.split(" : ");
                int start_time = Integer.parseInt(start_times[0])*60 + Integer.parseInt(start_times[1]);
                String end_times[] = endTime.split(" : ");
                int end_time = Integer.parseInt(end_times[0])*60 + Integer.parseInt(end_times[1]);
                System.out.println("************************\n"+start_time);
                System.out.println(end_time);


                //기존의 파이차트 정보와 추가할 일정 정보 합치기
                boolean done = false;
                ArrayList<PieEntry> yValues_new = new ArrayList<PieEntry>();
                Iterator<PieEntry> yValues_entries = yValues.iterator();
                int total_time=0;
                while(yValues_entries.hasNext()){
                    PieEntry yValues_entry = yValues_entries.next();
                    total_time += yValues_entry.getValue();
                    System.out.println("**************\n"+total_time);
                    if(!done && total_time >= start_time && total_time >= end_time){//선택한 시간
                        if(yValues_entry.getLabel().equals(" ")){//선택한 시간에 일정이 없는 경우
                            total_time -= yValues_entry.getValue();
                            yValues_new.add(new PieEntry(start_time-total_time, " "));
                            yValues_new.add(new PieEntry(end_time-start_time, taskLabel.getText().toString()));
                            yValues_new.add(new PieEntry(yValues_entry.getValue()-(end_time-total_time), " "));
                            total_time += yValues_entry.getValue();
                            done = true; //추가 완료함을 표시
                        }
                        else{//선택한 시간에 일정이 있는 경우
                            Toast.makeText(getApplicationContext(), "schedule already exists", Toast.LENGTH_LONG).show();
                            total_time += yValues_entry.getValue();
                        }
                    }
                    else{//나머지 시간
                        yValues_new.add(yValues_entry);
                    }
                }
                yValues= yValues_new;

                Description description = new Description();
                description.setText("계획표"); //라벨
                description.setTextSize(15);
                pieChart.setDescription(description);

                PieDataSet dataSet = new PieDataSet(yValues_new, "Countries");
                dataSet.setSliceSpace(2f);
                dataSet.setSelectionShift(1f);
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.YELLOW);

                pieChart.setData(data);

                Toast.makeText(getApplicationContext(), "" + taskLabel.getText().toString().trim(), Toast.LENGTH_LONG).show();
                addTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
            }
        });

        addTaskDialog.show();
    }

    //버튼 클릭시 decorate task 다이얼로그 띄우는 함수
    public void onClickDecoTaskButton(View v) {
        Log.i("Custom", "onClickDecoTaskButton");

        Dialog decoTaskDialog = new Dialog(this);
        decoTaskDialog.setContentView(R.layout.decorate_dialog);
        decoTaskDialog.setTitle("일정 추가");

        TextView taskLabelLine = (TextView) decoTaskDialog.findViewById(R.id.task_label_show);
        TextView taskTimeLine = (TextView) decoTaskDialog.findViewById(R.id.task_time_show);
        Button decorate_done = (Button) decoTaskDialog.findViewById(R.id.decorate_done);
        Button showTemplate = (Button) decoTaskDialog.findViewById(R.id.show_adapted_task);
        TextView backgroundColorButton = (TextView) decoTaskDialog.findViewById(R.id.set_background);
        TextView textColorButton = (TextView) decoTaskDialog.findViewById(R.id.set_text_color);

        decorate_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "decorating done", Toast.LENGTH_LONG).show();
                decoTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
            }
        });

        backgroundColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "pieColorButton done", Toast.LENGTH_LONG).show();
                flag_template = 1;
                showColorPicker(showTemplate);
            }
        });
        textColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "textColorButton done", Toast.LENGTH_LONG).show();
                flag_template = 2;
                showColorPicker(showTemplate);
            }
        });

        decoTaskDialog.show();
    }

    public void onClickSet(View view) {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        int mHour = cal.get(Calendar.HOUR_OF_DAY);
        int mMinute = cal.get(Calendar.MINUTE);

        if (view == dateButton) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (view == startTimeButton) {
            flag_time = 1;
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, timeSetListener, mHour, mMinute, false);
            timePickerDialog.show();
        } else if (view == endTimeButton) {
            flag_time = 2;
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, timeSetListener, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void showColorPicker(View view) {
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
        final ColorPicker colorPicker = new ColorPicker(TimeTableEditActivity.this);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            Button showTemplate = (Button) view.findViewById(R.id.show_adapted_task);

            @Override
            public void onChooseColor(int position, int color) {
                if (flag_template == 1)
                    showTemplate.setBackgroundColor(color);
                else if (flag_template == 2)
                    showTemplate.setTextColor(color);
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