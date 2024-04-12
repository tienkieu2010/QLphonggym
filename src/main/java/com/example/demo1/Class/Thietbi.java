package com.example.demo1.Class;


public class Thietbi {
    public String Tenthietbi;
    public int Soluong;
    public String Tenkhutap;
    public String Trangthai;

    public Thietbi(String Tenthietbi, int Soluong, String Tenkhutap, String Trangthai) {
        this.Tenthietbi = Tenthietbi;
        this.Soluong = Soluong;
        this.Tenkhutap = Tenkhutap;
        this.Trangthai = Trangthai;
    }

    public String getTenthietbi() {
        return Tenthietbi;
    }

    public void setTenthietbi(String Tenthietbi) {
        this.Tenthietbi = Tenthietbi;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int Soluong) {
        this.Soluong = Soluong;
    }

    public String getTenkhutap() {
        return Tenkhutap;
    }

    public void setTenkhutap(String Tenkhutap) {
        this.Tenkhutap = Tenkhutap;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String Tenkhutap) {
        this.Trangthai = Trangthai;
    }
}