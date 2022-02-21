package com.example.banlsinh.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banlsinh.ui.adapter.LeSinh;
import com.example.banlsinh.ui.adapter.LeSinhAdapter;
import com.example.banlsinh.R;
import com.example.banlsinh.custom.CustomToast;
import com.example.banlsinh.ultility.NetworkChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public String urlGetData = "http://banlesinhgiaoxutanthanh.000webhostapp.com/lesinh/get.php";
    public String urlRemove = "http://banlesinhgiaoxutanthanh.000webhostapp.com/lesinh/remove.php";
    public ListView lvLeSinh;
    public ArrayList<LeSinh> arrayLeSinh;
    public LeSinhAdapter adapter;
    private FloatingActionButton faMenu, faExit, faAdd, faUp;
    private Animation fabOpen, fabClose, fabFromBottom, fabToBottom, fabFromLeft, fabToLeft, fabFromLeftBot, fabToLeftBot;
    private boolean isOpen = false;
    public NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private Context context;
    private ProgressBar getProgress;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        lvLeSinh = findViewById(R.id.listview_lesinh);
        faMenu = findViewById(R.id.floating_menu);
        faAdd = findViewById(R.id.add_altarboy);
        faExit = findViewById(R.id.logout);
        faUp = findViewById(R.id.toplist);
        getProgress = findViewById(R.id.progress_loading_list);
        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        fabOpen = AnimationUtils.loadAnimation(context, R.anim.rotate_open);
        fabClose = AnimationUtils.loadAnimation(context, R.anim.rotate_close);
        fabToBottom = AnimationUtils.loadAnimation(context, R.anim.to_bottom);
        fabFromBottom = AnimationUtils.loadAnimation(context, R.anim.from_bottom);
        fabFromLeft = AnimationUtils.loadAnimation(context, R.anim.from_left);
        fabToLeft = AnimationUtils.loadAnimation(context, R.anim.to_left);
        fabFromLeftBot = AnimationUtils.loadAnimation(context, R.anim.from_bottom_left);
        fabToLeftBot = AnimationUtils.loadAnimation(context, R.anim.to_bottom_left);

        GetData(urlGetData);
        arrayLeSinh = new ArrayList<>();
        adapter = new LeSinhAdapter(this, R.layout.dong_le_sinh, arrayLeSinh);
        lvLeSinh.setAdapter(adapter);
        getProgress.setVisibility(View.GONE);

        faMenu.setOnClickListener(view -> animateFab());
        faAdd.setOnClickListener(view -> startActivity(new Intent(context, AddAltarboy.class)));
        faExit.setOnClickListener(view -> startActivity(new Intent(context, LogIn.class)));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onRefresh() {
                GetData(urlGetData);
                onRefesh(false);
            }
        });
        lvLeSinh.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {}

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int topRowVerticalPosition = (lvLeSinh == null || lvLeSinh.getChildCount() == 0) ? 0 : lvLeSinh.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(i == 0 && topRowVerticalPosition >= 0);
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        RequestPermission();

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private void onRefesh(boolean flag) {
        swipeRefreshLayout.setRefreshing(flag);
        swipeRefreshLayout.setColorSchemeResources(R.color.main);
    }

    private void GetData(String url) {
        onRefesh(true);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    arrayLeSinh.clear();
                    for (int i = 0; i < response.length(); i++) {
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    CustomToast.makeText(context, "Kết nối thất bại", CustomToast.LENGTH_SHORT, CustomToast.ERROR, true).show();
                    error.printStackTrace();
                });
        requestQueue.add(jsonArrayRequest);
        onRefesh(false);
    }

    public void RemoveAltarBoy(int idLS){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRemove,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            CustomToast.makeText(context, "Xoá thành công", CustomToast.LENGTH_SHORT, CustomToast.SUCCESS, true).show();
                            GetData(urlGetData);
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
                params.put("ID", String.valueOf(idLS));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void RequestPermission(){
        TedPermission.create()
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {}

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) { }
                })
                .setPermissions(Manifest.permission.CALL_PHONE)
                .setDeniedTitle("Mở cài đặt Quyền cho ứng dụng")
                .setDeniedMessage("Vui lòng truy cập Quyền ứng dụng\nCài đặt > Quyền")
                .check();
    }

    private void animateFab() {
        if (isOpen) {
            faMenu.startAnimation(fabClose);
            faUp.startAnimation(fabToBottom);
            faExit.startAnimation(fabToLeft);
            faAdd.startAnimation(fabToLeftBot);
            faAdd.setClickable(false);
            faExit.setClickable(false);
            faUp.setClickable(false);
            isOpen = false;
        } else {
            faMenu.startAnimation(fabOpen);
            faUp.startAnimation(fabFromBottom);
            faExit.startAnimation(fabFromLeft);
            faAdd.startAnimation(fabFromLeftBot);
            faAdd.setClickable(true);
            faExit.setClickable(true);
            faUp.setClickable(true);
            isOpen = true;
        }
    }

}