package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Doimatkhauevent implements Initializable {
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


    public void batbuocnhapso() {
        dmksdt_txt.setOnKeyTyped(event -> {
            String input = dmksdt_txt.getText();
            try {
                int value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                dmksdt_txt.clear();
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
    public void Xacnhandoimk(ActionEvent event) {
        String check = "SELECT * FROM Nhanvien Where Tendangnhap=? and Matkhau=? and Sdtnv=?";
        connect = ConnectSQL.connectDB();
        Alert alert;
        if (dmkmatkhaumoi_txt.getText().isEmpty() ||
                dmksdt_txt.getText().isEmpty() ||
                dmktaikhoan_txt.getText().isEmpty() ||
                dmkmatkhaucu_txt.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đủ trường dữ liệu");
            alert.showAndWait();
        } else {

            try {
                prepare = connect.prepareStatement(check);
                prepare.setString(1, dmktaikhoan_txt.getText());
                prepare.setString(2, dmkmatkhaucu_txt.getText());
                prepare.setString(3, dmksdt_txt.getText());
                result = prepare.executeQuery();
                if (result.next()) {
                    String updatedata = "UPDATE Nhanvien SET "
                            + "Matkhau= '" + dmkmatkhaumoi_txt.getText()
                            + "' WHERE Tendangnhap='" + dmktaikhoan_txt.getText()
                            + "' and Sdtnv='" + dmksdt_txt.getText()
                            + "' and Matkhau='" + dmkmatkhaucu_txt.getText() + "'";
                    connect = ConnectSQL.connectDB();
                    try {
                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Bạn có chắc chắn muốn đổi mật khẩu !");
                        Optional<ButtonType> option = alert.showAndWait();
                        if (option.get().equals(ButtonType.OK)) {
                            statement = connect.createStatement();
                            statement.executeUpdate(updatedata);
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Đổi mật khẩu thành công!");
                            alert.showAndWait();
                        } else {
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Thông tin bạn nhập chưa chính xác");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void lammoidmk(ActionEvent event){
        dmkmatkhaucu_txt.setText("");
        dmksdt_txt.setText("");
        dmktaikhoan_txt.setText("");
        dmkmatkhaumoi_txt.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        batbuocnhapso();
    }
}
