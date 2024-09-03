package com.example.riviuphim;

public class phim_theloai {
    int maPhim, maTheLoai;

    public phim_theloai(int maPhim, int maTheLoai) {
        this.maPhim = maPhim;
        this.maTheLoai = maTheLoai;
    }

    public phim_theloai() {
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public int getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }
}
