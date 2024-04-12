package com.example.demo1;

import com.example.demo1.Class.thongkedoanhthu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Date;

public class Thongkedoanhthutheongay implements Initializable {
    @FXML
    private TableView<thongkedoanhthu> thongkengay_tblv;
    @FXML
    private TableColumn<thongkedoanhthu,Date> Ngay_tbcl;
    @FXML
    private TableColumn<thongkedoanhthu,Integer> doanhthu_tbcl;
    @FXML
    private LineChart<?,?> baocaodoanhthu_tc;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    //@FXML
    //private ComboBox Songay2_cbb;


    public void baocaodoanhthu(){

        //String value = (String) Songay2_cbb.getValue();
        //int limit = Integer.parseInt(value);
        //baocaodoanhthu_tc.getData().clear();

        //String sql = "SELECT Top (?) FORMAT(CAST(Ngaydangki as date),'dd/MM/yyyy') ,Sum(Gia) FROM (Hoivien_Goitap inner join Goitap on Hoivien_Goitap.Tengoi = Goitap.Tengoi) GROUP BY Ngaydangki ORDER BY Ngaydangki DESC";

        String sql = "SELECT Top (10) FORMAT(CAST(Ngaydangki as date),'dd/MM/yyyy') ,Sum(Gia) FROM (Hoivien_Goitap inner join Goitap on Hoivien_Goitap.Tengoi = Goitap.Tengoi) GROUP BY Ngaydangki ORDER BY Ngaydangki DESC";
        connect = ConnectSQL.connectDB();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            //prepare.setObject(1,limit);
            result = prepare.executeQuery();


            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));

            }

            ObservableList<XYChart.Data<String, Integer>> reversedData = FXCollections.observableArrayList(chart.getData());
            Collections.reverse(reversedData);
            chart.setData(reversedData);


            //String input = String.valueOf(chart);
            //StringBuilder reversed = new StringBuilder(input).reverse();
            //String ouput = String.valueOf(reversed);
            //chart.getData().add(new XYChart.Data<>(1,reversed));
            baocaodoanhthu_tc.getData().add(chart);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ObservableList<thongkedoanhthu> membersDataList() {

        ObservableList<thongkedoanhthu> listData = FXCollections.observableArrayList();
        //String value = (String) Songay2_cbb.getValue();
        //int limit = Integer.parseInt(value);
        //baocaodoanhthu_tc.getData().clear();

        //String sql = "SELECT Top (?) FORMAT(CAST(Ngaydangki as date),'dd/MM/yyyy') ,Sum(Gia) FROM (Hoivien_Goitap inner join Goitap on Hoivien_Goitap.Tengoi = Goitap.Tengoi) GROUP BY Ngaydangki ORDER BY Ngaydangki DESC";

        String sql = "SELECT Top (10) FORMAT(CAST(Ngaydangki as date),'dd/MM/yyyy') ,Sum(Gia) FROM (Hoivien_Goitap inner join Goitap on Hoivien_Goitap.Tengoi = Goitap.Tengoi) GROUP BY Ngaydangki ORDER BY Ngaydangki DESC";
        connect = ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            //prepare.setObject(1, limit);
            result = prepare.executeQuery();

            thongkedoanhthu tk;

            while (result.next()) {
                tk = new thongkedoanhthu(result.getString(1),result.getInt(2));
                listData.add(tk);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<thongkedoanhthu> membersListData;
    public void membersShowData() {
        /*
        if(Songay2_cbb.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn mục số ngày!");
            alert.showAndWait();
        }
        else{
            if(baocaodoanhthu_tc.isVisible()==false){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Bạn chưa click để hiện sơ đồ!");
                alert.showAndWait();
            }
            else{

         */
                membersListData = membersDataList();
                Ngay_tbcl.setCellValueFactory(new PropertyValueFactory<>("Ngay_thang_nam"));
                doanhthu_tbcl.setCellValueFactory(new PropertyValueFactory<>("Doanhthu"));

                thongkengay_tblv.setItems(membersListData);

                /*
                if(thongkengay_tblv.getScene().getWindow().isShowing()){
                    baocaodoanhthu();
                }

                 */
        //membersListData = membersDataList();
        //Ngay_tbcl.setCellValueFactory(new PropertyValueFactory<>("Ngay_thang_nam"));
        //doanhthu_tbcl.setCellValueFactory(new PropertyValueFactory<>("Doanhthu"));

        //thongkengay_tblv.setItems(membersListData);

        //if(thongkengay_tblv.getScene().getWindow().isShowing()){
          //  baocaodoanhthu();
        //}
    }


    /*
    public void addcombobox() {
        ObservableList<String> comboBoxItems = (ObservableList<String>) Songay2_cbb.getItems();
        comboBoxItems.add("4");
        comboBoxItems.add("5");
        comboBoxItems.add("6");
        comboBoxItems.add("7");
        comboBoxItems.add("8");
        //Songay2_cbb.setValue("4");
    }


     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // addcombobox();
        //membersShowData();
        baocaodoanhthu();
    }
}
