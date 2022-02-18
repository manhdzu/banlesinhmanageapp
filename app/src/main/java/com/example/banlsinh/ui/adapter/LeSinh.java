package com.example.banlsinh.ui.adapter;

public class LeSinh {
    private int Id;
    private String tenthanh, hoten, ngaysinh, lienlac, diachi;

    public LeSinh(int id, String tenthanh, String hoten, String ngaysinh, String lienlac, String diachi) {
        Id = id;
        this.tenthanh = tenthanh;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.lienlac = lienlac;
        this.diachi = diachi;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenthanh() {
        return tenthanh;
    }

    public void setTenthanh(String tenthanh) {
        this.tenthanh = tenthanh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getLienlac() {
        return lienlac;
    }

    public void setLienlac(String lienlac) {
        this.lienlac = lienlac;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
