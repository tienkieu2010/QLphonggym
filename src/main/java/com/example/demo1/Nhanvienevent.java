package com.example.demo1;

import com.example.demo1.Class.Nhanvien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Nhanvienevent implements Initializable {
    @FXML
    private AnchorPane main_form;
    @FXML
    public TextField MaNv;
    @FXML
    private TextField TenNv;
    @FXML
    private TextField Sdt;
    @FXML
    private TextField Taikhoan;
    @FXML
    private TextField Matkhau;
    @FXML
    private Button Nhanvien_thembtn;
    @FXML
    private Button Nhanvien_xoabtn;
    @FXML
    private Button Nhanvien_suabtn;
    @FXML
    private TableView<Nhanvien> nhanvien_tableView;
    @FXML
    private TableColumn<Nhanvien, String> nhanvien_col_manv;
    @FXML
    private TableColumn<Nhanvien, String> nhanvien_col_tennv;
    @FXML
    private TableColumn<Nhanvien, String> nhanvien_col_taikhoan;
    @FXML
    private TableColumn<Nhanvien, String> nhanvien_col_matkhau;
    @FXML
    private TableColumn<Nhanvien, Integer> nhanvien_col_sdt;
    @FXML
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Object ConnectSQL;
    private TableColumn<Object, Object> hoivien_col_tenhoivien;

    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Điền đầy đủ vào ô trống");
        alert.showAndWait();
    }

    public void membersAddbtn() {
        String sql = " INSERT INTO Nhanvien "
                + "VALUES(?,?,?,?,?)";
        connect = com.example.demo1.ConnectSQL.connectDB();
        try {
            Alert alert;
            if (MaNv.getText().isEmpty() || TenNv.getText().isEmpty() || Taikhoan.getText().isEmpty() || Matkhau.getText().isEmpty() || Sdt.getText().isEmpty()) {
                emptyFields();
            } else {
                String checkData = "SELECT Manv FROM Nhanvien WHERE Manv = '"
                        + MaNv.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Mã nhân viên: " + MaNv.getText() + " đã tồn tại!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, MaNv.getText());
                    prepare.setString(2, TenNv.getText());
                    prepare.setString(3, Sdt.getText());
                    prepare.setString(4, Taikhoan.getText());
                    prepare.setString(5, Matkhau.getText());





                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm thành công!");
                    alert.showAndWait();

                    membersShowData();
                    membersClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void membersUpdate() {
        String sql = "UPDATE Nhanvien SET Tennv = ?, Tendangnhap = ?, Sdtnv = ?,  Matkhau = ? WHERE Manv = ?";

        connect = com.example.demo1.ConnectSQL.connectDB();

        try {
            Alert alert;

            if (MaNv.getText().isEmpty()) {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn sửa lại Mã nhân viên " + MaNv.getText() + " ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, TenNv.getText());
                    prepare.setString(2, Taikhoan.getText());
                    prepare.setString(3, Sdt.getText());
                    prepare.setString(4, Matkhau.getText());
                    prepare.setString(5, MaNv.getText());


                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cập nhật thành công!");
                    alert.showAndWait();

                    membersShowData();
                    membersClear();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Hủy cập nhật");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public void membersSearch() {
        String searchValue = MaNv.getText();
        String sql = "SELECT * FROM Nhanvien WHERE Manv = ? OR Tennv LIKE ? or Tendangnhap = ?";

        connect = com.example.demo6.ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, searchValue);
            prepare.setString(2, "%" + searchValue + "%");
            prepare.setString(3,"%"+ searchValue + "%" );
            ResultSet resultSet = prepare.executeQuery();

            if (resultSet.next()) {
                MaNv.setText(resultSet.getString("Manv"));
                TenNv.setText(resultSet.getString("Tennv"));
                Taikhoan.setText(resultSet.getString("Tendangnhap"));
                Matkhau.setText(resultSet.getString("Matkhau"));
                Sdt.setText(resultSet.getString("Sdtnv"));
                membersShowData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy nhân viên!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/



    public void membersDelete() {
        String sql = "DELETE FROM Nhanvien WHERE Manv = ?";
        connect = com.example.demo1.ConnectSQL.connectDB();

        try {
            Alert alert;

            if (MaNv.getText().isEmpty()) {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn xóa nhân viên "+MaNv.getText()+"?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, MaNv.getText());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa thành công!");
                    alert.showAndWait();

                    membersShowData();
                    membersClear();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Hủy xóa!");
                    alert.showAndWait();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void membersClear() {
        membersShowData();
        MaNv.setText("");
        TenNv.setText("");
        Taikhoan.setText("");
        Matkhau.setText("");
        Sdt.setText("");
    }

    private ObservableList<Nhanvien> membersListData;
    public ObservableList<Nhanvien> membersDataList() {
        ObservableList<Nhanvien> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Nhanvien order by Manv asc";

        connect = com.example.demo1.ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Nhanvien md = null;

            while (result.next()) {
                md = new Nhanvien(result.getString("Manv"),
                        result.getString("Tennv"),
                        result.getString("Tendangnhap"),
                        result.getString("Matkhau"),
                        result.getInt("Sdtnv"));

                listData.add(md);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;

    }

    public void membersShowData() {
        membersListData = membersDataList();

        nhanvien_col_manv.setCellValueFactory(new PropertyValueFactory<>("Manhanvien"));
        nhanvien_col_tennv.setCellValueFactory(new PropertyValueFactory<>("Tennhanvien"));
        nhanvien_col_taikhoan.setCellValueFactory(new PropertyValueFactory<>("Taikhoan"));
        nhanvien_col_matkhau.setCellValueFactory(new PropertyValueFactory<>("Matkhau"));
        nhanvien_col_sdt.setCellValueFactory(new PropertyValueFactory<>("Sdt"));

        nhanvien_tableView.setItems(membersListData);
    }

    public ObservableList<Nhanvien> membersDataList2() {
        ObservableList<Nhanvien> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Nhanvien WHERE Manv = ?";

        connect = com.example.demo1.ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, MaNv.getText());

            result = prepare.executeQuery();

            Nhanvien md = null;

            while (result.next()) {
                md = new Nhanvien(result.getString("Manv"),
                        result.getString("Tennv"),
                        result.getString("Tendangnhap"),
                        result.getString("Matkhau"),
                        result.getInt("Sdtnv"));

                listData.add(md);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;

    }
    public void membersShowData2() {
        membersListData = membersDataList2();

        nhanvien_col_manv.setCellValueFactory(new PropertyValueFactory<>("Manhanvien"));
        nhanvien_col_tennv.setCellValueFactory(new PropertyValueFactory<>("Tennhanvien"));
        nhanvien_col_taikhoan.setCellValueFactory(new PropertyValueFactory<>("Taikhoan"));
        nhanvien_col_matkhau.setCellValueFactory(new PropertyValueFactory<>("Matkhau"));
        nhanvien_col_sdt.setCellValueFactory(new PropertyValueFactory<>("Sdt"));

        if (membersListData.isEmpty()) {
            // Không tìm thấy Nhân viên
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Nhân viên không tồn tại!");
            alert.showAndWait();
        } else {
            nhanvien_tableView.setItems(membersListData);
        }
    }



    public void NumbersOnly() {
        Sdt.setOnKeyTyped(event -> {
            String input = Sdt.getText();
            try {
                int value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Sdt.clear();
                showAlert("Lỗi","Số điện thoại không hợp lệ!" );
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
    public void setTableViewClickListener() {
        nhanvien_tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                Nhanvien selectedItem = nhanvien_tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    MaNv.setText(selectedItem.getManhanvien().toString());
                    TenNv.setText(selectedItem.getTennhanvien().toString());
                    Taikhoan.setText(selectedItem.getTaikhoan().toString());
                    Matkhau.setText(selectedItem.getMatkhau().toString());
                    Sdt.setText(String.valueOf(selectedItem.getSdt()));

                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersShowData();
        setTableViewClickListener();
        NumbersOnly();
    }
}
