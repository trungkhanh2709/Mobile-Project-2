package com.example.riviuphim;

import java.util.Date;

public class DaoDien {
    int maDaoDien;
    String tenDaoDien,quocTich,urlHinhAnh;
    Date ngayThangNamSinh;

    public DaoDien(int maDaoDien, String tenDaoDien, String quocTich, Date ngayThangNamSinh) {
        this.maDaoDien = maDaoDien;
        this.tenDaoDien = tenDaoDien;
        this.quocTich = quocTich;
        this.ngayThangNamSinh = ngayThangNamSinh;
    }

    public DaoDien(int maDaoDien, String tenDaoDien, String quocTich, Date ngayThangNamSinh, String urlHinhAnh) {
        this.maDaoDien = maDaoDien;
        this.tenDaoDien = tenDaoDien;
        this.quocTich = quocTich;
        this.urlHinhAnh = urlHinhAnh;
        this.ngayThangNamSinh = ngayThangNamSinh;
    }

    public String getUrlHinhAnh() {
        return urlHinhAnh;
    }

    public void setUrlHinhAnh(String urlHinhAnh) {
        this.urlHinhAnh = urlHinhAnh;
    }

    public DaoDien(int maDaoDien, String tenDaoDien, String quocTich) {
        this.maDaoDien = maDaoDien;
        this.tenDaoDien = tenDaoDien;
        this.quocTich = quocTich;
    }

    public DaoDien(int maDaoDien, String tenDaoDien) {
        this.maDaoDien = maDaoDien;
        this.tenDaoDien = tenDaoDien;
    }

    public DaoDien() {
    }

    public int getMaDaoDien() {
        return maDaoDien;
    }

    public void setMaDaoDien(int maDaoDien) {
        this.maDaoDien = maDaoDien;
    }

    public String getTenDaoDien() {
        return tenDaoDien;
    }

    public void setTenDaoDien(String tenDaoDien) {
        this.tenDaoDien = tenDaoDien;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public Date getNgayThangNamSinh() {
        return ngayThangNamSinh;
    }

    public void setNgayThangNamSinh(Date ngayThangNamSinh) {
        this.ngayThangNamSinh = ngayThangNamSinh;
    }
}
