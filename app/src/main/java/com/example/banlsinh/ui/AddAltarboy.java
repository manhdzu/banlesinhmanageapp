package com.example.banlsinh.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banlsinh.R;
import com.example.banlsinh.custom.CustomToast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddAltarboy extends AppCompatActivity {

    private EditText edt_tenthanh, edt_ho, edt_ten, edt_ngaysinh, edt_thangsinh, edt_namsinh, edt_lienlac, edt_diachi;
    private Button btn_cofirm, btn_cancel;
    private Context context;
    private String type_tenthanh, type_ho, type_ten, type_ngaysinh, type_thangsinh, type_namsinh, type_lienlac, type_diachi, uploadby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_altarboy);

        context = getApplicationContext();

        edt_tenthanh = findViewById(R.id.them_tenthanh);
        edt_ho = findViewById(R.id.them_ho);
        edt_ten = findViewById(R.id.them_ten);
        edt_ngaysinh = findViewById(R.id.them_ngaysinh);
        edt_thangsinh = findViewById(R.id.them_thangsinh);
        edt_namsinh = findViewById(R.id.them_namsinh);
        edt_lienlac = findViewById(R.id.them_lienlac);
        edt_diachi = findViewById(R.id.them_diachi);
        btn_cofirm = findViewById(R.id.button_them);
        btn_cancel = findViewById(R.id.button_huy);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_cofirm.setOnClickListener(view -> {
            getText();
            if (type_tenthanh.isEmpty() || type_ho.isEmpty() || type_ten.isEmpty() || type_diachi.isEmpty() || type_ngaysinh.isEmpty() || type_lienlac.isEmpty()) {
                CustomToast.makeText(context, getString(R.string.pleaseType), Toast.LENGTH_SHORT, CustomToast.WARNING).show();
            } else {
                AddAltarBoyFunc();
            }
        });

        btn_cancel.setOnClickListener(view -> finish());
    }

    private void AddAltarBoyFunc() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://banlesinhgiaoxutanthanh.000webhostapp.com/lesinh/2.0/insert.php",
                response -> {
                    if (response.trim().equals("success")) {
                        CustomToast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                        startActivity(new Intent(context, MainActivity.class));
                    } else
                        CustomToast.makeText(context, "Thêm thất bại\n" + response.trim(), Toast.LENGTH_SHORT, CustomToast.ERROR).show();
                },
                error -> CustomToast.makeText(context, getString(R.string.responseError), Toast.LENGTH_SHORT, CustomToast.ERROR).show()) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tenthanh", type_tenthanh);
                params.put("ho", type_ho);
                params.put("ten", type_ten);
                params.put("ngaysinh", type_ngaysinh);
                params.put("thangsinh", type_thangsinh);
                params.put("namsinh", type_namsinh);
                params.put("lienlac", type_lienlac);
                params.put("diachi", type_diachi);
                params.put("uploadby", uploadby);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getText() {
        type_tenthanh = edt_tenthanh.getText().toString().trim();
        type_ho = edt_ho.getText().toString().trim();
        type_ten = edt_ten.getText().toString().trim();
        type_ngaysinh = edt_ngaysinh.getText().toString().trim();
        type_thangsinh = edt_thangsinh.getText().toString().trim();
        type_namsinh = edt_namsinh.getText().toString().trim();
        type_lienlac = edt_lienlac.getText().toString().trim();
        type_diachi = edt_diachi.getText().toString().trim();

        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        uploadby = sharedPreferences.getString("uploadby", null);
    }
}