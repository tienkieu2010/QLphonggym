package com.example.demo1;


import com.example.demo1.Class.Hoivien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;


import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class Hoivienevent implements Initializable {
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField Mahv;

    @FXML
    private TextField Timkiem;

    @FXML
    private TextField Tenhv;

    @FXML
    private TextField Diachi;

    @FXML
    private TextField Namsinh;

    @FXML
    private TextField Sdthv;

    @FXML
    private ComboBox Gioitinh;

    @FXML
    private ComboBox Timkiem_cbb;

    @FXML
    private Button Hoivien_thembtn;

    @FXML
    private Button Hoivien_suabtn;

    @FXML
    private Button Hoivien_xoabtn;

    @FXML
    private Button Hoivien_timkiembtn;

    @FXML
    private TableView<Hoivien> hoivien_tableView;

    @FXML
    private TableColumn<Hoivien, String> hoivien_col_mahoivien;

    @FXML
    private TableColumn<Hoivien, String> hoivien_col_tenhoivien;

    @FXML
    private TableColumn<Hoivien, String> hoivien_col_diachi;

    @FXML
    private TableColumn<Hoivien, Integer> hoivien_col_gioitinh;

    @FXML
    private TableColumn<Hoivien, Integer> hoivien_col_namsinh;

    @FXML
    private TableColumn<Hoivien, Integer> hoivien_col_sodienthoai;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private String mahoivien;


    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Điền đầy đủ các ô trống");
        alert.showAndWait();
    }


    public void batbuocnhapso() {
        Namsinh.setOnKeyTyped(event -> {
            String input = Namsinh.getText();
            try {
                int value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Namsinh.clear();
                showAlert("Lỗi","Vui lòng nhập kiểu số!" );
            }
        });
    }

    public void batbuocnhapso2() {
        Sdthv.setOnKeyTyped(event -> {
            String input = Sdthv.getText();
            try {
                int value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Sdthv.clear();
                showAlert("Lỗi","Vui lòng nhập kiểu số!" );
            }
        });
    }
    public void batbuocnhapso3() {
        Timkiem.setOnKeyTyped(event -> {
            String input = Sdthv.getText();
            try {
                int value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Sdthv.clear();
                showAlert("Lỗi","Vui lòng nhập kiểu số!" );
            }
        });
    }
    public void batbuocnhapso4() {
        Timkiem.setOnKeyTyped(event -> {
            String input = Timkiem.getText();
            if (Timkiem_cbb.getValue().equals("Số điện thoại")) {
                try {
                    int value = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    Timkiem.clear();
                    showAlert("Lỗi", "Vui lòng nhập kiểu số!");
                }
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

    public void membersAddBtn() {



        String sql = "INSERT INTO Hoivien "
                + "VALUES(?,?,?,?,?,?)";

        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Mahv.getText().isEmpty() || Tenhv.getText().isEmpty()
                    || Sdthv.getText().isEmpty() || Namsinh.getText().isEmpty()
                    || Gioitinh.getSelectionModel().getSelectedItem() == null
                    || Diachi.getText().isEmpty() || Diachi.getText().isEmpty())
            {
                emptyFields();
            } else {

                String checkData = "SELECT Mahv FROM Hoivien WHERE Mahv = '"
                        + Mahv.getText() + "'"  ;


                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Mã hội viên: " + Mahv.getText() +" đã tồn tại!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, Mahv.getText());
                    prepare.setString(2, Tenhv.getText());
                    prepare.setString(3, Namsinh.getText());
                    prepare.setString(4, Gioitinh.getSelectionModel().getSelectedItem().toString());
                    prepare.setString(5, Diachi.getText());
                    prepare.setString(6, Sdthv.getText());







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
        String sql = "UPDATE Hoivien SET Tenhv = ?, Diachi = ?, Sdthv = ?, Gioitinh = ?, Namsinh = ? WHERE Mahv = ?";

        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Mahv.getText().isEmpty() )
            {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muôn CẬP NHẬT Mã hội viên: " + Mahv.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, Tenhv.getText());
                    prepare.setString(2, Diachi.getText());
                    prepare.setString(3, Sdthv.getText());
                    prepare.setInt(4, (Integer) Gioitinh.getValue());
                    prepare.setString(5, Namsinh.getText());
                    prepare.setString(6, Mahv.getText());
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



    public ObservableList<Hoivien> membersDataList() {

        ObservableList<Hoivien> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Hoivien";

        connect = ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Hoivien md = null;

            while (result.next()) {
                md = new Hoivien(result.getString("Mahv"),
                        result.getString("Tenhv"),
                        result.getString("Diachi"),
                        result.getInt("Gioitinh"),
                        result.getInt("Namsinh"),
                        result.getInt("Sdthv"));


                listData.add(md);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void membersDelete() {
        String sql = "DELETE FROM Hoivien WHERE Mahv = ?";

        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Mahv.getText().isEmpty() )
            {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn XÓA Mã hội viên: " + Mahv.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, Mahv.getText());
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



    @FXML
    public void membersSearch() {
        if(Timkiem_cbb.getSelectionModel().isEmpty() || Timkiem.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin");
            alert.showAndWait();
        }
        else {
            String searchValue = Timkiem.getText();
            String searchColumn = "";

            // Xác định cột tìm kiếm dựa trên mục đã chọn trong ComboBox
            String selectedItem = (String) Timkiem_cbb.getSelectionModel().getSelectedItem();
            if (selectedItem.equals("Mã hội viên")) {
                searchColumn = "Mahv";
            } else {
                if (selectedItem.equals("Số điện thoại")) {
                    searchColumn = "Sdthv";
                } else searchColumn = null;
            }


            // Thực hiện truy vấn tìm kiếm
            String sql = "SELECT * FROM Hoivien WHERE " + searchColumn + " = ?";
            try {
                connect = ConnectSQL.connectDB();
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, searchValue);
                ResultSet resultSet = prepare.executeQuery();

                // Xóa bảng trước khi điền dữ liệu kết quả tìm kiếm
                hoivien_tableView.getItems().clear();

                // Thêm kết quả tìm kiếm vào bảng
                boolean found = false; // Biến để kiểm tra xem có tìm thấy kết quả hay khôn

                while (resultSet.next()) {
                    // Trích xuất dữ liệu từ kết quả và thêm vào bảng
                    String Mahv = resultSet.getString("Mahv");
                    String tenhoivien = resultSet.getString("Tenhv");
                    String diachi = resultSet.getString("Diachi");
                    Integer gioitinh = resultSet.getInt("Gioitinh");
                    int namsinh = resultSet.getInt("Namsinh");
                    int sodienthoai = resultSet.getInt("Sdthv");

                    hoivien_tableView.getItems().add(new Hoivien(Mahv, tenhoivien, diachi, gioitinh, namsinh, sodienthoai));
                    found = true; // Đánh dấu là đã tìm thấy kết quả

                    // Kiểm tra kết quả tìm kiếm
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy hội viên", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }







    public void membersClear() {
        Mahv.setText("");
        Tenhv.setText("");
        Sdthv.setText("");
        Namsinh.setText("");
        Gioitinh.setValue(null);
        Diachi.setText("");
        membersShowData();
    }



    private ObservableList<Hoivien> membersListData;

    public void membersShowData() {
        membersListData = membersDataList();

        hoivien_col_mahoivien.setCellValueFactory(new PropertyValueFactory<>("Mahoivien"));
        hoivien_col_tenhoivien.setCellValueFactory(new PropertyValueFactory<>("Tenhoivien"));
        hoivien_col_sodienthoai.setCellValueFactory(new PropertyValueFactory<>("Sdthv"));
        hoivien_col_namsinh.setCellValueFactory(new PropertyValueFactory<>("Namsinh"));
        hoivien_col_gioitinh.setCellValueFactory(new PropertyValueFactory<>("Gioitinh"));
        hoivien_col_diachi.setCellValueFactory(new PropertyValueFactory<>("Diachi"));


        hoivien_tableView.setItems(membersListData);
    }

    public void setTableViewClickListener() {
        hoivien_tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                Hoivien selectedItem = hoivien_tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    Mahv.setText(selectedItem.getMahoivien());
                    Tenhv.setText(selectedItem.getTenhoivien());
                    Sdthv.setText(String.valueOf(selectedItem.getSdthv()));
                    Namsinh.setText(String.valueOf(selectedItem.getNamsinh()));
                    Gioitinh.setValue(selectedItem.getGioitinh());
                    Diachi.setText(selectedItem.getDiachi());
                }
            }
        });
    }



    public void addcombobox(){
        ObservableList<String> comboBoxItems = Gioitinh.getItems();

// Thêm các mục vào danh sách
        comboBoxItems.add(" 1");
        comboBoxItems.add(" 0");


    }




    public ObservableList<Hoivien> membersDataList2() {

        ObservableList<Hoivien> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Hoivien where Sdthv=?";

        connect = ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,Sdthv.getText());
            result = prepare.executeQuery();

            Hoivien md = null;

            while (result.next()) {
                md = new Hoivien(result.getString("Mahv"),
                        result.getString("Tenhv"),
                        result.getString("Diachi"),
                        result.getInt("Gioitinh"),
                        result.getInt("Namsinh"),
                        result.getInt("Sdthv"));


                listData.add(md);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }


    private ObservableList<Hoivien> membersListData2;

    public void membersShowData2() {
        membersListData2 = membersDataList2();

        hoivien_col_mahoivien.setCellValueFactory(new PropertyValueFactory<>("Mahoivien"));
        hoivien_col_tenhoivien.setCellValueFactory(new PropertyValueFactory<>("Tenhoivien"));
        hoivien_col_sodienthoai.setCellValueFactory(new PropertyValueFactory<>("Sdthv"));
        hoivien_col_namsinh.setCellValueFactory(new PropertyValueFactory<>("Namsinh"));
        hoivien_col_gioitinh.setCellValueFactory(new PropertyValueFactory<>("Gioitinh"));
        hoivien_col_diachi.setCellValueFactory(new PropertyValueFactory<>("Diachi"));


        hoivien_tableView.setItems(membersListData2);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersShowData();
        addcombobox();
        batbuocnhapso();
        batbuocnhapso2();
        batbuocnhapso3();
        batbuocnhapso4();
        setTableViewClickListener();
        //addcombobox2();
        Timkiem_cbb.getItems().addAll("Mã hội viên", "Số điện thoại");
        //Timkiem_cbb.getSelectionModel().selectFirst();

        //membersSelect();
    }
}
