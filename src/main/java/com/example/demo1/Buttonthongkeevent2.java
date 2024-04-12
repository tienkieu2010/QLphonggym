package com.example.demo1;


import com.example.demo1.Class.Hoivien;
import com.example.demo1.Class.Thongke;
import com.example.demo1.Class.thongkedoanhthu;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Buttonthongkeevent2 implements Initializable {
    @FXML
    private ComboBox<?> thang_tkkh;
    @FXML
    private ComboBox<?> thang_tkgoi;
    @FXML
    private PieChart goitap_sodo;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private Label soluonghv_lbl;
    @FXML
    private Label soluongkt_lbl;
    @FXML
    private Label soluongnv_lbl;
    @FXML
    private Label soluongtb_lbl;
    @FXML
    private TableColumn<thongkedoanhthu,String> kh_doanhthu;
    @FXML
    private TableColumn<thongkedoanhthu,Integer> dt_doanhthu;
    @FXML
    private TableColumn<thongkedoanhthu,Integer> hang_doanhthu;
    @FXML
    private TableView<thongkedoanhthu> top5kh_tblv;
    @FXML
    private TableView<thongkedoanhthu> thongkegoi_tblv;
    @FXML
    private TableColumn<thongkedoanhthu,String> tengoi_thongke;
    @FXML
    private TableColumn<thongkedoanhthu,Integer> soluong_thongke;
    public void Thongkedoanhthu(ActionEvent event){
        loadStage("thongke2.fxml");
    }
    public void Baocaokh(ActionEvent event){
        loadStage("thongke.fxml");
    }
    public void Thongkethang(ActionEvent event){
        loadStage("thongke3.fxml");
    }
    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("phonggym.png"));
            stage.setTitle("Hệ Thống Quản Lý Phòng Gym");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void consthv(){
        String sql = "select count(Mahv) as tong from Hoivien";
        int count = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                count = result.getInt("tong");
                soluonghv_lbl.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void constkt(){
        String sql = "select count(Tenkhutap) as tong from Khutap";
        int count = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                count = result.getInt("tong");
                soluongkt_lbl.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void constnv(){
        String sql = "select count(Manv) as tong from Nhanvien";
        int count = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                count = result.getInt("tong");
                soluongnv_lbl.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consttb(){
        String sql = "select count(Tenthietbi) as tong from Thietbitap";
        int count = 0;
        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                count = result.getInt("tong");
                soluongtb_lbl.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void thongketop5kht(){
        if(thang_tkkh.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn dữ liệu bạn muốn tìm!");
            alert.showAndWait();
        }
        else {
            top5kh_tblv.getItems().removeAll(top5kh_tblv.getItems());
            thongketop5khthang();
        }
    }

    public ObservableList<thongkedoanhthu> thongketop5kh() {

            ObservableList<thongkedoanhthu> listData = FXCollections.observableArrayList();

            String sql = "select Top(5) Tenhv, Sum(Gia) as doanhthu from ((Hoivien inner join Hoivien_Goitap on Hoivien.Mahv = Hoivien_Goitap.Mahv) inner join Goitap on Hoivien_Goitap.Tengoi = Goitap.Tengoi) where CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) = ? group by Tenhv,Gia order by Sum(Gia) DESC";

            connect = ConnectSQL.connectDB();

            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, (String) thang_tkkh.getValue());
                result = prepare.executeQuery();
                thongkedoanhthu tk;
                while (result.next()) {
                    tk = new thongkedoanhthu();
                    tk.setNgay_thang_nam(result.getString(1));
                    tk.setDoanhthu(result.getInt(2));
                    listData.add(tk);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return listData;
    }

    private ObservableList<thongkedoanhthu> thongke;
    public void thongketop5khthang() {

                thongke = thongketop5kh();
                kh_doanhthu.setCellValueFactory(new PropertyValueFactory<>("Ngay_thang_nam"));
                dt_doanhthu.setCellValueFactory(new PropertyValueFactory<>("Doanhthu"));

                top5kh_tblv.setItems(thongke);
    }



    public void thongkegoitheothang(){
        if(thang_tkgoi.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn dữ liệu bạn muốn tìm!");
            alert.showAndWait();
        }
        else {
            goitap_sodo.getData().removeAll(goitap_sodo.getData());
            thongkegoi1();
        }
    }



    public ObservableList<thongkedoanhthu> thongkegoi() {

        ObservableList<thongkedoanhthu> listData = FXCollections.observableArrayList();

        String sql = "select Tengoi, count(*) as soluong from Hoivien_Goitap where CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) = ? group by Tengoi order by soluong DESC";

        connect = ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, (String) thang_tkgoi.getValue());
            result = prepare.executeQuery();
            thongkedoanhthu tk;
            while (result.next()) {
                tk = new thongkedoanhthu();
                tk.setNgay_thang_nam(result.getString(1));
                tk.setDoanhthu(result.getInt(2));
                listData.add(tk);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }


    ObservableList<thongkedoanhthu> thongkegoi;

    public void thongkegoi1(){
        thongkegoi = thongkegoi();
        tengoi_thongke.setCellValueFactory(new PropertyValueFactory<>("Ngay_thang_nam"));
        soluong_thongke.setCellValueFactory(new PropertyValueFactory<>("Doanhthu"));

        thongkegoi_tblv.setItems(thongkegoi);



        String sql = "select Tengoi, count(*) as soluong from Hoivien_Goitap where (CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki))=?) group by Tengoi order by soluong DESC";

        connect = ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, (String) thang_tkgoi.getValue());
            result = prepare.executeQuery();
            ObservableList<PieChart.Data> pieChartData;
            pieChartData = FXCollections.observableArrayList();
            while(result.next()){
                pieChartData.add(new PieChart.Data(result.getString(1), result.getInt(2)));
                //goitap_sodo.setData(thongkegoi);
            }

            goitap_sodo.getData().addAll(pieChartData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addcomboboxthang(){
        String listthang = "SELECT DISTINCT CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) as Thang FROM Hoivien_Goitap GROUP BY CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) ORDER BY Thang DESC";
        connect = ConnectSQL.connectDB();
        try {
            ObservableList listG = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listthang);
            result = prepare.executeQuery();
            while (result.next()) {
                listG.add(result.getString(1));
            }
            thang_tkgoi.setItems(listG);
        } catch (Exception e) {
            e.printStackTrace();
        }



        String listthang2 = "SELECT DISTINCT CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) as Thang FROM Hoivien_Goitap GROUP BY CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) ORDER BY Thang DESC";
        connect = ConnectSQL.connectDB();
        try {
            ObservableList listG = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listthang2);
            result = prepare.executeQuery();
            while (result.next()) {
                listG.add(result.getString(1));
            }
            thang_tkkh.setItems(listG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        consthv();
        constkt();
        constnv();
        consttb();
        addcomboboxthang();
    }
}