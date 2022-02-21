package com.example.banlsinh.ultility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.banlsinh.R;

public class RemoveClickListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout_diaglog = LayoutInflater.from(context).inflate(R.layout.remove_dialog, null);
        builder.setView(layout_diaglog);

    }
}
