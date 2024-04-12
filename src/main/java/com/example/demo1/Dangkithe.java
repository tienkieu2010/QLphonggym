package com.example.demo1;

import com.example.demo1.Class.Dangkigoitap;
import com.example.demo1.Class.Goitap;
import com.example.demo1.Class.Hoivien;
import com.example.demo1.Class.Nhanvien;
import com.example.demo1.ConnectSQL;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dangkithe implements Initializable {
    @FXML
    private TextField Mahvtk;
    @FXML
    private TextField Mahv;
    @FXML
    private ComboBox<String> Manv;
    @FXML
    private ComboBox<String> Tengoi;
    @FXML
    private DatePicker Ngaydangki;
    @FXML
    private DatePicker Ngayhethan;
    @FXML
    private TextField Mahoadon;
    @FXML
    private Button Them;
    @FXML
    private Button Lammoi;
    @FXML
    private Button Xoa;
    @FXML
    private Button Timkiem;
    @FXML
    private TableColumn<Dangkithe, String> tbMahv;
    @FXML
    private TableColumn<Dangkithe, String> tbNgaydangki;
    @FXML
    private TableColumn<Dangkithe, String>tbMahoadon;
    @FXML
    private TableColumn<Dangkithe, String> tbNgayhethan;
    @FXML
    private TableColumn<Dangkithe, String> tbTengoi;
    @FXML
    private TableColumn<Dangkithe, String> tbManv;
    @FXML
    private TableView<Dangkigoitap> viewTable;

    private ObservableList<Dangkigoitap> membersListData;
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

    public void Manv() {
        try {
            Connection connection = ConnectSQL.connectDB();
            String query = "SELECT DISTINCT Manv FROM Nhanvien";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String manv = resultSet.getString("Manv");
                Manv.getItems().add(manv);
            }

            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void Tengoi() {
        try {
            Connection connection = ConnectSQL.connectDB();
            String query = "SELECT DISTINCT Tengoi FROM Goitap";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String tengoi = resultSet.getString("Tengoi");
                Tengoi.getItems().add(tengoi);
            }

            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private boolean kiemTraTonTaiMahd(String mahd) {
        String sql = "SELECT COUNT(*) FROM Hoadon WHERE Mahoadon = ?";
        ConnectSQL connect = new ConnectSQL();
        try {
            PreparedStatement preparedStatement = connect.connectDB().prepareStatement(sql);
            preparedStatement.setString(1, mahd);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void Themm(ActionEvent event) {
        LocalDate ngayDangKi = Ngaydangki.getValue();
        LocalDate ngayHetHan = Ngayhethan.getValue();
        String mahd = Mahoadon.getText();

        if(Mahoadon.getText().isEmpty()|| Mahv.getText().isEmpty() || Manv.getValue().isEmpty()|| Ngayhethan.getValue()==null || Ngaydangki.getValue()==null || Tengoi.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin");
            alert.showAndWait();
        }
        else{
            if (kiemTraTonTaiMahd(mahd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Mã hóa đơn đã tồn tại trong cơ sở dữ liệu.");
                alert.showAndWait();
                return;
            }

            // Kiểm tra ngày đăng ký không lớn hơn ngày hết hạn
            if (ngayDangKi != null && ngayHetHan != null && ngayDangKi.isAfter(ngayHetHan)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Ngày đăng ký không được lớn hơn ngày hết hạn.");
                alert.showAndWait();
                return;
            }

            // Kiểm tra sự tồn tại của Mahv trong cơ sở dữ liệu
            String checkMahvExistQuery = "SELECT * FROM Hoivien WHERE Mahv = ?";
            try {
                prepare = connect.prepareStatement(checkMahvExistQuery);
                prepare.setString(1, Mahv.getText());
                ResultSet result = prepare.executeQuery();
                if (!result.next()) {
                    // Mahv không tồn tại trong cơ sở dữ liệu
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Mã hội viên bạn vừa nhập không tồn tại trong danh sách hội viên!");
                    alert.showAndWait();
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sql = "INSERT INTO Hoivien_Goitap (Mahv, Tengoi, Ngaydangki, Ngayhethan) VALUES (?, ?, ?, ?)";
            String sql2 = "INSERT INTO Hoadon (Mahoadon, Ngaymua, Mahv, Manv) " +
                    "VALUES (?, ?, ?, ?)";

            ConnectSQL connect = new ConnectSQL();
            try {
                PreparedStatement preparedStatement = connect.connectDB().prepareStatement(sql);
                preparedStatement.setString(1, Mahv.getText());
                preparedStatement.setString(2, Tengoi.getSelectionModel().getSelectedItem());
                preparedStatement.setString(3, String.valueOf(Ngaydangki.getValue()));
                preparedStatement.setString(4, String.valueOf(Ngayhethan.getValue()));
                preparedStatement.executeUpdate();

                PreparedStatement preparedStatement2 = connect.connectDB().prepareStatement(sql2);
                preparedStatement2.setString(1, Mahoadon.getText());
                preparedStatement2.setString(2, String.valueOf(Ngaydangki.getValue()));
                preparedStatement2.setString(3, Mahv.getText());
                preparedStatement2.setString(4, Manv.getSelectionModel().getSelectedItem());
                preparedStatement2.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công");
                alert.showAndWait();
                membersShowData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getSoThang(String goiTap) {
        switch (goiTap) {
            case "1 ngày":
                return 1;
            case "1 tháng":
                return 30;
            case "3 tháng":
                return 90; // Thay đổi giá trị thành 90
            default:
                return 0;
        }
    }


    public ObservableList<Dangkigoitap> membersDataList() {
        ObservableList<Dangkigoitap> listData = FXCollections.observableArrayList();

        String sql = "select Mahoadon,Hoivien.Mahv,Hoivien_Goitap.Tengoi,Ngaydangki,Ngayhethan,Nhanvien.Manv\n" +
                "from (Hoivien inner join Hoivien_Goitap on Hoivien.Mahv = Hoivien_Goitap.Mahv)\t\n" +
                "\t\tinner join Hoadon on Hoivien.Mahv = Hoadon.Mahv\n" +
                "\t\tinner join Nhanvien on Hoadon.Manv = Nhanvien.Manv\n" +
                "Where Ngaydangki=Ngaymua\n"+
                "Group by Mahoadon,Hoivien.Mahv,Hoivien_Goitap.Tengoi,Ngaydangki,Ngayhethan,Nhanvien.Manv order by Ngaydangki desc";

        connect = ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                Goitap gt = new Goitap();
                gt.setTengoi(result.getString("Tengoi"));
                Nhanvien nv = new Nhanvien();
                nv.setManhanvien(result.getString("Manv"));
                Hoivien hv = new Hoivien();
                hv.setMahoivien(result.getString("Mahv"));
                Dangkigoitap dk = new Dangkigoitap();
                dk.setGoitap(gt);
                dk.setHoivien(hv);
                dk.setNhanvien(nv);
                dk.setNgaydangki(result.getDate("Ngaydangki"));
                dk.setNgayhethan(result.getDate("Ngayhethan"));
                dk.setMahoadon(result.getString("Mahoadon"));

                listData.add(dk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void membersShowData() {
        membersListData = membersDataList();

        tbMahv.setCellValueFactory(new PropertyValueFactory<>("Mahoivien"));
        tbTengoi.setCellValueFactory(new PropertyValueFactory<>("Tengoi"));
        tbNgaydangki.setCellValueFactory(new PropertyValueFactory<>("Ngaydangki"));
        tbNgayhethan.setCellValueFactory(new PropertyValueFactory<>("Ngayhethan"));
        tbMahoadon.setCellValueFactory(new PropertyValueFactory<>("Mahoadon"));
        tbManv.setCellValueFactory(new PropertyValueFactory<>("Manhanvien"));

        viewTable.setItems(membersListData);
    }

    public ObservableList<Dangkigoitap> membersDataList23( ) {
        ObservableList<Dangkigoitap> listData = FXCollections.observableArrayList();

        String sql = "select Mahoadon,Hoivien.Mahv,Hoivien_Goitap.Tengoi,Ngaydangki,Ngayhethan,Nhanvien.Manv\n" +
                "from (Hoivien inner join Hoivien_Goitap on Hoivien.Mahv = Hoivien_Goitap.Mahv)\t\n" +
                "\t\tinner join Hoadon on Hoivien.Mahv = Hoadon.Mahv\n" +
                "\t\tinner join Nhanvien on Hoadon.Manv = Nhanvien.Manv \n" +
                "Where Ngaydangki=Ngaymua and Hoivien.Mahv = ?\n"+
                "Group by Mahoadon,Hoivien.Mahv,Hoivien_Goitap.Tengoi,Ngaydangki,Ngayhethan,Nhanvien.Manv";

        connect = ConnectSQL.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,Mahvtk.getText());
            result = prepare.executeQuery();

            Dangkigoitap md ;

            while (result.next()) {
                Goitap gt = new Goitap();
                gt.setTengoi(result.getString("Tengoi"));
                Nhanvien nv = new Nhanvien();
                nv.setManhanvien(result.getString("Manv"));
                Hoivien hv = new Hoivien();
                hv.setMahoivien(result.getString("Mahv"));
                Dangkigoitap dk = new Dangkigoitap();
                dk.setGoitap(gt);
                dk.setHoivien(hv);
                dk.setNhanvien(nv);
                dk.setNgaydangki(result.getDate("Ngaydangki"));
                dk.setNgayhethan(result.getDate("Ngayhethan"));
                dk.setMahoadon(result.getString("Mahoadon"));

                listData.add(dk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<Dangkigoitap>memberListdata2;
    public void membersShowData2( ) {
        if(Mahvtk.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nhập mã hv trước khi bấm tìm kiếm!");
            alert.showAndWait();
        }
        else {
            memberListdata2 = membersDataList23();

            tbMahv.setCellValueFactory(new PropertyValueFactory<>("Mahoivien"));
            tbTengoi.setCellValueFactory(new PropertyValueFactory<>("Tengoi"));
            tbNgaydangki.setCellValueFactory(new PropertyValueFactory<>("Ngaydangki"));
            tbNgayhethan.setCellValueFactory(new PropertyValueFactory<>("Ngayhethan"));
            tbMahoadon.setCellValueFactory(new PropertyValueFactory<>("Mahoadon"));
            tbManv.setCellValueFactory(new PropertyValueFactory<>("Manhanvien"));

            viewTable.setItems(memberListdata2);
        }
    }

    public void membersClear() {
        Mahoadon.setText("");
        Mahv.setText("");
        Tengoi.getSelectionModel().getSelectedItem();
        Ngaydangki.setValue(null);
        Ngayhethan.setValue(null);
        Manv.getSelectionModel().getSelectedItem();

    }


    public void membersSelect() {

        Dangkigoitap dk= viewTable.getSelectionModel().getSelectedItem();
        int num = viewTable.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        tbMahoadon.setText(dk.getMahoadon());
        tbManv.setText(dk.getManhanvien());
        tbTengoi.setText(dk.getTengoi());
        tbNgaydangki.setText(String.valueOf(dk.getNgaydangki()));
        tbNgayhethan.setText(String.valueOf(dk.getNgayhethan()));
        tbMahv.setText(dk.getMahoivien());



    }

    public void setTableViewClickListener() {
        viewTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                Dangkigoitap selectedItem = viewTable.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    Mahv.setText(selectedItem.getMahoivien());
                    Tengoi.getSelectionModel().select(selectedItem.getTengoi().toString());
                    Manv.getSelectionModel().select(selectedItem.getManhanvien().toString());
                    Ngaydangki.setValue(LocalDate.parse(selectedItem.getNgaydangki().toString()));
                    Ngayhethan.setValue(LocalDate.parse(selectedItem.getNgayhethan().toString()));
                    Mahoadon.setText(selectedItem.getMahoadon());
                }
            }
        });
    }
    public void Lammoi(){
        membersShowData();
        viewTable.refresh();
        Mahv.setText("");
        Manv.getSelectionModel().clearSelection();
        Tengoi.getSelectionModel().clearSelection();
        Ngaydangki.setValue(null);
        Ngayhethan.setValue(null);
        Mahoadon.setText("");

    }
    public void xoa() {
        String sql = "DELETE FROM Hoadon WHERE Mahoadon = ?";
        String sql2 = "DELETE FROM Hoivien_Goitap where Mahv = ? and Ngaydangki = ?";
        connect = ConnectSQL.connectDB();

        try {
            Alert alert;

            if (Mahoadon.getText().isEmpty()) {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận xóa");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn XÓA Mã hóa đơn: " + Mahoadon.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                    String selectedMahoadon = Mahoadon.getText();
                    String selectedMahv = Mahv.getText();
                    String selectedNgaydangki = String.valueOf(Ngaydangki.getValue());
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, selectedMahoadon);
                    prepare.executeUpdate();
                    prepare = connect.prepareStatement(sql2);
                    prepare.setString(1,selectedMahv);
                    prepare.setString(2,selectedNgaydangki);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa thành công!");
                    alert.showAndWait();

                    // Xóa dòng trong Tableview
                    Dangkigoitap selectedItem = viewTable.getSelectionModel().getSelectedItem();
                    viewTable.getItems().remove(selectedItem);

                    membersClear();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Hủy xóa!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersShowData();
        membersSelect();
        setTableViewClickListener();
        Manv();
        Tengoi();

        Ngaydangki.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate ngayDangKi = Ngaydangki.getValue();
            String selectedGoiTap = Tengoi.getSelectionModel().getSelectedItem();
            if (ngayDangKi != null && selectedGoiTap != null) {
                int soThang = getSoThang(selectedGoiTap);
                LocalDate ngayHetHan = ngayDangKi;
                if (soThang == 1) {
                    ngayHetHan = ngayDangKi.plusDays(1); // Thêm 1 ngày nếu là "1 ngày"
                } else if (soThang == 30) {
                    ngayHetHan = ngayDangKi.plusMonths(1); // Thêm 1 tháng nếu là "1 tháng"
                } else if (soThang == 90) {
                    ngayHetHan = ngayDangKi.plusMonths(3); // Thêm 3 tháng nếu là "3 tháng"
                }
                Ngayhethan.setValue(ngayHetHan);
            }
        });

        Tengoi.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate ngayDangKi = Ngaydangki.getValue();
            String selectedGoiTap = Tengoi.getSelectionModel().getSelectedItem();
            if (ngayDangKi != null && selectedGoiTap != null) {
                int soThang = getSoThang(selectedGoiTap);
                LocalDate ngayHetHan = ngayDangKi;
                if (soThang == 1) {
                    ngayHetHan = ngayDangKi.plusDays(1); // Thêm 1 ngày nếu là "1 ngày"
                } else if (soThang == 30) {
                    ngayHetHan = ngayDangKi.plusMonths(1); // Thêm 1 tháng nếu là "1 tháng"
                } else if (soThang == 90) {
                    ngayHetHan = ngayDangKi.plusMonths(3); // Thêm 3 tháng nếu là "3 tháng"
                }
                Ngayhethan.setValue(ngayHetHan);
            }
        });

        Ngayhethan.setOnMouseClicked(event -> Ngayhethan.setValue(null)); // Xóa ngày sau khi chọn

    }

}

