package com.example.banlsinh.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
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
import com.example.banlsinh.ui.adapter.LeSinh;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditAltarBoy extends AppCompatActivity {
    private Context context;
    private EditText udt_tenthanh, udt_ho, udt_ten, udt_ngaysinh, udt_thangsinh, udt_namsinh, udt_lienlac, udt_diachi;
    private MaterialButton btn_cofirm1, btn_cancel1;
    private int id;
    private String type_tenthanh, type_ho, type_ten, type_ngaysinh, type_thangsinh, type_namsinh, type_lienlac, type_diachi, uploadby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_altarboy);

        context = getApplicationContext();

        udt_tenthanh = findViewById(R.id.sua_tenthanh);
        udt_ho = findViewById(R.id.sua_ho);
        udt_ten = findViewById(R.id.sua_ten);
        udt_ngaysinh = findViewById(R.id.sua_ngaysinh);
        udt_thangsinh = findViewById(R.id.sua_thangsinh);
        udt_namsinh = findViewById(R.id.sua_namsinh);
        udt_lienlac = findViewById(R.id.sua_lienlac);
        udt_diachi = findViewById(R.id.sua_diachi);
        btn_cofirm1 = findViewById(R.id.button_update);
        btn_cancel1 = findViewById(R.id.button_huy1);

        Intent intent = getIntent();
        LeSinh leSinh = (LeSinh) intent.getSerializableExtra("dataLeSinh");
        id = leSinh.getId();
        udt_tenthanh.setText(leSinh.getTenthanh());
        udt_ho.setText(leSinh.getHo());
        udt_ten.setText(leSinh.getTen());
        udt_ngaysinh.setText(String.valueOf(leSinh.getNgaysinh()));
        udt_thangsinh.setText(String.valueOf(leSinh.getThangsinh()));
        udt_namsinh.setText(String.valueOf(leSinh.getNamsinh()));
        udt_lienlac.setText(leSinh.getLienlac());
        udt_diachi.setText(leSinh.getDiachi());
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_cofirm1.setOnClickListener(view -> {
            getText();
            if (type_tenthanh.isEmpty() || type_ho.isEmpty() || type_ten.isEmpty() || type_diachi.isEmpty() || type_ngaysinh.isEmpty() || type_thangsinh.isEmpty() || type_namsinh.isEmpty() || type_lienlac.isEmpty()) {
                CustomToast.makeText(context, getString(R.string.pleaseType), Toast.LENGTH_SHORT, CustomToast.WARNING).show();
            } else {
                EditAltarBoyFunc();
            }
        });
        btn_cancel1.setOnClickListener(view -> finish());
    }

    private void EditAltarBoyFunc() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://banlesinhgiaoxutanthanh.000webhostapp.com/lesinh/2.0/update.php", response -> {
            if (response.trim().equals("success")) {
                CustomToast.makeText(context, "Cập nhật thành công", CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                startActivity(new Intent(context, MainActivity.class));
            } else
                CustomToast.makeText(context, getString(R.string.responseError) + response.trim(), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
        }, error -> {

        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));
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
        type_tenthanh = udt_tenthanh.getText().toString().trim();
        type_ho = udt_ho.getText().toString().trim();
        type_ten = udt_ten.getText().toString().trim();
        type_ngaysinh = udt_ngaysinh.getText().toString().trim();
        type_thangsinh = udt_thangsinh.getText().toString().trim();
        type_namsinh = udt_namsinh.getText().toString().trim();
        type_lienlac = udt_lienlac.getText().toString().trim();
        type_diachi = udt_diachi.getText().toString().trim();

        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        uploadby = sharedPreferences.getString("uploadby", null);
    }
}
