package com.example.banlsinh.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banlsinh.R;
import com.example.banlsinh.custom.CustomToast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Objects;

public class LogIn extends AppCompatActivity {

    private Button loginBtn;
    public EditText edt_usr, edt_pass;
    private ProgressBar progressBar;
    private String username, password;
    private CheckBox cbRemember;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();
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
            Intent i = new Intent(this, MainActivity.class);

            if (username.isEmpty() || password.isEmpty())
                CustomToast.makeText(getApplicationContext(), "Y??u c???u t??n ????ng nh???p v?? m???t kh???u", CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
            else {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(() -> {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "password";
                    String[] data = new String[2];
                    data[0] = username;
                    data[1] = password;
                    PutData putData = new PutData("https://banlesinhgiaoxutanthanh.000webhostapp.com/user/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            switch (result) {
                                case "Dang nhap thanh cong":
                                    CustomToast.makeText(getApplicationContext(), "????ng nh???p th??nh c??ng", CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                                    if (cbRemember.isChecked()) {
                                        editor.putString("username", username);
                                        editor.putString("password", password);
                                        editor.putBoolean("checked", true);
                                    } else {
                                        editor.remove("username");
                                        editor.remove("password");
                                        editor.remove("checked");
                                    }
                                    editor.putString("uploadby", username);
                                    editor.apply();
                                    startActivity(i);
                                    break;
                                case "Ten dang nhap hoac mat khau sai":
                                    CustomToast.makeText(getApplicationContext(), "T??n ????ng nh???p ho???c m???t kh???u sai", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                                    break;
                                case "Loi ket noi":
                                    CustomToast.makeText(getApplicationContext(), "L???i k???t n???i", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                                    break;
                                default:
                                    CustomToast.makeText(getApplicationContext(), "Vui l??ng k???t n???i m???ng", CustomToast.LENGTH_SHORT, CustomToast.WARNING).show();
                                    break;
                            }
                        }
                    }
                });
            }
        });
    }
}