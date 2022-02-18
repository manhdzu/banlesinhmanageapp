package com.example.banlsinh.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banlsinh.R;
import com.example.banlsinh.custom.CustomToast;

import java.util.HashMap;
import java.util.Map;

public class AddAltarboy extends AppCompatActivity {

    private EditText edt_tenthanh, edt_hoten, edt_ngaysinh, edt_lienlac, edt_diachi;
    private Button btn_cofirm, btn_cancel;
    private Context context;
    private String type_tenthanh, type_hoten, type_ngaysinh, type_lienlac, type_diachi;
    private final String urlInsert = "https://banlesinhgiaoxutanthanh.000webhostapp.com/lesinh/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_altarboy);

        context = getApplicationContext();

        edt_tenthanh = findViewById(R.id.them_tenthanh);
        edt_hoten = findViewById(R.id.them_hoten);
        edt_ngaysinh = findViewById(R.id.them_ngaysinh);
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
            if (type_tenthanh.isEmpty() || type_hoten.isEmpty() || type_diachi.isEmpty() || type_ngaysinh.isEmpty() || type_lienlac.isEmpty()) {
                CustomToast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT, CustomToast.WARNING, true).show();
            } else {
                AddAltarBoyFunc(urlInsert);
                startActivity(new Intent(context, MainActivity.class));
            }
        });

        btn_cancel.setOnClickListener(view -> finish());
    }

    private void AddAltarBoyFunc(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.trim().equals("success")) {
                        CustomToast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT, CustomToast.SUCCESS, true).show();
                        finish();
                    } else
                        CustomToast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT, CustomToast.ERROR, true).show();
                },
                error -> CustomToast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT, CustomToast.ERROR, true).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tenthanh", type_tenthanh);
                params.put("hoten", type_hoten);
                params.put("ngaysinh", type_ngaysinh);
                params.put("lienlac", type_lienlac);
                params.put("diachi", type_diachi);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getText() {
        type_tenthanh = edt_tenthanh.getText().toString().trim();
        type_hoten = edt_hoten.getText().toString().trim();
        type_ngaysinh = edt_ngaysinh.getText().toString().trim();
        type_lienlac = edt_lienlac.getText().toString().trim();
        type_diachi = edt_diachi.getText().toString().trim();
    }
}