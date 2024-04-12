package com.example.demo1.Class;

public class Khutap {
    public String Tenkhutap;
    public String Loaihinhtap;
    public String Manv;
    public String Trangthai;

    public Khutap() {

    }

    public Khutap(String Tenkhutap, String Loaihinhtap, String Manv, String Trangthai) {
        this.Tenkhutap = Tenkhutap;
        this.Loaihinhtap = Loaihinhtap;
        this.Manv = Manv;
        this.Trangthai = Trangthai;


    }

    public String getTenkhutap() {
        return Tenkhutap;
    }

    public void setTenkhutap(String tenKhuTap) {
        Tenkhutap = tenKhuTap;
    }

    public String getLoaihinhtap() {
        return Loaihinhtap;
    }

    public void setLoaihinhtap(String loaihinhtap) {
        Loaihinhtap = loaihinhtap;
    }

    public String getManv() {
        return Manv;
    }

    public void setManv(String Manv) {
        this.Manv = Manv;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String trangthai) {
        Trangthai = trangthai;
    }
}
