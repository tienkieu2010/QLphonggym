package com.example.demo1.Class;

public class Hoivien {
    public String Mahoivien;
    public String Tenhoivien;
    public String Diachi;
    public int Gioitinh;
    public int Namsinh;
    public int Sdthv;

    public Hoivien(){

    }

    public Hoivien(String Mahoivien, String Tenhoivien, String Diachi, int Gioitinh, int Namsinh, int Sdthv) {
        this.Mahoivien = Mahoivien;
        this.Tenhoivien = Tenhoivien;
        this.Diachi = Diachi;
        this.Gioitinh = Gioitinh;
        this.Namsinh = Namsinh;
        this.Sdthv=Sdthv;
    }

    public String getMahoivien() {
        return Mahoivien;
    }

    public void setMahoivien(String Mahoivien) {
        this.Mahoivien = Mahoivien;
    }

    public String getTenhoivien() {
        return Tenhoivien;
    }

    public void setTenhoivien(String Tenhoivien) {
        this.Tenhoivien = Tenhoivien;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String Diachi) {
        this.Diachi = Diachi;
    }

    public int getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(int Gioitinh) {
        this.Gioitinh = Gioitinh;
    }

    public int getNamsinh() {
        return Namsinh;
    }

    public void setNamsinh(int Namsinh) {
        this.Namsinh = Namsinh;
    }

    public int getSdthv() {
        return Sdthv;
    }

    public void setSodt(int Sodt) {
        this.Sdthv = Sodt;
    }
}

