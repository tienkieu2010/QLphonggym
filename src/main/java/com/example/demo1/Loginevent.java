package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Loginevent implements Initializable {
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
    @FXML
    private TextField Matkhau2;

    @FXML
    private ImageView imagelogin;
    //login


    public void showpass(){
        MatKhau.setVisible(true);
        Matkhau2.setVisible(false);
        imagelogin.setImage(new Image("pass2.png"));
    }

    public void clickpass(){
        //imagelogin.setFocusTraversable(true);
        imagelogin.setOnMouseClicked(event -> {
            //if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                if (MatKhau.isVisible() == true) {
                    String mk = MatKhau.getText().toString();
                    Matkhau2.setText(mk);
                    MatKhau.setVisible(false);
                    Matkhau2.setVisible(true);
                    imagelogin.setImage(new Image("pass1.png"));
                } else {
                    String mk = Matkhau2.getText().toString();
                    MatKhau.setText(mk);
                    Matkhau2.setVisible(false);
                    MatKhau.setVisible(true);
                    imagelogin.setImage(new Image("pass2.png"));
                }
            //}
        });
    }
    public void Login(ActionEvent event){
        String sql = "SELECT Tennv,Tendangnhap,Matkhau FROM Nhanvien where Tendangnhap=? and  Matkhau=?";
        connect = ConnectSQL.connectDB();
        try{
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,TaiKhoan.getText());
            prepare.setString(2,MatKhau.getText());

            result = prepare.executeQuery();

            if (TaiKhoan.getText().isEmpty() || MatKhau.getText().isEmpty()) {
                alert.setContentText("Vui lòng điền tất cả vào các trường trống!");
                alert.showAndWait();
            }
            else {
                if (result.next()) {
                    Mainnhanvien.tennvmain = result.getString("Tennv").toString();
                    alert.setContentText("Đăng nhập thành công! Bạn là " + result.getString("Tennv"));
                    alert.showAndWait();
                    Dangnhap_btn.getScene().getWindow().hide();
                    loadStage("mainnhanvien.fxml");
                }
                else{
                    if(TaiKhoan.getText().equals("admin") && MatKhau.getText().equals("admin")) {
                        alert.setContentText("Đăng nhập thành công! Bạn là admin");
                        alert.showAndWait();
                        Dangnhap_btn.getScene().getWindow().hide();
                        loadStage("mainquanly.fxml");
                    }
                    else {
                        alert.setContentText("Sai tài khoản hoặc mật khẩu !");
                        alert.showAndWait();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Doimatkhau(ActionEvent event){
        loadStage("doimatkhau.fxml");
    }

    public void Quenmatkhau(ActionEvent event){
        loadStage("quenmatkhau.fxml");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        showpass();
        clickpass();
        TaiKhoan.setFocusTraversable(false);
        MatKhau.setFocusTraversable(false);
    }
}
