package com.example.banlsinh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private Button loginBtn;
    public EditText edt_usr, edt_pass;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login);
        edt_usr = findViewById(R.id.username);
        edt_pass = findViewById(R.id.password);

        username = edt_usr.getText().toString();
        password = edt_pass.getText().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loginBtn.setOnClickListener(view -> {
            username = edt_usr.getText().toString();
            password = edt_pass.getText().toString();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);

            if(username.isEmpty() || password.isEmpty())
                Toast.makeText(getApplicationContext(), "Yêu cầu TÊN ĐĂNG NHẬP và MẬT KHẨU", Toast.LENGTH_SHORT).show();
            else startActivity(i);
        });
    }
}