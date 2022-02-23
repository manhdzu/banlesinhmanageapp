package com.example.banlsinh.ui.adapter;

import java.io.Serializable;

public class LeSinh implements Serializable {
    private int Id;
    private final int ngaysinh;
    private final int thangsinh;
    private final int namsinh;
    private final String tenthanh;
    private final String ho;
    private final String ten;
    private final String lienlac;
    private final String diachi;

    public LeSinh(int id, String tenthanh, String ho, String ten, int ngaysinh, int thangsinh, int namsinh, String lienlac, String diachi) {
        Id = id;
        this.ngaysinh = ngaysinh;
        this.thangsinh = thangsinh;
        this.namsinh = namsinh;
        this.tenthanh = tenthanh;
        this.ho = ho;
        this.ten = ten;
        this.lienlac = lienlac;
        this.diachi = diachi;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getNgaysinh() {
        return ngaysinh;
    }

    public int getThangsinh() {
        return thangsinh;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public String getTenthanh() {
        return tenthanh;
    }

    public String getHo() {
        return ho;
    }

    public String getTen() {
        return ten;
    }

    public String getLienlac() {
        return lienlac;
    }

    public String getDiachi() {
        return diachi;
    }

}

