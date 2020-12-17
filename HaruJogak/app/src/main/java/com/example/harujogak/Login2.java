package com.example.harujogak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login2 extends AppCompatActivity {
    private Button join;
    private Button login;
    private EditText email_login;
    private EditText pwd_login;
    private static String UserID;
    static String id_split[];
    FirebaseAuth firebaseAuth;
    String email,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        //title bar 제거하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        join = (Button) findViewById(R.id.join_btn);
        login = (Button) findViewById(R.id.login_btn);
        email_login = (EditText) findViewById(R.id.login_email);
        pwd_login = (EditText) findViewById(R.id.login_pwd);

        firebaseAuth = firebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_login.getText().toString().trim();
                pwd = pwd_login.getText().toString().trim();
                email_login.setText("");
                pwd_login.setText("");

                firebaseAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(Login2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //User user = User.getInstance(email, pwd);
                                    User user = new User(email,pwd);
                                    UserID=email;
                                    Intent intent = new Intent(Login2.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Login2.this, "이메일 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login2.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    public static String getUserID(){
        if(UserID==null){
            UserID="비회원";
            return UserID;
        }
        else {
            id_split = UserID.split("@");
            return id_split[0];
        }
    }
}
