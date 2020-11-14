package com.example.harujogak;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TimeTableEdit extends AppCompatActivity {
    Dialog addTaskDialog, decoTaskDialog;
    EditText taskLabel;
    Button datebutton, timebutton;

    DateSetListener dateSetListener = new DateSetListener();
    TimeSetListener timeSetListener = new TimeSetListener();

    class DateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            datebutton.setText(year + " / " + month + " / " + dayOfMonth);
        }
    }

    class TimeSetListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timebutton.setText(hourOfDay + " : " + minute);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit);

        datebutton = (Button) findViewById(R.id.date_set_button);
    }

    public void onClickAddTaskButton(View v) {
        Log.i("Custom", "onClickButton");
        addTaskDialog = new Dialog(this);
        addTaskDialog.setContentView(R.layout.add_task_dialog);
        addTaskDialog.setTitle("일정 추가");

        Button add_task_done = (Button) addTaskDialog.findViewById(R.id.add_task_done);
        taskLabel = (EditText) addTaskDialog.findViewById(R.id.task_label_set);
        timebutton = (Button) addTaskDialog.findViewById(R.id.time_set_button);

        add_task_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "" + taskLabel.getText().toString().trim(),
                        Toast.LENGTH_LONG).show();
                addTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
            }
        });

        addTaskDialog.show();
    }

    public void onClickDecoTaskButton(View v) {
        Log.i("Custom", "onClickButton");
        decoTaskDialog = new Dialog(this);
        decoTaskDialog.setContentView(R.layout.decorate_dialog);
        decoTaskDialog.setTitle("일정 추가");

        Button decorate_done = (Button) decoTaskDialog.findViewById(R.id.decorate_done);
        taskLabel = (EditText) decoTaskDialog.findViewById(R.id.task_label_set);

        decorate_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "decorating done", Toast.LENGTH_LONG).show();
                decoTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
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

        if (view == datebutton) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == timebutton) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, mHour, mMinute, true);
            timePickerDialog.show();
        }
    }
}
