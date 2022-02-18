package com.example.banlsinh.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.banlsinh.R;

import java.util.List;

public class LeSinhAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<LeSinh> leSinhList;

    public LeSinhAdapter(Context context, int layout, List<LeSinh> leSinhList) {
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

    private class ViewHolder {
        TextView txtTenThanh, txtHoten, txtNgaySinh, txtLienlac, txtDiaChi;
        ImageView imgDelete, imgEdit;
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
            holder.imgDelete = view.findViewById(R.id.imageview_remove);
            holder.imgEdit = view.findViewById(R.id.imageview_edit);
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
        return view;
    }
}
