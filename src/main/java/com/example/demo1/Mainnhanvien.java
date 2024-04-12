package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Mainnhanvien implements Initializable {
    //@FXML
    //private Button baocao_btn;

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
    private Button Dangxuat_btn;
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
    private TextField maintennv_txt;
    public static String tennvmain;


    public void settenmainnv(String tennv){
        maintennv_txt.setText(tennv);
    }

    public void Dangxuat(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {
            Dangxuat_btn.getScene().getWindow().hide();
            loadStage("login.fxml");
        }
    }

    public void Doimk(ActionEvent event){
        loadStage("doimatkhau.fxml");
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
    public void swf(ActionEvent event) throws IOException {
        if(event.getSource()==hoivien_btn){
            hoivien_btn.setVisible(true);
            hoivien_btn.setStyle("-fx-background-color: white");
            thietbi_btn.setStyle("-fx-background-color: transparent");
            dangkigoi_btn.setStyle("-fx-background-color: transparent");
            dentap_btn.setStyle("-fx-background-color: transparent");
            khutap_btn.setStyle("-fx-background-color: transparent");
            //baocao_btn.setStyle("-fx-background-color: transparent");
            ChucNang = FXMLLoader.load(getClass().getResource("hoivien.fxml"));
            bdpnv.setCenter(ChucNang);
        }
        if(event.getSource()==thietbi_btn){
            hoivien_btn.setStyle("-fx-background-color: transparent");
            thietbi_btn.setStyle("-fx-background-color: white");
            dangkigoi_btn.setStyle("-fx-background-color: transparent");
            dentap_btn.setStyle("-fx-background-color: transparent");
            khutap_btn.setStyle("-fx-background-color: transparent");
            //baocao_btn.setStyle("-fx-background-color: transparent");

            ChucNang = FXMLLoader.load(getClass().getResource("thietbi.fxml"));
            bdpnv.setCenter(ChucNang);

        }
        if(event.getSource()==dangkigoi_btn){
            hoivien_btn.setStyle("-fx-background-color: transparent");
            thietbi_btn.setStyle("-fx-background-color: transparent");
            dangkigoi_btn.setStyle("-fx-background-color: white");
            dentap_btn.setStyle("-fx-background-color: transparent");
            khutap_btn.setStyle("-fx-background-color: transparent");
            //baocao_btn.setStyle("-fx-background-color: transparent");

            ChucNang = FXMLLoader.load(getClass().getResource("dangkithe.fxml"));
            bdpnv.setCenter(ChucNang);
        }
        if(event.getSource()==dentap_btn){
            hoivien_btn.setStyle("-fx-background-color: transparent");
            thietbi_btn.setStyle("-fx-background-color: transparent");
            dangkigoi_btn.setStyle("-fx-background-color: transparent");
            dentap_btn.setStyle("-fx-background-color: white");
            khutap_btn.setStyle("-fx-background-color: transparent");
            //baocao_btn.setStyle("-fx-background-color: transparent");


            ChucNang = FXMLLoader.load(getClass().getResource("thoigiantap.fxml"));
            bdpnv.setCenter(ChucNang);
        }
        if(event.getSource()==khutap_btn){
            hoivien_btn.setStyle("-fx-background-color: transparent");
            thietbi_btn.setStyle("-fx-background-color: transparent");
            dangkigoi_btn.setStyle("-fx-background-color: transparent");
            dentap_btn.setStyle("-fx-background-color: transparent");
            khutap_btn.setStyle("-fx-background-color: white");
            //baocao_btn.setStyle("-fx-background-color: transparent");

            ChucNang = FXMLLoader.load(getClass().getResource("khutap.fxml"));
            bdpnv.setCenter(ChucNang);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settenmainnv(tennvmain);
    }
}
