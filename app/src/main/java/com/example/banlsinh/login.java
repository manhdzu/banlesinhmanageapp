package com.example.banlsinh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class login extends AppCompatActivity {

    private Button loginBtn;
    public EditText edt_usr, edt_pass;
    private ProgressBar progressBar;
    private String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login);
        edt_usr = findViewById(R.id.username);
        edt_pass = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress);

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
            else {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "password";
                        String[] data = new String[2];
                        data[0] = username;
                        data[1] = password;
                        PutData putData = new PutData("https://banlesinhgiaoxutanthanh.000webhostapp.com/user/login.php", "POST", field, data);
                        if(putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if (result.equals("Dang nhap thanh cong")) {
                                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(i);
                                    finish();
                                } else if(result.equals("Ten dang nhap hoac mat khau sai"))
                                    Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                                else if(result.equals("Loi ket noi"))
                                    Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}