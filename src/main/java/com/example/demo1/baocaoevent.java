package com.example.demo1;

import com.example.demo1.Class.Goitap;
import com.example.demo1.Class.Hoivien;
import com.example.demo1.Class.Nhanvien;
import com.example.demo1.Class.Thongke;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class baocaoevent implements Initializable {
    @FXML
    private ComboBox<?> Manv_cbb;
    @FXML
    private TextField tktongtien_txt;
    @FXML
    private ComboBox<?> Tkloaithe_cbb;
    @FXML
    TableView<Thongke> Bangthongke_tblv;
    @FXML
    private DatePicker Tungay_dtpk;
    @FXML
    private DatePicker Denngay_dtpk;
    @FXML
    private TextField sothe_txt;
    @FXML
    private TableColumn<Thongke, String> tkloaithe_tbcl;
    @FXML
    private TableColumn<Thongke, String> tksdtkh_tbcl;
    @FXML
    private TableColumn<Thongke, String> tktenhoivien_tbcl;
    @FXML
    private TableColumn<Thongke, Date> tkngaydk_tbcl;
    @FXML
    private TableColumn<Thongke, Integer> tkgiatien_tbcl;
    @FXML
    private TableColumn<Thongke, String> Manv_tbcl;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    /*public void addHoadon(){
        String insertData = "INSERT INTO Hoadon "
                + "(Mahoadon,Ngaymua,Mahv,Manv) "
                + "VALUES(?,?,?,?)";
        connect = ConnectSQL.connectDB();

        try{
            Alert alert;

            if(Hdmahoadon_txt.getText().isEmpty()
                || Hdmahoivien_txt.getText().isEmpty()
                || Hdmanhanvien_cbb.getSelectionModel().getSelectedItem()==null
                || Hdthoigian_dtpk.getValue()==null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập tất cả trường dữ liệu!");
                alert.showAndWait();
            }
            else{

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

     */
    public void addTktengoitaplist() {
        String listtengoitap = "SELECT Tengoi FROM Goitap";
        connect = ConnectSQL.connectDB();
        try {
            ObservableList listG = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listtengoitap);
            result = prepare.executeQuery();
            while (result.next()) {
                listG.add(result.getString("Tengoi"));
            }
            Tkloaithe_cbb.setItems(listG);
            ObservableList<String> comboBoxItems = (ObservableList<String>) Tkloaithe_cbb.getItems();
            comboBoxItems.add("Tất cả");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addBaocaoshowlistdata(ActionEvent event) {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        LocalDate date1 = Tungay_dtpk.getValue();
        LocalDate date2 = Denngay_dtpk.getValue();
        if (Tungay_dtpk.getValue() == null || Denngay_dtpk.getValue() == null || Tkloaithe_cbb.getSelectionModel().getSelectedItem() == null|| Manv_cbb.getSelectionModel().getSelectedItem()==null) {
            alert.setContentText("Vui lòng điền đẩy đủ thông tin vào trường trống");
            alert.show();
        }

        else{
            if (date1.compareTo(date2) > 0) {
                alert.setContentText("Vui lòng sửa sao cho date đầu < date cuối!");
                alert.show();
            } else {
                if(Tkloaithe_cbb.getValue().equals("Tất cả")) {

                    if (Manv_cbb.getValue().equals("Tất cả")) {
                        ObservableList<Thongke> listhongke = FXCollections.observableArrayList();
                        {
                            String ouputdata = "select Goitap.Tengoi, Sdthv, Tenhv, Ngaydangki, Gia, Manv from (((Goitap inner join Hoivien_Goitap on Goitap.Tengoi= Hoivien_Goitap.Tengoi) inner join Hoivien on Hoivien.Mahv = Hoivien_Goitap.Mahv) inner join Hoadon on Hoivien.Mahv = Hoadon.Mahv)  Where (Ngaymua=Ngaydangki and Ngaydangki>=? and Ngaydangki<=?) order by Ngaydangki desc";
                            //int countt=0;
                            connect = ConnectSQL.connectDB();

                            try {
                                prepare = connect.prepareStatement(ouputdata);
                                prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
                                prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
                                //prepare.setString(3, (String) Tkloaithe_cbb.getValue());
                                result = prepare.executeQuery();

                                Thongke thongke;
                                Hoivien hoivien;
                                Goitap goitap;
                                while (result.next()) {
                                    hoivien = new Hoivien();
                                    hoivien.Tenhoivien = result.getString("Tenhv");
                                    hoivien.Sdthv = result.getInt("Sdthv");

                                    goitap = new Goitap();
                                    goitap.tengoi = result.getString("Tengoi");
                                    goitap.Gia = result.getInt("Gia");

                                    Nhanvien nhanvien = new Nhanvien();
                                    nhanvien.Manhanvien = result.getString("Manv");

                                    thongke = new Thongke();
                                    thongke.setGoitap(goitap);
                                    thongke.setHoivien(hoivien);
                                    thongke.setNv(nhanvien);
                                    thongke.setNgaydangki(result.getDate("Ngaydangki"));
                                    listhongke.add(thongke);

                                    //countt= Integer.parseInt(result.getString("count(Tenhv)"));
                                    //sothe_txt.setText(String.valueOf(countt));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        tkloaithe_tbcl.setCellValueFactory(new PropertyValueFactory<>("tengoi"));
                        tktenhoivien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Tenhoivien"));
                        tksdtkh_tbcl.setCellValueFactory(new PropertyValueFactory<>("Sdthv"));
                        tkngaydk_tbcl.setCellValueFactory(new PropertyValueFactory<>("Ngaydangki"));
                        tkgiatien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Gia"));
                        Manv_tbcl.setCellValueFactory(new PropertyValueFactory<>("Manv"));
                        Bangthongke_tblv.setItems(listhongke);
                        countsove3();
                        sumgiatien3();

                    } else {
                        ObservableList<Thongke> listhongke = FXCollections.observableArrayList();
                        {
                            String ouputdata = "select Goitap.Tengoi, Sdthv, Tenhv, Ngaydangki, Gia, Manv from (((Goitap inner join Hoivien_Goitap on Goitap.Tengoi= Hoivien_Goitap.Tengoi) inner join Hoivien on Hoivien.Mahv = Hoivien_Goitap.Mahv) inner join Hoadon on Hoivien.Mahv = Hoadon.Mahv)  Where (Ngaymua=Ngaydangki and Ngaydangki>=? and Ngaydangki<=? and Manv=?) order by Ngaydangki desc";
                            //int countt=0;
                            connect = ConnectSQL.connectDB();

                            try {
                                prepare = connect.prepareStatement(ouputdata);
                                prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
                                prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
                                //prepare.setString(3, (String) Tkloaithe_cbb.getValue());
                                prepare.setString(3, (String) Manv_cbb.getValue());
                                result = prepare.executeQuery();

                                Thongke thongke;
                                Hoivien hoivien;
                                Goitap goitap;
                                while (result.next()) {
                                    hoivien = new Hoivien();
                                    hoivien.Tenhoivien = result.getString("Tenhv");
                                    hoivien.Sdthv = result.getInt("Sdthv");

                                    goitap = new Goitap();
                                    goitap.tengoi = result.getString("Tengoi");
                                    goitap.Gia = result.getInt("Gia");

                                    Nhanvien nhanvien = new Nhanvien();
                                    nhanvien.Manhanvien = result.getString("Manv");

                                    thongke = new Thongke();
                                    thongke.setGoitap(goitap);
                                    thongke.setHoivien(hoivien);
                                    thongke.setNv(nhanvien);
                                    thongke.setNgaydangki(result.getDate("Ngaydangki"));
                                    listhongke.add(thongke);

                                    //countt= Integer.parseInt(result.getString("count(Tenhv)"));
                                    //sothe_txt.setText(String.valueOf(countt));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        tkloaithe_tbcl.setCellValueFactory(new PropertyValueFactory<>("tengoi"));
                        tktenhoivien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Tenhoivien"));
                        tksdtkh_tbcl.setCellValueFactory(new PropertyValueFactory<>("Sdthv"));
                        tkngaydk_tbcl.setCellValueFactory(new PropertyValueFactory<>("Ngaydangki"));
                        tkgiatien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Gia"));
                        Manv_tbcl.setCellValueFactory(new PropertyValueFactory<>("Manv"));
                        Bangthongke_tblv.setItems(listhongke);
                        countsove4();
                        sumgiatien4();
                    }
                }
                else{
                    if (Manv_cbb.getValue().equals("Tất cả")) {
                        ObservableList<Thongke> listhongke = FXCollections.observableArrayList();
                        {
                            String ouputdata = "select Goitap.Tengoi, Sdthv, Tenhv, Ngaydangki, Gia, Manv from (((Goitap inner join Hoivien_Goitap on Goitap.Tengoi= Hoivien_Goitap.Tengoi) inner join Hoivien on Hoivien.Mahv = Hoivien_Goitap.Mahv) inner join Hoadon on Hoivien.Mahv = Hoadon.Mahv)  Where (Ngaymua=Ngaydangki and Ngaydangki>=? and Ngaydangki<=? and Goitap.Tengoi= ?) order by Ngaydangki desc";
                            //int countt=0;
                            connect = ConnectSQL.connectDB();

                            try {
                                prepare = connect.prepareStatement(ouputdata);
                                prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
                                prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
                                prepare.setString(3, (String) Tkloaithe_cbb.getValue());
                                result = prepare.executeQuery();

                                Thongke thongke;
                                Hoivien hoivien;
                                Goitap goitap;
                                while (result.next()) {
                                    hoivien = new Hoivien();
                                    hoivien.Tenhoivien = result.getString("Tenhv");
                                    hoivien.Sdthv = result.getInt("Sdthv");

                                    goitap = new Goitap();
                                    goitap.tengoi = result.getString("Tengoi");
                                    goitap.Gia = result.getInt("Gia");

                                    Nhanvien nhanvien = new Nhanvien();
                                    nhanvien.Manhanvien = result.getString("Manv");

                                    thongke = new Thongke();
                                    thongke.setGoitap(goitap);
                                    thongke.setHoivien(hoivien);
                                    thongke.setNv(nhanvien);
                                    thongke.setNgaydangki(result.getDate("Ngaydangki"));
                                    listhongke.add(thongke);

                                    //countt= Integer.parseInt(result.getString("count(Tenhv)"));
                                    //sothe_txt.setText(String.valueOf(countt));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        tkloaithe_tbcl.setCellValueFactory(new PropertyValueFactory<>("tengoi"));
                        tktenhoivien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Tenhoivien"));
                        tksdtkh_tbcl.setCellValueFactory(new PropertyValueFactory<>("Sdthv"));
                        tkngaydk_tbcl.setCellValueFactory(new PropertyValueFactory<>("Ngaydangki"));
                        tkgiatien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Gia"));
                        Manv_tbcl.setCellValueFactory(new PropertyValueFactory<>("Manv"));
                        Bangthongke_tblv.setItems(listhongke);
                        countsove();
                        sumgiatien();

                    } else {
                        ObservableList<Thongke> listhongke = FXCollections.observableArrayList();
                        {
                            String ouputdata = "select Goitap.Tengoi, Sdthv, Tenhv, Ngaydangki, Gia, Manv from (((Goitap inner join Hoivien_Goitap on Goitap.Tengoi= Hoivien_Goitap.Tengoi) inner join Hoivien on Hoivien.Mahv = Hoivien_Goitap.Mahv) inner join Hoadon on Hoivien.Mahv = Hoadon.Mahv)  Where (Ngaymua=Ngaydangki and Ngaydangki>=? and Ngaydangki<=? and Goitap.Tengoi= ? and Manv=?) order by Ngaydangki desc";
                            //int countt=0;
                            connect = ConnectSQL.connectDB();

                            try {
                                prepare = connect.prepareStatement(ouputdata);
                                prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
                                prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
                                prepare.setString(3, (String) Tkloaithe_cbb.getValue());
                                prepare.setString(4, (String) Manv_cbb.getValue());
                                result = prepare.executeQuery();

                                Thongke thongke;
                                Hoivien hoivien;
                                Goitap goitap;
                                while (result.next()) {
                                    hoivien = new Hoivien();
                                    hoivien.Tenhoivien = result.getString("Tenhv");
                                    hoivien.Sdthv = result.getInt("Sdthv");

                                    goitap = new Goitap();
                                    goitap.tengoi = result.getString("Tengoi");
                                    goitap.Gia = result.getInt("Gia");

                                    Nhanvien nhanvien = new Nhanvien();
                                    nhanvien.Manhanvien = result.getString("Manv");

                                    thongke = new Thongke();
                                    thongke.setGoitap(goitap);
                                    thongke.setHoivien(hoivien);
                                    thongke.setNv(nhanvien);
                                    thongke.setNgaydangki(result.getDate("Ngaydangki"));
                                    listhongke.add(thongke);

                                    //countt= Integer.parseInt(result.getString("count(Tenhv)"));
                                    //sothe_txt.setText(String.valueOf(countt));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        tkloaithe_tbcl.setCellValueFactory(new PropertyValueFactory<>("tengoi"));
                        tktenhoivien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Tenhoivien"));
                        tksdtkh_tbcl.setCellValueFactory(new PropertyValueFactory<>("Sdthv"));
                        tkngaydk_tbcl.setCellValueFactory(new PropertyValueFactory<>("Ngaydangki"));
                        tkgiatien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Gia"));
                        Manv_tbcl.setCellValueFactory(new PropertyValueFactory<>("Manv"));
                        Bangthongke_tblv.setItems(listhongke);
                        countsove2();
                        sumgiatien2();
                    }
                }
            }
        }
    }

    //Tất cả mã nv, tên gói bất kì
    public void countsove() {
        String sql = "select count(Tengoi) as tong from Hoivien_Goitap inner join Hoadon on Hoivien_Goitap.Mahv=Hoadon.Mahv where (Ngaymua = Ngaydangki and Ngaydangki>=? and Ngaydangki<=? and Tengoi=?)";
        int count = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
            prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
            prepare.setString(3, (String) Tkloaithe_cbb.getValue());
            result = prepare.executeQuery();
            if (result.next()) {
                count = result.getInt("tong");
                sothe_txt.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Tất cả mã nv, tên gói bất kì
    public void sumgiatien(){
        String dts = "select Sum(Gia) as tongt from ((Goitap inner join Hoivien_Goitap on Goitap.Tengoi= Hoivien_Goitap.Tengoi) inner join Hoadon on Hoivien_Goitap.Mahv = Hoadon.Mahv) where (Ngaydangki = Ngaymua and Ngaydangki>=? and Ngaydangki<=? and Goitap.Tengoi=?)";
        int countt = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(dts);
            prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
            prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
            prepare.setString(3, (String) Tkloaithe_cbb.getValue());
            result = prepare.executeQuery();
            if (result.next()) {
                countt = result.getInt("tongt");
                tktongtien_txt.setText(String.valueOf(countt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Mã nv bất kì, tên gói bất kì
    public void countsove2() {
        String sql = "select count(Tengoi) as tong from (Hoivien_Goitap inner join Hoadon on Hoivien_Goitap.Mahv=Hoadon.Mahv) where (Ngaymua=Ngaydangki and Ngaydangki>=? and Ngaydangki<=? and Tengoi=? and Manv=?)";
        int count = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
            prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
            prepare.setString(3, (String) Tkloaithe_cbb.getValue());
            prepare.setString(4, (String) Manv_cbb.getValue());
            result = prepare.executeQuery();
            if (result.next()) {
                count = result.getInt("tong");
                sothe_txt.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Mã nv bất kì, Tên gói bất kì.
    public void sumgiatien2(){
        String dts = "select Sum(Gia) as tongt from ((Goitap inner join Hoivien_Goitap on Goitap.Tengoi= Hoivien_Goitap.Tengoi) inner join Hoadon on Hoivien_Goitap.Mahv = Hoadon.Mahv) where (Ngaymua= Ngaydangki and Ngaydangki>=? and Ngaydangki<=? and Goitap.Tengoi=? and Manv=?)";
        int countt = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(dts);
            prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
            prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
            prepare.setString(3, (String) Tkloaithe_cbb.getValue());
            prepare.setString(4, (String) Manv_cbb.getValue());
            result = prepare.executeQuery();
            if (result.next()) {
                countt = result.getInt("tongt");
                tktongtien_txt.setText(String.valueOf(countt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Tất cả Mã nv, Tất cả tên gói
    public void countsove3() {
        String sql = "select count(Tengoi) as tong from (Hoivien_Goitap inner join Hoadon on Hoivien_Goitap.Mahv=Hoadon.Mahv) where (Ngaymua=Ngaydangki and Ngaydangki>=? and Ngaydangki<=?)";
        int count = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
            prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
            //prepare.setString(3, (String) Tkloaithe_cbb.getValue());
            //prepare.setString(4, (String) Manv_cbb.getValue());
            result = prepare.executeQuery();
            if (result.next()) {
                count = result.getInt("tong");
                sothe_txt.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Tất cả Mã nv, tất cả tên gói
    public void sumgiatien3(){
        String dts = "select Sum(Gia) as tongt from ((Goitap inner join Hoivien_Goitap on Goitap.Tengoi= Hoivien_Goitap.Tengoi) inner join Hoadon on Hoivien_Goitap.Mahv = Hoadon.Mahv) where (Ngaymua= Ngaydangki and Ngaydangki>=? and Ngaydangki<=?)";
        int countt = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(dts);
            prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
            prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
            //prepare.setString(3, (String) Tkloaithe_cbb.getValue());
            //prepare.setString(4, (String) Manv_cbb.getValue());
            result = prepare.executeQuery();
            if (result.next()) {
                countt = result.getInt("tongt");
                tktongtien_txt.setText(String.valueOf(countt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Mã nv bất kì, Tất cả tên gói
    public void countsove4() {
        String sql = "select count(Tengoi) as tong from (Hoivien_Goitap inner join Hoadon on Hoivien_Goitap.Mahv=Hoadon.Mahv) where (Ngaymua=Ngaydangki and Ngaydangki>=? and Ngaydangki<=? and Manv=?)";
        int count = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
            prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
            //prepare.setString(3, (String) Tkloaithe_cbb.getValue());
            prepare.setString(3, (String) Manv_cbb.getValue());
            result = prepare.executeQuery();
            if (result.next()) {
                count = result.getInt("tong");
                sothe_txt.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Mã nv bất kì, tất cả tên gói
    public void sumgiatien4(){
        String dts = "select Sum(Gia) as tongt from ((Goitap inner join Hoivien_Goitap on Goitap.Tengoi= Hoivien_Goitap.Tengoi) inner join Hoadon on Hoivien_Goitap.Mahv = Hoadon.Mahv) where (Ngaymua= Ngaydangki and Ngaydangki>=? and Ngaydangki<=? and Manv=?)";
        int countt = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(dts);
            prepare.setString(1, String.valueOf(Tungay_dtpk.getValue()));
            prepare.setString(2, String.valueOf(Denngay_dtpk.getValue()));
            //prepare.setString(3, (String) Tkloaithe_cbb.getValue());
            prepare.setString(3, (String) Manv_cbb.getValue());
            result = prepare.executeQuery();
            if (result.next()) {
                countt = result.getInt("tongt");
                tktongtien_txt.setText(String.valueOf(countt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void Lammoi(ActionEvent event){
        //ObservableList<datanull> data= FXCollections.observableArrayList();
        Tkloaithe_cbb.setValue(null);
        Tungay_dtpk.setValue(null);
        Denngay_dtpk.setValue(null);
        Bangthongke_tblv.getItems().clear();
        tktongtien_txt.setText("");
        sothe_txt.setText("");
        Manv_cbb.setValue(null);
    }
/*
    public void addHoivienshowlistdata(ActionEvent event) {
        ObservableList<Thongke> thongkes;
        thongkes = listhongke;
        tkloaithe_tbcl.setCellValueFactory(new PropertyValueFactory<>("tengoi"));
        tktenhoivien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Tenhoivien"));
        tksdtkh_tbcl.setCellValueFactory(new PropertyValueFactory<>("Sdthv"));
        tkngaydk_tbcl.setCellValueFactory(new PropertyValueFactory<>("Ngaydangki"));
        tkgiatien_tbcl.setCellValueFactory(new PropertyValueFactory<>("Gia"));

        Bangthongke_tblv.setItems(thongkes);
    }
 */

    public void addcomboboxnv(){
        String listmanv = "SELECT Manv FROM Nhanvien";
        connect = ConnectSQL.connectDB();
        try {
            ObservableList listG = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listmanv);
            result = prepare.executeQuery();
            while (result.next()) {
                listG.add(result.getString("Manv"));
            }
            Manv_cbb.setItems(listG);

            ObservableList<String> comboBoxItems = (ObservableList<String>) Manv_cbb.getItems();
            comboBoxItems.add("Tất cả");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTktengoitaplist();
        addcomboboxnv();
    }
}
