package com.example.riviuphim;

public class ImageFilm {
    String  UrlHinhAnh;
    int MaPhim,MaHinhAnh;
    public ImageFilm() {
    }

    public ImageFilm(int maHinhAnh, String urlHinhAnh, int maPhim) {
        MaHinhAnh = maHinhAnh;
        UrlHinhAnh = urlHinhAnh;
        MaPhim = maPhim;
    }

    public int getMaHinhAnh() {
        return MaHinhAnh;
    }

    public void setMaHinhAnh(int maHinhAnh) {
        MaHinhAnh = maHinhAnh;
    }

    public String getUrlHinhAnh() {
        return UrlHinhAnh;
    }

    public void setUrlHinhAnh(String urlHinhAnh) {
        UrlHinhAnh = urlHinhAnh;
    }

    public int getMaPhim() {
        return MaPhim;
    }

    public void setMaPhim(int maPhim) {
        MaPhim = maPhim;
    }
}
