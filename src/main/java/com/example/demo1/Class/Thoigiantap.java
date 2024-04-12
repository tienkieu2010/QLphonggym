package com.example.demo1.Class;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Thoigiantap {
    private Hoivien hoivien;
    private Time Thoigianden;
    private Time Thoigianve;
    private Date Ngay;
    private Khutap khutap;

    public Thoigiantap(){

    }

    public Thoigiantap(Hoivien hoivien, Khutap khutap, Time Thoigianden, Time Thoigianve, Date Ngay){
        this.hoivien = hoivien;
        this.khutap = khutap;
        this.Thoigianve = Thoigianve;
        this.Thoigianden = Thoigianden;
        this.Ngay = Ngay;
    }
    public Hoivien gethoivien() {
        return hoivien;
    }
    public void setHoivien(Hoivien hoivien){
        this.hoivien = hoivien;
    }
    public Time getThoigianden(){
        return  Thoigianden;
    }
    public void setThoigianden(Time Thoigianden){
        this.Thoigianden = Thoigianden;
    }
    public Time getThoigianve(){
        return Thoigianve;
    }
    public void setThoigianve(Time Thoigianve){
        this.Thoigianve = Thoigianve;
    }
    public Date getNgay(){
        return Ngay;
    }

    public void setNgay(Date ngay) {
        this.Ngay = ngay;
    }
    public Khutap getKhutap(){
        return khutap;
    }
    public void setKhutap(Khutap khutap){
        this.khutap = khutap;
    }
    public String getMahoivien() {
        return hoivien.Mahoivien;
    }
    public String getTenkhutap() {
        return khutap.Tenkhutap;
    }
}