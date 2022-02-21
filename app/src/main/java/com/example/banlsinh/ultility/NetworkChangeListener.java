package com.example.banlsinh.ultility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatButton;

import com.example.banlsinh.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Common.isConnectedToInternet((context))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_diaglog = LayoutInflater.from(context).inflate(R.layout.check_internet, null);
            builder.setView(layout_diaglog);

            AppCompatButton btnRetry = layout_diaglog.findViewById(R.id.btn_retry);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int dialog_height = display.getHeight();
            int dialog_width = display.getWidth();

            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout((20 * dialog_width) / 27, (200 * dialog_height) / 457);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            btnRetry.setOnClickListener(view -> {
                dialog.dismiss();
                onReceive(context, intent);
            });
        }
    }
}
