package com.example.harujogak;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText email_join;
    private EditText pwd_join;
    private Button btn;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_signup);

        email_join=findViewById(R.id.input_email);
        pwd_join=findViewById(R.id.input_pwd);
        btn=findViewById(R.id.okButton);

        firebaseAuth=FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=email_join.getText().toString().trim();
                String pwd=pwd_join.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                   }
                });

    }
}
