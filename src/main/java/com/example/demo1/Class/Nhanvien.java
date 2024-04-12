package com.example.demo1.Class;

public class Nhanvien {
    public String Manhanvien;

    public String Tennhanvien;

    public String Taikhoan;

    public String Matkhau;

    public int Sdt;

    public Nhanvien(){

    }

    public Nhanvien(String Manhanvien, String Tennhanvien, String Taikhoan, String Matkhau, int Sdt) {
        this.Manhanvien = Manhanvien;
        this.Tennhanvien = Tennhanvien;
        this.Taikhoan = Taikhoan;
        this.Matkhau = Matkhau;
        this.Sdt = Sdt;
    }

    public String getManhanvien() {
        return Manhanvien;
    }

    public void setManhanvien(String Manhanvien) {
        this.Manhanvien = Manhanvien;
    }

    public String getTennhanvien() {
        return Tennhanvien;
    }

    public void setTennhanvien(String Tennhanvien) {
        this.Tennhanvien = Tennhanvien;
    }

    public int getSdt() {
        return Sdt;
    }

    public void setSdt(int Sdt) {
        this.Sdt = Sdt;
    }


    public String getTaikhoan() {
        return Taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        Taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        Matkhau = matkhau;
    }
}