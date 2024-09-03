package com.example.riviuphim;

import java.util.Date;

public class DienVien {
    private int maDienVien;
    private String tenDienVien, quocTich,UrlHinhAnh;
    private Date ngayThangNamSinh;

    public DienVien(int maDienVien, String tenDienVien, String quocTich, String urlHinhAnh, Date ngayThangNamSinh) {
        this.maDienVien = maDienVien;
        this.tenDienVien = tenDienVien;
        this.quocTich = quocTich;
        UrlHinhAnh = urlHinhAnh;
        this.ngayThangNamSinh = ngayThangNamSinh;
    }


    public DienVien(int maDienVien, String tenDienVien, String quocTich) {
        this.maDienVien = maDienVien;
        this.tenDienVien = tenDienVien;
        this.quocTich = quocTich;

    }

    public DienVien() {
    }

    public String getUrlHinhAnh() {
        return UrlHinhAnh;
    }

    public void setUrlHinhAnh(String urlHinhAnh) {
        UrlHinhAnh = urlHinhAnh;
    }

    public int getMaDienVien() {
        return maDienVien;
    }

    public void setMaDienVien(int maDienVien) {
        this.maDienVien = maDienVien;
    }

    public String getTenDienVien() {
        return tenDienVien;
    }

    public void setTenDienVien(String tenDienVien) {
        this.tenDienVien = tenDienVien;
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
