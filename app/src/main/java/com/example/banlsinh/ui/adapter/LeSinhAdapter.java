package com.example.banlsinh.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.banlsinh.R;
import com.example.banlsinh.ui.EditAltarBoy;
import com.example.banlsinh.ui.MainActivity;

import java.util.ArrayList;

public class LeSinhAdapter extends BaseAdapter {

    private final MainActivity context;
    private final int layout;
    private ArrayList<LeSinh> leSinhList;
    private ArrayList<LeSinh> original;

    public LeSinhAdapter(MainActivity context, int layout, ArrayList<LeSinh> leSinhList) {
        this.context = context;
        this.layout = layout;
        this.leSinhList = leSinhList;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults filterResults = new FilterResults();
                final ArrayList<LeSinh> results = new ArrayList<>();
                if (original == null)
                    original = leSinhList;
                if (constraint != null) {
                    if (original != null && original.size() > 0) {
                        for (final LeSinh g : original) {
                            if (g.getTen().contains(constraint.toString()) || g.getTen().toLowerCase().contains(constraint.toString()) || g.getTen().toUpperCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    filterResults.values = results;
                }
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                leSinhList = (ArrayList<LeSinh>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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
        TextView txtTenThanh, txtHo, txtTen, txtNgaySinh, txtThangSinh, txtNamSinh, txtLienlac, txtDiaChi;
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
            holder.txtHo = view.findViewById(R.id.textview_ho);
            holder.txtTen = view.findViewById(R.id.textview_ten);
            holder.txtDiaChi = view.findViewById(R.id.textview_diachi_re);
            holder.txtLienlac = view.findViewById(R.id.textview_lienlac_re);
            holder.txtNgaySinh = view.findViewById(R.id.textview_ngaysinh_re);
            holder.txtThangSinh = view.findViewById(R.id.textview_thangsinh_re);
            holder.txtNamSinh = view.findViewById(R.id.textview_namsinh_re);
            holder.item_layout = view.findViewById(R.id.item_layout);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        LeSinh leSinh = leSinhList.get(i);

        holder.txtTenThanh.setText(leSinh.getTenthanh() + " ");
        holder.txtHo.setText(leSinh.getHo() + " ");
        holder.txtTen.setText(leSinh.getTen());
        if (leSinh.getNgaysinh() < 10)
            holder.txtNgaySinh.setText("0" + leSinh.getNgaysinh());
        else holder.txtNgaySinh.setText(String.valueOf(leSinh.getNgaysinh()));
        if (leSinh.getThangsinh() < 10)
            holder.txtThangSinh.setText("0" + leSinh.getThangsinh());
        else holder.txtThangSinh.setText(String.valueOf(leSinh.getThangsinh()));
        holder.txtNamSinh.setText(String.valueOf(leSinh.getNamsinh()));
        holder.txtLienlac.setText(leSinh.getLienlac());
        holder.txtDiaChi.setText(leSinh.getDiachi());

        holder.txtLienlac.setOnLongClickListener(view1 -> {
            String phoneNumber = holder.txtLienlac.getText().toString().trim();
            Intent makeCall = new Intent(Intent.ACTION_CALL);
            makeCall.setData(Uri.parse("tel:" + phoneNumber));
            makeCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(makeCall);

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

        holder.item_layout.setOnClickListener(view13 -> {
            Intent editIntent = new Intent(context, EditAltarBoy.class);
            editIntent.putExtra("dataLeSinh", leSinh);
            editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(editIntent);
        });

        holder.item_layout.setOnLongClickListener(view14 -> {
            comfirmRemove(leSinh.getTenthanh(), leSinh.getHo(), leSinh.getTen(), leSinh.getId());
            return true;
        });
        return view;
    }

    private void comfirmRemove(String holyname, String firstname, String lastname, final int id) {
        AlertDialog.Builder dialogRemove = new AlertDialog.Builder(context);
        dialogRemove.setMessage("Xác nhận xoá lễ sinh " + holyname + " " + firstname + " " + lastname);
        dialogRemove.setPositiveButton("Xác nhận", (dialogInterface, i) -> context.RemoveAltarBoy(id));
        dialogRemove.setNegativeButton("Huỷ", (dialogInterface, i) -> {});
        dialogRemove.show();
    }
}
