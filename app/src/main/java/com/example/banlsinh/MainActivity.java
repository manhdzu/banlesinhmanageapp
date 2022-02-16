package com.example.banlsinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String urlGetData = "http://banlesinhgiaoxutanthanh.000webhostapp.com/lesinh/get.php";
    ListView lvLeSinh;
    ArrayList<LeSinh> arrayLeSinh;
    LeSinhAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvLeSinh = findViewById(R.id.listview_lesinh);
        arrayLeSinh = new ArrayList<>();
        adapter = new LeSinhAdapter(getApplicationContext(), R.layout.dong_le_sinh, arrayLeSinh);
        lvLeSinh.setAdapter(adapter);

        GetData(urlGetData);
    }

    private void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for(int i = 0; i < response.length(); i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            arrayLeSinh.add(new LeSinh(
                                    object.getInt("ID"),
                                    object.getString("tenthanh"),
                                    object.getString("hoten"),
                                    object.getString("ngaysinh"),
                                    object.getString("lienlac"),
                                    object.getString("diachi")
                            ));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                });
        requestQueue.add(jsonArrayRequest);

    }
}