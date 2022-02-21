package com.example.banlsinh.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.banlsinh.R;
import com.example.banlsinh.custom.CustomToast;
import com.example.banlsinh.ui.EditAltarBoy;
import com.example.banlsinh.ui.MainActivity;
import com.example.banlsinh.ultility.RemoveClickListener;

import java.util.List;

public class LeSinhAdapter extends BaseAdapter {

    private final MainActivity context;
    private final int layout;
    private final List<LeSinh> leSinhList;

    public LeSinhAdapter(MainActivity context, int layout, List<LeSinh> leSinhList) {
        this.context = context;
        this.layout = layout;
        this.leSinhList = leSinhList;
    }


    @Override
    public int getCount() {
        return leSinhList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private static class ViewHolder {
        TextView txtTenThanh, txtHoten, txtNgaySinh, txtLienlac, txtDiaChi;
        ImageView imgDelete, imgEdit;
        RelativeLayout item_layout;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTenThanh = view.findViewById(R.id.textview_tenthanh);
            holder.txtHoten = view.findViewById(R.id.textview_hoten);
            holder.txtDiaChi = view.findViewById(R.id.textview_diachi_re);
            holder.txtLienlac = view.findViewById(R.id.textview_lienlac_re);
            holder.txtNgaySinh = view.findViewById(R.id.textview_ngaysinh_re);
            holder.item_layout = view.findViewById(R.id.item_layout);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        LeSinh leSinh = leSinhList.get(i);

        holder.txtTenThanh.setText(leSinh.getTenthanh() + " ");
        holder.txtHoten.setText(leSinh.getHoten());
        holder.txtNgaySinh.setText(leSinh.getNgaysinh());
        holder.txtLienlac.setText(leSinh.getLienlac());
        holder.txtDiaChi.setText(leSinh.getDiachi());

        holder.txtLienlac.setOnLongClickListener(view1 -> {
            String phoneNumber = holder.txtLienlac.getText().toString().trim();
            try {
                Intent makeCall = new Intent(Intent.ACTION_CALL);
                makeCall.setData(Uri.parse("tel:" + phoneNumber));
                makeCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(makeCall);
            } catch (Exception e) {
                CustomToast.makeText(context, "Xảy ra lỗi", CustomToast.LENGTH_SHORT, CustomToast.ERROR, true).show();
            }
            return false;
        });

        holder.txtDiaChi.setOnLongClickListener(view12 -> {
            String address = holder.txtDiaChi.getText().toString().trim();
            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mapIntent);
            return true;
        });

        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(context, EditAltarBoy.class);
                editIntent.putExtra("dataLeSinh", leSinh);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(editIntent);
            }
        });

        holder.item_layout.setOnLongClickListener(view14 -> {
            comfirmRemove(leSinh.getTenthanh(), leSinh.getHoten(), leSinh.getId());
            return true;
        });
        return view;
    }

    private void comfirmRemove(String holyname, String name, final int id) {
        AlertDialog.Builder dialogRemove = new AlertDialog.Builder(context);
        dialogRemove.setMessage("Xác nhận xoá lễ sinh " + holyname + " " + name);
        dialogRemove.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.RemoveAltarBoy(id);
            }
        });
        dialogRemove.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogRemove.show();
    }
}
