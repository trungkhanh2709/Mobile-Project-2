package com.example.riviuphim;

public class Data {
    static String ip = "192.168.0.101:8080";

    static int idUser;
    static  int idPhim;

    public Data() {
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Data.ip = ip;
    }

    public static int getIdUser() {
        return idUser;
    }

    public static void setIdUser(int idUser) {
        Data.idUser = idUser;
    }

    public static int getIdPhim() {
        return idPhim;
    }

    public static void setIdPhim(int idPhim) {
        Data.idPhim = idPhim;
    }
}
