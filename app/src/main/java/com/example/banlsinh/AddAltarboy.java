package com.example.banlsinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAltarboy extends AppCompatActivity {

    EditText edt_tenthanh, edt_hoten, edt_ngaysinh, edt_lienlac, edt_diachi;
    Button btn_cofirm, btn_cancel;
    Context context;
    String type_tenthanh, type_hoten, type_ngaysinh, type_lienlac, type_diachi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_altarboy);

        context = getApplicationContext();

        edt_tenthanh = (EditText) findViewById(R.id.them_tenthanh);
        edt_hoten = (EditText) findViewById(R.id.them_hoten);
        edt_ngaysinh = (EditText) findViewById(R.id.them_ngaysinh);
        edt_lienlac = (EditText) findViewById(R.id.them_lienlac);
        edt_diachi = (EditText) findViewById(R.id.them_diachi);
        btn_cofirm = (Button) findViewById(R.id.button_them);
        btn_cancel = (Button) findViewById(R.id.button_huy);



        btn_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getText();
                if(type_tenthanh.isEmpty() || type_hoten.isEmpty() || type_diachi.isEmpty() || type_ngaysinh.isEmpty() || type_lienlac.isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddAltarboy.this, MainActivity.class));
            }
        });
    }

    public void getText(){
        type_tenthanh = edt_tenthanh.getText().toString().trim();
        type_hoten = edt_hoten.getText().toString().trim();
        type_ngaysinh = edt_ngaysinh.getText().toString().trim();
        type_lienlac = edt_lienlac.getText().toString().trim();
        type_diachi = edt_diachi.getText().toString().trim();
    }
}