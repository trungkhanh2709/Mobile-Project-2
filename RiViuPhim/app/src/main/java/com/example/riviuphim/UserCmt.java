package com.example.riviuphim;

import java.util.Date;

public class UserCmt {
    int maDanhGia, maPhim, maNguoiDung;
    int diemSo;
    String binhLuan;
    Date ngaybinhluan;

    public UserCmt(int maDanhGia, int maPhim, int maNguoiDung, int diemSo, String binhLuan, Date ngaybinhluan) {
        this.maDanhGia = maDanhGia;
        this.maPhim = maPhim;
        this.maNguoiDung = maNguoiDung;
        this.diemSo = diemSo;
        this.binhLuan = binhLuan;
        this.ngaybinhluan = ngaybinhluan;
    }

    public UserCmt(int maDanhGia, int maPhim, int maNguoiDung, int diemSo, String binhLuan) {
        this.maDanhGia = maDanhGia;
        this.maPhim = maPhim;
        this.maNguoiDung = maNguoiDung;
        this.diemSo = diemSo;
        this.binhLuan = binhLuan;
    }

    public UserCmt(int diemSo, String binhLuan) {
        this.diemSo = diemSo;
        this.binhLuan = binhLuan;
    }

    public UserCmt(int diemSo, String binhLuan, Date ngaybinhluan) {
        this.diemSo = diemSo;
        this.binhLuan = binhLuan;
        this.ngaybinhluan = ngaybinhluan;
    }



    public int getMaDanhGia() {
        return maDanhGia;
    }

    public void setMaDanhGia(int maDanhGia) {
        this.maDanhGia = maDanhGia;
    }

    public int getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public int getDiemSo() {
        return diemSo;
    }

    public void setDiemSo(int diemSo) {
        this.diemSo = diemSo;
    }

    public String getBinhLuan() {
        return binhLuan;
    }

    public void setBinhLuan(String binhLuan) {
        this.binhLuan = binhLuan;
    }

    public Date getNgaybinhluan() {
        return ngaybinhluan;
    }

    public void setNgaybinhluan(Date ngaybinhluan) {
        this.ngaybinhluan = ngaybinhluan;
    }
}
