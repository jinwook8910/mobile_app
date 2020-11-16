package com.example.harujogak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.main_btn1);
        btn2 = (Button) findViewById(R.id.main_btn2);
        btn3 = (Button) findViewById(R.id.main_btn3);
        btn4 = (Button) findViewById(R.id.main_btn4);
        btn5 = (Button) findViewById(R.id.main_btn5);

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
    }

    class Listener implements View.OnClickListener{
        public void onClick(View view){
            if(view==btn1){
                Intent intent = new Intent(MainActivity.this, Goal.class);
                startActivity(intent);
            }
            else if(view==btn2){
                System.out.println("btn2");
            }
            else if(view==btn3){
                Log.i("MainActivity", "onClickButton");
                Intent intent = new Intent(MainActivity.this, TimeTableList.class);
                startActivity(intent);
            }
            else if(view==btn4){
                Intent intent = new Intent(MainActivity.this, Calendar.class);
                startActivity(intent);
            }
            else if(view==btn5){
                System.out.println("btn5");
            }
        }
    }
    Listener listener = new Listener();
}