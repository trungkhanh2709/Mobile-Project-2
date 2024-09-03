package com.example.riviuphim;

public class Phim_DienVien {
    private int maPhim, maDienVien;

    public Phim_DienVien(int maPhim, int maDienVien) {
        this.maPhim = maPhim;
        this.maDienVien = maDienVien;
    }

    public Phim_DienVien() {
    }

    public int getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }

    public int getMaDienVien() {
        return maDienVien;
    }

    public void setMaDienVien(int maDienVien) {
        this.maDienVien = maDienVien;
    }
}
