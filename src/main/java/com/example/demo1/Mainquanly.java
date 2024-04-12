package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class Mainquanly {

    @FXML
    private Button baocao_btn;
    @FXML
    private Button Dangxuat_btn2;
    @FXML
    private TextField TaiKhoan;
    @FXML
    private PasswordField MatKhau;
    @FXML
    private Button Dangnhap_btn;
    @FXML
    private Button Dangxuat_btn;
    @FXML
    private Button Exit_btn;
    @FXML
    private AnchorPane ChucNang;
    @FXML
    private BorderPane bdpquanly;
    @FXML
    private BorderPane bdpnv;
    @FXML
    private Button hoivien_btn;
    @FXML
    private Button thietbi_btn;
    @FXML
    private Button khutap_btn;
    @FXML
    private Button dangkigoi_btn;
    @FXML
    private Button dentap_btn;
    @FXML
    private Button hoivien2_btn;
    @FXML
    private Button hoadon_btn;
    @FXML
    private Button thietbi2_btn;
    @FXML
    private Button dangkigoi2_btn;
    @FXML
    private Button khutap2_btn;
    @FXML
    private Button dentap2_btn;
    @FXML
    private Button thongke_btn;
    @FXML
    private Button nhanvien_btn;
    @FXML
    private Button baocao2_btn;
    @FXML
    public Label tennv_lbl;
    @FXML
    private Label matkhaull_lbl;

    @FXML
    private TextField tk_txt;
    @FXML
    private TextField sdt_txt;

    @FXML
    private TextField dmktaikhoan_txt;
    @FXML
    private TextField dmksdt_txt;
    @FXML
    private TextField dmkmatkhaucu_txt;
    @FXML
    private TextField dmkmatkhaumoi_txt;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

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

    public void Dangxuat(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {
            Dangxuat_btn2.getScene().getWindow().hide();
            loadStage("login.fxml");
        }
    }
    public void swf2(ActionEvent event) throws IOException {
        if(event.getSource()==hoivien2_btn){
            //hoivien2_btn.setVisible(true);
            hoivien2_btn.setStyle("-fx-background-color: white");
            thietbi2_btn.setStyle("-fx-background-color: transparent");
            dangkigoi2_btn.setStyle("-fx-background-color: transparent");
            dentap2_btn.setStyle("-fx-background-color: transparent");
            khutap2_btn.setStyle("-fx-background-color: transparent");
            thongke_btn.setStyle("-fx-background-color: transparent");
            nhanvien_btn.setStyle("-fx-background-color: transparent");
            baocao2_btn.setStyle("-fx-background-color: transparent");
            ChucNang = FXMLLoader.load(getClass().getResource("hoivien.fxml"));
            bdpquanly.setCenter(ChucNang);
        }
        if(event.getSource()==thietbi2_btn){
            //thietbi2_btn.setVisible(true);
            hoivien2_btn.setStyle("-fx-background-color: transparent");
            thietbi2_btn.setStyle("-fx-background-color: white");
            dangkigoi2_btn.setStyle("-fx-background-color: transparent");
            dentap2_btn.setStyle("-fx-background-color: transparent");
            khutap2_btn.setStyle("-fx-background-color: transparent");
            thongke_btn.setStyle("-fx-background-color: transparent");
            nhanvien_btn.setStyle("-fx-background-color: transparent");
            baocao2_btn.setStyle("-fx-background-color: transparent");
            ChucNang = FXMLLoader.load(getClass().getResource("thietbi.fxml"));
            bdpquanly.setCenter(ChucNang);

        }
        if(event.getSource()==dangkigoi2_btn){
            hoivien2_btn.setStyle("-fx-background-color: transparent");
            thietbi2_btn.setStyle("-fx-background-color: transparent");
            dangkigoi2_btn.setStyle("-fx-background-color: white");
            dentap2_btn.setStyle("-fx-background-color: transparent");
            khutap2_btn.setStyle("-fx-background-color: transparent");
            thongke_btn.setStyle("-fx-background-color: transparent");
            nhanvien_btn.setStyle("-fx-background-color: transparent");
            baocao2_btn.setStyle("-fx-background-color: transparent");
            ChucNang = FXMLLoader.load(getClass().getResource("dangkithe.fxml"));
            bdpquanly.setCenter(ChucNang);
        }
        if(event.getSource()==dentap2_btn){
            hoivien2_btn.setStyle("-fx-background-color: transparent");
            thietbi2_btn.setStyle("-fx-background-color: transparent");
            dangkigoi2_btn.setStyle("-fx-background-color: transparent");
            dentap2_btn.setStyle("-fx-background-color: white");
            khutap2_btn.setStyle("-fx-background-color: transparent");
            thongke_btn.setStyle("-fx-background-color: transparent");
            nhanvien_btn.setStyle("-fx-background-color: transparent");
            baocao2_btn.setStyle("-fx-background-color: transparent");
            ChucNang = FXMLLoader.load(getClass().getResource("thoigiantap.fxml"));
            bdpquanly.setCenter(ChucNang);
        }
        if(event.getSource()==khutap2_btn){
            hoivien2_btn.setStyle("-fx-background-color: transparent");
            thietbi2_btn.setStyle("-fx-background-color: transparent");
            dangkigoi2_btn.setStyle("-fx-background-color: transparent");
            dentap2_btn.setStyle("-fx-background-color: transparent");
            khutap2_btn.setStyle("-fx-background-color: white");
            thongke_btn.setStyle("-fx-background-color: transparent");
            nhanvien_btn.setStyle("-fx-background-color: transparent");
            baocao2_btn.setStyle("-fx-background-color: transparent");
            ChucNang = FXMLLoader.load(getClass().getResource("khutap.fxml"));
            bdpquanly.setCenter(ChucNang);
        }

        if(event.getSource()==thongke_btn){
            hoivien2_btn.setStyle("-fx-background-color: transparent");
            thietbi2_btn.setStyle("-fx-background-color: transparent");
            dangkigoi2_btn.setStyle("-fx-background-color: transparent");
            dentap2_btn.setStyle("-fx-background-color: transparent");
            khutap2_btn.setStyle("-fx-background-color: transparent");
            thongke_btn.setStyle("-fx-background-color: white");
            nhanvien_btn.setStyle("-fx-background-color: transparent");
            baocao2_btn.setStyle("-fx-background-color: transparent");
            ChucNang = FXMLLoader.load(getClass().getResource("mainthongke.fxml"));
            bdpquanly.setCenter(ChucNang);
        }


        if(event.getSource()==nhanvien_btn){
            hoivien2_btn.setStyle("-fx-background-color: transparent");
            thietbi2_btn.setStyle("-fx-background-color: transparent");
            dangkigoi2_btn.setStyle("-fx-background-color: transparent");
            dentap2_btn.setStyle("-fx-background-color: transparent");
            khutap2_btn.setStyle("-fx-background-color: transparent");
            thongke_btn.setStyle("-fx-background-color: transparent");
            nhanvien_btn.setStyle("-fx-background-color: white");
            baocao2_btn.setStyle("-fx-background-color: transparent");
            ChucNang = FXMLLoader.load(getClass().getResource("nhanvien.fxml"));
            bdpquanly.setCenter(ChucNang);
        }

        if(event.getSource()==baocao2_btn){
            hoivien2_btn.setStyle("-fx-background-color: transparent");
            thietbi2_btn.setStyle("-fx-background-color: transparent");
            dangkigoi2_btn.setStyle("-fx-background-color: transparent");
            dentap2_btn.setStyle("-fx-background-color: transparent");
            khutap2_btn.setStyle("-fx-background-color: transparent");
            thongke_btn.setStyle("-fx-background-color: transparent");
            nhanvien_btn.setStyle("-fx-background-color: transparent");
            baocao2_btn.setStyle("-fx-background-color: white");
            ChucNang = FXMLLoader.load(getClass().getResource("baocao1.fxml"));
            bdpquanly.setCenter(ChucNang);
        }

    }
}
