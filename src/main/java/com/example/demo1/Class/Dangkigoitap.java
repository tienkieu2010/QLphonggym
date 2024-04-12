package com.example.demo1.Class;

import java.util.Date;

public class Dangkigoitap {
    public Hoivien hoivien;
    public Goitap goitap;
    public Date Ngaydangki;
    public Date Ngayhethan;
    Nhanvien nhanvien;

    String Mahoadon;


    public Dangkigoitap(){

    }
    public Dangkigoitap(Hoivien hoivien, Goitap goitap, Date Ngaydangki, Date Ngayhethan, Nhanvien nhanvien, String Mahoadon) {
        this.hoivien = hoivien;
        this.goitap = goitap;
        this.Ngaydangki = Ngaydangki;
        this.Ngayhethan = Ngayhethan;
        this.nhanvien = nhanvien;
        this.Mahoadon = Mahoadon;
    }

    public Hoivien getHoivien() {
        return hoivien;
    }

    public void setHoivien(Hoivien hoivien) {
        this.hoivien = hoivien;
    }

    public Goitap getGoitap() {
        return goitap;
    }

    public void setGoitap(Goitap goitap) {
        this.goitap = goitap;
    }

    public Date getNgaydangki() {
        return Ngaydangki;
    }

    public void setNgaydangki(Date Ngaydangki) {
        this.Ngaydangki = Ngaydangki;
    }

    public Date getNgayhethan() {
        return Ngayhethan;
    }

    public void setNgayhethan(Date Ngayhethan) {
        this.Ngayhethan = Ngayhethan;
    }

    public String getMahoadon() {return Mahoadon;}

    public void setMahoadon(String Mahoadon){this.Mahoadon = Mahoadon ;}

    public Nhanvien getNhanvien(Nhanvien nhanvien){return nhanvien; }

    public void setNhanvien(Nhanvien nhanvien){this.nhanvien = nhanvien;}

    public String getTengoi(){return goitap.tengoi;}

    public String getManhanvien() {
        return nhanvien.Manhanvien;
    }

    public String getMahoivien() {
        return hoivien.Mahoivien;
    }
}
