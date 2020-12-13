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

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//import com.jaredrummler.android.colorpicker.ColorPanelView;
//import com.jaredrummler.android.colorpicker.ColorPickerView;

public class TableByDateEditActivity extends AppCompatActivity {
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

    private DateSetListener dateSetListener = new DateSetListener();
    private TimeSetListener timeSetListener = new TimeSetListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit_date);

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

    //노티피케이션(푸시알림)
    void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {

            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }

    //리스너
    class DateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month+=1;
            dateButton.setText(year + " / " + month + " / " + dayOfMonth);

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

        add_task_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_task_thread(taskLabel);
                Toast.makeText(getApplicationContext(), "" + taskLabel.getText().toString().trim(), Toast.LENGTH_LONG).show();
                addTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
            }
        });


//알림 부분
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 14);
        calendar.set(Calendar.SECOND, 0);

        // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Date currentDateTime = calendar.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
        Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

        //  Preference에 설정한 값 저장
        SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
        editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
        editor.apply();

        diaryNotification(calendar);
        //알림부분 끝

        addTaskDialog.show();
    }

    public void add_task_thread(EditText taskLabel){
        Runnable task = new Runnable(){
            @Override
            public void run(){
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

        final ColorPicker colorPicker = new ColorPicker(TableByDateEditActivity.this);
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