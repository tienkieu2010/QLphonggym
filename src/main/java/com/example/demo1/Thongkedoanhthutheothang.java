package com.example.demo1;

import com.example.demo1.Class.thongkedoanhthu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
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


public class Thongkedoanhthutheothang implements Initializable {
    /*
    @FXML
    private BarChart<?,?> baocaocotluongkh;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    @FXML
    private ComboBox Songay_cbb;


    int i=1;
    public void baocao()
    {
        String value = (String) Songay_cbb.getValue();
        int limit = Integer.parseInt(value);
        baocaocotluongkh.getData().clear();

        String sql = "SELECT Top (?) CAST(Ngaydangki as date) , COUNT(Mahv) FROM Hoivien_Goitap GROUP BY Ngaydangki ORDER BY Ngaydangki DESC";

        connect = ConnectSQL.connectDB();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            prepare.setObject(1, limit);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            baocaocotluongkh.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addcombobox() {
        ObservableList<String> comboBoxItems = (ObservableList<String>) Songay_cbb.getItems();
        comboBoxItems.add("4");
        comboBoxItems.add("5");
        comboBoxItems.add("6");
        comboBoxItems.add("7");
        comboBoxItems.add("8");
    }
    */

    @FXML
    private TableView<thongkedoanhthu> thongkethang_tblv;
    @FXML
    private TableColumn<thongkedoanhthu,String> thang_tbcl;
    @FXML
    private TableColumn<thongkedoanhthu,Integer> doanhthu_tbcl;
    @FXML
    private BarChart<?,?> baocaodoanhthutt_tc;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    //@FXML
    //private ComboBox Sothang_cbb;


    public void baocaodoanhthutt(){

        //String value = (String) Sothang_cbb.getValue();
        //int limit = Integer.parseInt(value);
        //baocaodoanhthutt_tc.getData().clear();

        String sql = "SELECT Top (10) CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) as Thang ,Sum(Gia) FROM (Hoivien_Goitap inner join Goitap on Hoivien_Goitap.Tengoi = Goitap.Tengoi) GROUP BY CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) ORDER BY Thang DESC";

        connect = ConnectSQL.connectDB();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            //prepare.setObject(1,limit);
            result = prepare.executeQuery();


            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

           // HashSet<XYChart.Data<String, Integer>> uniqueData = new HashSet<>(chart.getData());
            //chart.getData().clear();
            //chart.getData().addAll(uniqueData);

            ObservableList<XYChart.Data<String, Integer>> reversedData = FXCollections.observableArrayList(chart.getData());
            Collections.reverse(reversedData);
            chart.setData(reversedData);

            //String input = String.valueOf(chart);
            //StringBuilder reversed = new StringBuilder(input).reverse();
            //String ouput = String.valueOf(reversed);
            //chart.getData().add(new XYChart.Data<>(1,reversed));
            baocaodoanhthutt_tc.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public ObservableList<thongkedoanhthu> membersDataList() {

        ObservableList<thongkedoanhthu> listData = FXCollections.observableArrayList();
       // String value = (String) Sothang_cbb.getValue();
        //int limit = Integer.parseInt(value);
        //baocaodoanhthutt_tc.getData().clear();
        String sql = "SELECT Top (10) CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) as Thang ,Sum(Gia) FROM (Hoivien_Goitap inner join Goitap on Hoivien_Goitap.Tengoi = Goitap.Tengoi) GROUP BY CONCAT(MONTH(Ngaydangki),'/', YEAR(Ngaydangki)) ORDER BY Thang DESC";

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
        if(Sothang_cbb.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn mục số ngày!");
            alert.showAndWait();
        }
        else {
            if (baocaodoanhthutt_tc.isVisible() == false) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Bạn chưa click để hiện sơ đồ!");
                alert.showAndWait();
            } else {


         */
                membersListData = membersDataList();
                thang_tbcl.setCellValueFactory(new PropertyValueFactory<>("Ngay_thang_nam"));
                doanhthu_tbcl.setCellValueFactory(new PropertyValueFactory<>("Doanhthu"));

                thongkethang_tblv.setItems(membersListData);

                /*
                if (thongkethang_tblv.getScene().getWindow().isShowing()) {
                    baocaodoanhthutt();
                }

                 */
    }

    /*
    public void addcombobox() {
        ObservableList<String> comboBoxItems = (ObservableList<String>) Sothang_cbb.getItems();
        comboBoxItems.add("1");
        comboBoxItems.add("2");
        comboBoxItems.add("3");
        comboBoxItems.add("4");
        comboBoxItems.add("5");
        comboBoxItems.add("6");
        comboBoxItems.add("7");
        comboBoxItems.add("8");
    }


     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //addcombobox();
        baocaodoanhthutt();
    }
}
