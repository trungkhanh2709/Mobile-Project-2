package com.example.riviuphim;

public class Nguoidung {
    int manguoidung;
    String tenNguoiDung;

    public int getManguoidung() {
        return manguoidung;
    }

    public void setManguoidung(int manguoidung) {
        this.manguoidung = manguoidung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public Nguoidung(int mamadanhgia, String tenNguoiDung) {
        this.manguoidung = mamadanhgia;
        this.tenNguoiDung = tenNguoiDung;
    }

    public Nguoidung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }
}
