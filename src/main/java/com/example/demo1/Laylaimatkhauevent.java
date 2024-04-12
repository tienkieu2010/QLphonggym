package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Laylaimatkhauevent implements Initializable {

    @FXML
    public Label tennv_lbl;
    @FXML
    private Label matkhaull_lbl;

    @FXML
    private TextField tk_txt;
    @FXML
    private TextField sdt_txt;
    @FXML
    private Button Exit_btn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void batbuocnhapso() {
        sdt_txt.setOnKeyTyped(event -> {
            String input = sdt_txt.getText();
            try {
                int value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                sdt_txt.clear();
                showAlert("Lỗi","Vui lòng nhập kiểu số!" );
            }
        });
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void Xacnhan(ActionEvent event){
        String sql = "SELECT Tendangnhap,Sdtnv,Matkhau FROM Nhanvien where Tendangnhap=? and  Sdtnv=?";
        connect = ConnectSQL.connectDB();
        try {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (tk_txt.getText().isEmpty() || sdt_txt.getText().isEmpty()) {
                alert.setContentText("Vui lòng điền tất cả vào các trường trống!");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, tk_txt.getText());
                prepare.setString(2, sdt_txt.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    matkhaull_lbl.setText("Mật khẩu của bạn là: " + result.getString("Matkhau"));
                } else {
                    alert.setContentText("Thông tin bạn nhập không chính xác!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Lammoi(ActionEvent event){
        tk_txt.setText("");
        sdt_txt.setText("");
        matkhaull_lbl.setText("");
    }

    public void Exit(ActionEvent event){
        Exit_btn.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        batbuocnhapso();
    }
}
