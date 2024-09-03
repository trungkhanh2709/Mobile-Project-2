package com.example.riviuphim;

public class TheLoai {
    int maTheLoai;
    String TenTheLoai;

    public TheLoai(int maTheLoai, String tenTheLoai) {
        this.maTheLoai = maTheLoai;
        TenTheLoai = tenTheLoai;
    }

    public TheLoai() {
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }
}
