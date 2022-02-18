package com.example.banlsinh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banlsinh.custom.CustomToast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LogIn extends AppCompatActivity {

    private Button loginBtn;
    public EditText edt_usr, edt_pass;
    private ProgressBar progressBar;
    private String username, password;
    private CheckBox cbRemember;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login);
        edt_usr = findViewById(R.id.username);
        edt_pass = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress);
        cbRemember = findViewById(R.id.remember);

        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        edt_usr.setText(sharedPreferences.getString("username", null));
        edt_pass.setText(sharedPreferences.getString("password", null));
        cbRemember.setChecked(sharedPreferences.getBoolean("checked", false));
    }

    @Override
    protected void onStart() {
        super.onStart();

        loginBtn.setOnClickListener(view -> {
            username = edt_usr.getText().toString();
            password = edt_pass.getText().toString();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);

            if(username.isEmpty() || password.isEmpty())
                CustomToast.makeText(getApplicationContext(), "Yêu cầu tên đăng nhập và mật khẩu", CustomToast.LENGTH_SHORT, CustomToast.WARNING, true).show();
            else {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
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
                                    CustomToast.makeText(getApplicationContext(), "Đăng nhập thành công", CustomToast.LENGTH_SHORT, CustomToast.SUCCESS, true).show();
                                    if(cbRemember.isChecked()){
                                        editor.putString("username", username);
                                        editor.putString("password", password);
                                        editor.putBoolean("checked", true);
                                        editor.commit();
                                    }else{
                                        editor.remove("username");
                                        editor.remove("password");
                                        editor.remove("checked");
                                        editor.commit();
                                    }
                                    startActivity(i);
                                    finish();
                                } else if(result.equals("Ten dang nhap hoac mat khau sai"))
                                    CustomToast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu sai", CustomToast.LENGTH_SHORT, CustomToast.ERROR, true).show();
                                else if(result.equals("Loi ket noi"))
                                    CustomToast.makeText(getApplicationContext(), "Lỗi kết nối", CustomToast.LENGTH_SHORT, CustomToast.ERROR, true).show();
                                else
                                    CustomToast.makeText(getApplicationContext(), "Vui lòng kết nối mạng", CustomToast.LENGTH_SHORT, CustomToast.WARNING, true).show();
                            }
                        }
                    }
                });
            }
        });
    }
}