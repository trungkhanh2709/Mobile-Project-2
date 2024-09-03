package com.example.riviuphim;

public class DanhGia {
    int MaDanhGia, MaPhim, MaNguoiDung;
    int DiemSo;
    String BinhLuan,tenNguoiDung,NgayBinhLuan;

    public DanhGia(int maDanhGia, int maPhim, int maNguoiDung, int diemSo, String binhLuan) {
        MaDanhGia = maDanhGia;
        MaPhim = maPhim;
        MaNguoiDung = maNguoiDung;
        DiemSo = diemSo;
        BinhLuan = binhLuan;
    }


    public DanhGia() {
    }

    public DanhGia(String name, int diemso, String binhluan, String date) {
        this.tenNguoiDung = name;
        this.DiemSo = diemso;
        this.BinhLuan = binhluan;
        this.NgayBinhLuan = date;
    }
    public DanhGia(String tennguoidung, String binhluan, int diemso) {
        this.tenNguoiDung = tennguoidung;
        this.BinhLuan = binhluan;
        this.DiemSo = diemso;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public int getMaDanhGia() {
        return MaDanhGia;
    }

    public void setMaDanhGia(int maDanhGia) {
        MaDanhGia = maDanhGia;
    }

    public int getMaPhim() {
        return MaPhim;
    }

    public void setMaPhim(int maPhim) {
        MaPhim = maPhim;
    }

    public int getMaNguoiDung() {
        return MaNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        MaNguoiDung = maNguoiDung;
    }

    public int getDiemSo() {
        return DiemSo;
    }

    public void setDiemSo(int diemSo) {
        DiemSo = diemSo;
    }

    public String getBinhLuan() {
        return BinhLuan;
    }

    public void setBinhLuan(String binhLuan) {
        BinhLuan = binhLuan;
    }

    public String getNgayBinhLuan() {
        return NgayBinhLuan;
    }

    public void setNgayBinhLuan(String ngayBinhLuan) {
        NgayBinhLuan = ngayBinhLuan;
    }
}
