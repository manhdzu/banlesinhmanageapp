package com.example.banlsinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public String urlGetData = "http://banlesinhgiaoxutanthanh.000webhostapp.com/lesinh/get.php";
    public ListView lvLeSinh;
    public ArrayList<LeSinh> arrayLeSinh;
    public LeSinhAdapter adapter;
    private ImageButton logout;
    private FloatingActionButton faMenu, faExit, faAdd;
    private Animation fabOpen, fabClose, fabFromBottom, fabToBottom, fabFromLeft, fabToLeft;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvLeSinh = findViewById(R.id.listview_lesinh);
        faMenu = findViewById(R.id.floating_menu);
        faAdd = findViewById(R.id.add_altarboy);
        faExit = findViewById(R.id.logout);
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close);
        fabToBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom);
        fabFromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom);
        fabFromLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_left);
        fabToLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_left);

        arrayLeSinh = new ArrayList<>();
        adapter = new LeSinhAdapter(getApplicationContext(), R.layout.dong_le_sinh, arrayLeSinh);
        lvLeSinh.setAdapter(adapter);

        GetData(urlGetData);
    }

    @Override
    protected void onStart() {
        super.onStart();

        faMenu.setOnClickListener(view -> animateFab());

        faAdd.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddAltarboy.class)));

        faExit.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), login.class)));
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

    private void animateFab(){
        if(isOpen){
            faMenu.startAnimation(fabClose);
            faAdd.startAnimation(fabToBottom);
            faExit.startAnimation(fabToLeft);
            faAdd.setClickable(false);
            faExit.setClickable(false);
            isOpen=false;
        }else{
            faMenu.startAnimation(fabOpen);
            faAdd.startAnimation(fabFromBottom);
            faExit.startAnimation(fabFromLeft);
            faAdd.setClickable(true);
            faExit.setClickable(true);
            isOpen=true;
        }
    }
}