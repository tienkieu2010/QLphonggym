package com.example.demo1.Class;
import java.util.Date;

public class thongkedoanhthu {
    String Ngay_thang_nam;
    int Doanhthu;

    public thongkedoanhthu(){

    }
    public thongkedoanhthu(String Ngay_thang_nam, int Doanhthu)
    {
        this.Ngay_thang_nam=Ngay_thang_nam;
        this.Doanhthu=Doanhthu;
    }

    public void setNgay_thang_nam(String Ngay_thang_nam){
        this.Ngay_thang_nam=Ngay_thang_nam;
    }
    public String getNgay_thang_nam(){
        return Ngay_thang_nam;
    }

    public void setDoanhthu(int Doanhthu){
        this.Doanhthu = Doanhthu;
    }

    public int getDoanhthu(){
        return Doanhthu;
    }
}
