package com.example.demo1;

import com.example.demo1.Class.Hoivien;
import com.example.demo1.Class.Khutap;
import com.example.demo1.ConnectSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseButton;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Khutapevent implements Initializable {
    @FXML
    private TextField Tenkhutap;
    @FXML
    private TextField LoaihinhTap;
    @FXML
    private ComboBox<String> Trangthai;
    @FXML
    private TableColumn<Khutap, String> tbloaihinhTap;

    @FXML
    private Button Them;
    @FXML
    private Button Sua;
    @FXML
    private Button Xoa;
    @FXML
    private Button Lammoi;
    @FXML
    private ComboBox<String> Manv;
    @FXML
    private TableColumn<Khutap, String> tbTenkhuTap;
    @FXML
    private TableColumn<Khutap, String> tbTrangthai;
    @FXML
    private TableColumn<Khutap, String> tbManv;
    @FXML
    private TableView<Khutap> tableView;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;


    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Điền đầy đủ các ô trống");
        alert.showAndWait();
    }

    public void Them() {
        String sql = "INSERT INTO Khutap (Tenkhutap, Loaihinhtap, Manv, Trangthai) VALUES(?,?,?,?)";
        ConnectSQL connect = new ConnectSQL();

        try {
            Alert alert;

            if (Tenkhutap.getText().isEmpty() || LoaihinhTap.getText().isEmpty() || Manv.getSelectionModel().isEmpty() || Trangthai.getSelectionModel().isEmpty()) {
                emptyFields();
            } else {
                String checkData = "SELECT Tenkhutap FROM Khutap WHERE Tenkhutap = ?";
                //+Tenkhutap.getText()+"''"

                PreparedStatement checkStatement = connect.connectDB().prepareStatement(checkData);
                checkStatement.setString(1, Tenkhutap.getText());
                ResultSet result = checkStatement.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Tên khu tập: " + Tenkhutap.getText() + " đã tồn tại!");
                    alert.showAndWait();
                } else {
                    prepare = connect.connectDB().prepareStatement(sql);
                    prepare.setString(1, Tenkhutap.getText());
                    prepare.setString(2, LoaihinhTap.getText());
                    prepare.setString(3, Manv.getSelectionModel().getSelectedItem());
                    prepare.setString(4, Trangthai.getSelectionModel().getSelectedItem());

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
        String sql = "UPDATE Khutap SET Tenkhutap = ?, Loaihinhtap = ?, Manv = ?, Trangthai = ? WHERE Tenkhutap = ?";

        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Tenkhutap.getText().isEmpty()) {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn CẬP NHẬT ten khu tap: " + Tenkhutap.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);

                    prepare.setString(1, Tenkhutap.getText());
                    prepare.setString(2, LoaihinhTap.getText());
                    prepare.setString(3, Manv.getSelectionModel().getSelectedItem());
                    prepare.setString(4, Trangthai.getSelectionModel().getSelectedItem());
                    prepare.setString(5, Tenkhutap.getText());


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
                    alert.setContentText("Hủy Cập Nhật!!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Khutap> membersDataList() {
        ObservableList<Khutap> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Khutap";
        connect = ConnectSQL.connectDB();
        try {
            Khutap khutap;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                khutap = new Khutap(result.getString("Tenkhutap"),
                        result.getString("Loaihinhtap"),
                        result.getString("Manv"),
                        result.getString("Trangthai"));
                listData.add(khutap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void membersDelete() {
        String sql = "DELETE FROM Khutap WHERE Tenkhutap = ?";

        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Tenkhutap.getText().isEmpty()) {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn XÓA Khu tap: " + Tenkhutap.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, Tenkhutap.getText());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa thành công!");
                    alert.showAndWait();

                    membersShowData();
                    membersClear();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Hủy xóa!!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void membersClears(ActionEvent event) {
        membersClear();
    }

    private void membersClear() {
        Tenkhutap.clear();
        LoaihinhTap.clear();
        Manv.getSelectionModel().clearSelection();
        Trangthai.getSelectionModel().clearSelection();
    }

    private ObservableList<Khutap> membersListData;

    private void membersShowData() {
        membersListData = membersDataList();

        try {
            ObservableList<Khutap> listData = FXCollections.observableArrayList();
            String sql = "SELECT * FROM Khutap";
            connect = ConnectSQL.connectDB();
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            tbTenkhuTap.setCellValueFactory(new PropertyValueFactory<>("Tenkhutap"));
            tbloaihinhTap.setCellValueFactory(new PropertyValueFactory<>("Loaihinhtap"));
            tbTrangthai.setCellValueFactory(new PropertyValueFactory<>("Trangthai"));
            tbManv.setCellValueFactory(new PropertyValueFactory<>("Manv"));


            tableView.setItems(membersListData);
        } catch (SQLException e) {
            e.printStackTrace();
            tableView.setItems(membersListData);
        }
    }

    public void setTableViewClickListener() {
        tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                Khutap selectedItem =tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    Tenkhutap.setText(selectedItem.getTenkhutap());
                    LoaihinhTap.setText(selectedItem.getLoaihinhtap());
                    Trangthai.setValue(String.valueOf(selectedItem.getTrangthai()));
                    Manv.setValue(String.valueOf(selectedItem.getManv()));
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Trangthaii();
        setTableViewClickListener();
        membersShowData();
        nhanvienn();
    }

    public void Trangthaii() {
        /*
        try {
            Connection connection = ConnectSQL.connectDB();
            String query = "SELECT DISTINCT LoaihinhTap FROM Khutap";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String loaiHinhTap = resultSet.getString("LoaihinhTap");
                LoaihinhTap..add(loaiHinhTap);
            }

            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

         */
        Trangthai.setItems(FXCollections.observableArrayList("On dinh", "Bao tri"));
    }

    public void nhanvienn() {
        try {
            Connection connection = ConnectSQL.connectDB();
            String query = "SELECT DISTINCT Manv FROM Nhanvien";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String manvv = resultSet.getString("Manv");
                Manv.getItems().add(manvv);
            }
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}