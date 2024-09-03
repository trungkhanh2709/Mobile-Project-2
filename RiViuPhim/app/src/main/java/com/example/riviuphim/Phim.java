package com.example.riviuphim;

import java.io.Serializable;

public class Phim implements Serializable {
    private int maPhim;
     String tenPhim;
    private int NamRaMat;
    private int diemSo;
    private String quocGia;
    private String moTaPhim;
    private String urlTrailer;
     String PosterPhim;

    // Constructor
    public Phim(int maPhim, String tenPhim, int NamRaMat, String quocGia, String moTaPhim, String urlTrailer,String Poster) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.NamRaMat = NamRaMat;
        this.quocGia = quocGia;
        this.moTaPhim = moTaPhim;
        this.urlTrailer = urlTrailer;
        this.PosterPhim = Poster;
    }
    public Phim(String tenPhim, String PosterPhim, int diemSo, int maPhim) {
        this.tenPhim = tenPhim;
        this.PosterPhim = PosterPhim;
        this.diemSo = diemSo;
        this.maPhim = maPhim;
    }
    public Phim(String tenPhim, String Poster) {
        this.tenPhim = tenPhim;
        this.PosterPhim = Poster;
    }
    public String getPosterPhim() {
        return PosterPhim;
    }

    public void setPosterPhim(String posterPhim) {
        PosterPhim = posterPhim;
    }

    // Getters and Setters
    public int getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public int getNamRaMat() {
        return NamRaMat;
    }

    public void setNamRaMat(int NamRaMat) {
        this.NamRaMat = NamRaMat;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getMoTaPhim() {
        return moTaPhim;
    }

    public void setMoTaPhim(String moTaPhim) {
        this.moTaPhim = moTaPhim;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    public int getDiemSo() {
        return diemSo;
    }

    public void setDiemSo(int diemSo) {
        this.diemSo = diemSo;
    }

    public boolean searchPhim(String keyword){
        return tenPhim.toLowerCase().contains(keyword.toLowerCase());
    }
}
