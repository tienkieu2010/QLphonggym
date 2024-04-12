package com.example.demo1.Class;

import java.util.Date;

public class Thongke extends Dangkigoitap {
    Nhanvien nv;

    public Thongke(){
        super();
    }
    public Thongke(Hoivien hoivien, Goitap goitap, Date Ngaydangki, Date Ngayhethan, Nhanvien nv,String Mahoadon) {
        super(hoivien, goitap, Ngaydangki, Ngayhethan,nv,Mahoadon);
    }
    public String getTengoi() {
        return super.getGoitap().getTengoi();
    }

    public int getGia() {
        return super.getGoitap().getGia();
    }

    public String getTenhoivien() {
        return super.getHoivien().getTenhoivien();
    }
    public int getSdthv(){
        return super.getHoivien().getSdthv();
    }

    public String getManv(){
        return nv.getManhanvien();
    }

    public void setNv(Nhanvien nv){
        this.nv=nv;
    }
    public Nhanvien getNv(){
        return nv;
    }

    public Date getNgaydangki(){
        return super.getNgaydangki();
    }
}
