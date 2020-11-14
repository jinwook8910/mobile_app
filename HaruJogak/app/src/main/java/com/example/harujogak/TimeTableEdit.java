package com.example.harujogak;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TimeTableEdit extends AppCompatActivity {
    Dialog addTaskDialog, decoTaskDialog;
    EditText taskLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit);
    }

    public void onClickAddTaskButton(View v) {
        Log.i("Custom", "onClickButton");
        addTaskDialog = new Dialog(this);
        addTaskDialog.setContentView(R.layout.add_task_dialog);
        addTaskDialog.setTitle("일정 추가");

        Button add_task_done = (Button) addTaskDialog.findViewById(R.id.add_task_done);
        taskLabel = (EditText) addTaskDialog.findViewById(R.id.task_label_set);

        add_task_done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), ""+taskLabel.getText().toString().trim(),
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

        decorate_done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "decorating done", Toast.LENGTH_LONG).show();
                decoTaskDialog.dismiss(); // Cancel 버튼을 누르면 다이얼로그가 사라짐
            }
        });

        decoTaskDialog.show();
    }
}
