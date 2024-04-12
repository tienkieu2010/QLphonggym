package com.example.demo1;

import com.example.demo1.Class.Thietbi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class Thietbievent implements Initializable {
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField Tenthietbi;

    @FXML
    private ComboBox Tenkhutap;

    @FXML
    private TextField Soluong;

    @FXML
    private TextField Timkiem;

    @FXML
    private ComboBox Trangthai;

    @FXML
    private Button Thietbi_thembtn;

    @FXML
    private Button Thietbi_suabtn;

    @FXML
    private Button Thietbi_xoabtn;

    @FXML
    private Button Thietbi_timkiembtn;

    @FXML
    private TableView<Thietbi> Thietbi_tableView;

    @FXML
    private TableColumn<Thietbi, String> thietbi_col_tenkhutap;

    @FXML
    private TableColumn<Thietbi, String> thietbi_col_tenthietbi;

    @FXML
    private TableColumn<Thietbi, Boolean> thietbi_col_trangthai;

    @FXML
    private TableColumn<Thietbi, Integer> thietbi_col_soluong;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void batbuocnhapso() {
        Soluong.setOnKeyTyped(event -> {
            String input = Soluong.getText();

            try {
                int value = Integer.parseInt(input);
                if (value < 1) {
                    Soluong.clear();
                    showAlert("Lỗi", "Số lượng phải lớn hơn 1!");
                }
            } catch (NumberFormatException e) {
                Soluong.clear();
                showAlert("Lỗi", "Vui lòng nhập số dương!");
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
    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Điền đầy đủ các ô trống");
        alert.showAndWait();
    }

    public void thietbiAddBtn() {

        String sql = "INSERT INTO Thietbitap(Tenthietbi, Soluong, Trangthai, Tenkhutap)  "
                + "VALUES(?,?,?,?)";

        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Tenthietbi.getText().isEmpty() || Soluong.getText().isEmpty()
                    || Trangthai.getSelectionModel().getSelectedItem() == null
                    || Tenkhutap.getSelectionModel().isEmpty() || Tenkhutap.getSelectionModel().isEmpty())
            {
                emptyFields();
            }
            else {

                String checkData = "SELECT Tenthietbi FROM Thietbitap WHERE Tenthietbi = '"
                        + Tenthietbi.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Thiet bi: " + Tenthietbi.getText() + " đã tồn tại!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, Tenthietbi.getText());
                    prepare.setString(2, Soluong.getText());
                    prepare.setString(3, (String) Trangthai.getSelectionModel().getSelectedItem());
                    prepare.setString(4, (String) Tenkhutap.getValue());

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
        String sql = "UPDATE Thietbitap SET  Soluong = ?, Trangthai = ?, Tenkhutap = ? WHERE Tenthietbi = ?";

        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Tenthietbi.getText().isEmpty() )
            {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muôn CẬP NHẬT thiet bi: " + Tenthietbi.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, Soluong.getText());
                    prepare.setString(2, (String) Trangthai.getSelectionModel().getSelectedItem());
                    prepare.setString(3, (String) Tenkhutap.getValue());
                    prepare.setString(4, Tenthietbi.getText());

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

    public ObservableList<Thietbi> membersDataList() {

        ObservableList<Thietbi> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Thietbitap";

        connect = ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Thietbi md;

            while (result.next()) {
                md = new Thietbi(result.getString("Tenthietbi"),
                        result.getInt("Soluong"),
                        result.getString("Tenkhutap"),
                        result.getString("Trangthai"));


                listData.add(md);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }



    public void membersDelete() {
        String sql = "DELETE FROM Thietbitap WHERE Tenthietbi = ?";

        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Tenthietbi.getText().isEmpty() )
            {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn XÓA Thiet  bi: " + Tenthietbi.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, Tenthietbi.getText());
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

    public void membersSearch() {
        String searchValue = Tenthietbi.getText();
        String sql = "SELECT * FROM Hoivien WHERE Tenthietbi = ? OR Tenkhutap LIKE ?";

        connect = ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, searchValue);
            prepare.setString(2, "%" + searchValue + "%");
            ResultSet resultSet = prepare.executeQuery();

            if (resultSet.next()) {
                Tenthietbi.setText(resultSet.getString("Mahv"));
                Soluong.setText(resultSet.getString("Tenhv"));
                Trangthai.getSelectionModel().select(resultSet.getString("Gioitinh"));
                Tenkhutap.setValue(resultSet.getString("Diachi"));
                membersShowData();



            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thiet bi!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void membersClear() {
        Tenthietbi.setText("");
        Soluong.setText("");
        Trangthai.setValue(null);
        Tenkhutap.setValue("");
        membersShowData();

    }



    private ObservableList<Thietbi> membersListData;

    public void membersShowData() {
        membersListData = membersDataList();

        thietbi_col_tenthietbi.setCellValueFactory(new PropertyValueFactory<>("Tenthietbi"));
        thietbi_col_soluong.setCellValueFactory(new PropertyValueFactory<>("Soluong"));
        thietbi_col_trangthai.setCellValueFactory(new PropertyValueFactory<>("Trangthai"));
        thietbi_col_tenkhutap.setCellValueFactory(new PropertyValueFactory<>("Tenkhutap"));


        Thietbi_tableView.setItems(membersListData);
    }
    public void setTableViewClickListener() {
        Thietbi_tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                Thietbi selectedItem = Thietbi_tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    Tenthietbi.setText(selectedItem.getTenthietbi());
                    Tenkhutap.setValue(selectedItem.getTenkhutap());
                    Soluong.setText(String.valueOf(selectedItem.getSoluong()));
                    Trangthai.getSelectionModel().select(selectedItem.getTrangthai());
                }
            }
        });
    }








    public void addcombobox(){
        ObservableList<String> comboBoxItems =Trangthai.getItems();

// Thêm các mục vào danh sách
        comboBoxItems.add("đang hoạt động");
        comboBoxItems.add(" bảo trì");


    }


    public void addcomboboxkhutap(){
        String listmanv = "SELECT Tenkhutap FROM Khutap";
        connect = ConnectSQL.connectDB();
        try {
            ObservableList listG = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listmanv);
            result = prepare.executeQuery();
            while (result.next()) {
                listG.add(result.getString("TenKhutap"));
            }
            Tenkhutap.setItems(listG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersShowData();
        addcombobox();
        batbuocnhapso();
        addcomboboxkhutap();
        setTableViewClickListener();
    }
}
