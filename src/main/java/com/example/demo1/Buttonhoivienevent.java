package com.example.demo1;

import com.example.demo1.Class.Hoivien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Buttonhoivienevent implements Initializable {
    @FXML
    private TableView<Hoivien> Hoivien_tblv;
    @FXML
    private TextField hc_tenhoivien_txt;

    @FXML
    private TableColumn<Hoivien,String> hv_mahoivien_tblcl;
    @FXML
    private TableColumn<Hoivien,String> hv_tenhoivien_tblcl;
    @FXML
    private TableColumn<Hoivien,String> hv_diachi_tblcl;
    @FXML
    private TableColumn<Hoivien,Integer> hv_gioitinh_tblcl;
    @FXML
    private TableColumn<Hoivien,Integer> hv_sdt_tblcl;
    @FXML
    private TableColumn<Hoivien, Integer> hv_namsinh_tblcl;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    public ObservableList<Hoivien> listhoivien = FXCollections.observableArrayList();

    {
        String sql = "SELECT * FROM Hoivien";
        connect = ConnectSQL.connectDB();
        try{
            Hoivien hoivien;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                hoivien = new Hoivien(result.getString("Mahv"),
                        result.getString("Tenhv"),
                        result.getString("Diachi"),
                        result.getInt("Gioitinh"),
                        result.getInt("Namsinh"),
                        result.getInt("Sdthv")
                        );
                listhoivien.add(hoivien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Hoivien> addhoivien;
    public void addHoivienshowlistdata(){
        addhoivien = listhoivien;
        hv_mahoivien_tblcl.setCellValueFactory(new PropertyValueFactory<>("Mahoivien"));
        hv_tenhoivien_tblcl.setCellValueFactory(new PropertyValueFactory<>("Tenhoivien"));
        hv_diachi_tblcl.setCellValueFactory(new PropertyValueFactory<>("Diachi"));
        hv_gioitinh_tblcl.setCellValueFactory(new PropertyValueFactory<>("Gioitinh"));
        hv_namsinh_tblcl.setCellValueFactory(new PropertyValueFactory<>("Namsinh"));
        hv_sdt_tblcl.setCellValueFactory(new PropertyValueFactory<>("Sdthv"));

        Hoivien_tblv.setItems(addhoivien);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addHoivienshowlistdata();
    }
}
