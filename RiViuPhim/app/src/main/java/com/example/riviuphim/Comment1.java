package com.example.riviuphim;

import java.util.Date;

public class Comment1 {
    String binhluan;
    int diemso;
    Date ngaybinhluan;

    public Comment1() {

    }

    public Comment1(String binhluan, int diemso) {
        this.binhluan = binhluan;
        this.diemso = diemso;
    }

    public Comment1(String binhluan, int diemso, Date ngaybinhluan) {
        this.binhluan = binhluan;
        this.diemso = diemso;
        this.ngaybinhluan = ngaybinhluan;
    }


    public Date getNgaybinhluan() {
        return ngaybinhluan;
    }

    public void setNgaybinhluan(Date ngaybinhluan) {
        this.ngaybinhluan = ngaybinhluan;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }

    public int getDiemso() {
        return diemso;
    }

    public void setDiemso(int diemso) {
        this.diemso = diemso;
    }
}
