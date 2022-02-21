package com.example.banlsinh.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banlsinh.R;
import com.example.banlsinh.custom.CustomToast;
import com.example.banlsinh.ui.adapter.LeSinh;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class EditAltarBoy extends AppCompatActivity {

    private Context context;
    private Intent intent;
    private EditText udt_tenthanh, udt_hoten, udt_ngaysinh, udt_lienlac, udt_diachi;
    private MaterialButton btn_cofirm1, btn_cancel1;
    private int id;
    private String type_tenthanh, type_hoten, type_ngaysinh, type_lienlac, type_diachi;
    private String urlUpdate = "https://banlesinhgiaoxutanthanh.000webhostapp.com/lesinh/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_altarboy);

        context = getApplicationContext();
        intent = getIntent();

        udt_tenthanh = findViewById(R.id.sua_tenthanh);
        udt_hoten = findViewById(R.id.sua_hoten);
        udt_ngaysinh = findViewById(R.id.sua_ngaysinh);
        udt_lienlac = findViewById(R.id.sua_lienlac);
        udt_diachi = findViewById(R.id.sua_diachi);
        btn_cofirm1 = findViewById(R.id.button_update);
        btn_cancel1 = findViewById(R.id.button_huy1);

        Intent intent = getIntent();
        LeSinh leSinh = (LeSinh) intent.getSerializableExtra("dataLeSinh");
        id = leSinh.getId();
        udt_tenthanh.setText(leSinh.getTenthanh());
        udt_hoten.setText(leSinh.getHoten());
        udt_ngaysinh.setText(leSinh.getNgaysinh());
        udt_lienlac.setText(leSinh.getLienlac());
        udt_diachi.setText(leSinh.getDiachi());
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_cofirm1.setOnClickListener(view -> {
            getText();
            if (type_tenthanh.isEmpty() || type_hoten.isEmpty() || type_diachi.isEmpty() || type_ngaysinh.isEmpty() || type_lienlac.isEmpty()) {
                CustomToast.makeText(context, getString(R.string.pleaseType), Toast.LENGTH_SHORT, CustomToast.WARNING, true).show();
            } else {
                EditAltarBoyFunc(urlUpdate);
            }
        });
        btn_cancel1.setOnClickListener(view -> finish());
    }

    private void EditAltarBoyFunc (String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")) {
                    CustomToast.makeText(context, "Cập nhật thành công", CustomToast.LENGTH_SHORT, CustomToast.SUCCESS, true).show();
                    startActivity(new Intent(context, MainActivity.class));
                } else CustomToast.makeText(context, getString(R.string.responseError), CustomToast.LENGTH_SHORT, CustomToast.SUCCESS, true).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));
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
    public void getText(){
        type_tenthanh = udt_tenthanh.getText().toString().trim();
        type_hoten = udt_hoten.getText().toString().trim();
        type_ngaysinh = udt_ngaysinh.getText().toString().trim();
        type_lienlac = udt_lienlac.getText().toString().trim();
        type_diachi = udt_diachi.getText().toString().trim();
    }
}
