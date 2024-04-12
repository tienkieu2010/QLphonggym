package com.example.demo1;


import com.example.demo1.Class.Hoivien;
import com.example.demo1.Class.Khutap;
import com.example.demo1.Class.Thoigiantap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

public class Thoigiandentapevent implements Initializable {
    public Button thoigiantap_addbtn;
    @FXML
    private TextField Mahvtk;
    @FXML
    private DatePicker Ngaytk;
    @FXML
    private AnchorPane mainform;
    @FXML
    private TextField Mahv;
    @FXML
    private TextField Thoigianden;
    @FXML
    private TextField Thoigianve;
    @FXML
    private ComboBox<String> Khutap;
    @FXML
    private DatePicker Ngay;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateTableviewData;

    @FXML
    private TableView<Thoigiantap> thoigiantap_tableView;
    @FXML
    private TableColumn<Thoigiantap, Time> thoigiantap_col_thoigianden;
    @FXML
    private TableColumn <Thoigiantap, String>thoigiantap_col_mahv;
    @FXML
    private TableColumn<Thoigiantap, Time> thoigiantap_col_thoigianve;
    @FXML
    private TableColumn<Thoigiantap, String> thoigiantap_col_khutap;
    @FXML
    private TableColumn<Thoigiantap, Date> thoigiantap_col_ngay;
    @FXML
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Object ConnectSQL;
    private TableColumn<Object, Object> thoigiantap_col_manv;

    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Điền đầy đủ vào ô trống");
        alert.showAndWait();
    }
    public void addBtn() {
        String sql = "INSERT INTO Hoivien_Khutap VALUES(?,?,?,?,?)";

        connect = com.example.demo1.ConnectSQL.connectDB();
        try {
            Alert alert;
            if (Mahv.getText().isEmpty() || Khutap.getItems().isEmpty() || Ngay.getValue() == null ||
                    Thoigianden.getText().isEmpty()) {
                emptyFields();
            } else {
                // Kiểm tra sự tồn tại của Mahv trong cơ sở dữ liệu
                String checkMahvExistQuery = "SELECT * FROM Hoivien WHERE Mahv = ?";
                prepare = connect.prepareStatement(checkMahvExistQuery);
                prepare.setString(1, Mahv.getText());
                result = prepare.executeQuery();

                if (!result.next()) {
                    // Mahv không tồn tại trong cơ sở dữ liệu
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Mã hội viên bạn vừa nhập không tồn tại trong danh sách hội viên!");
                    alert.showAndWait();
                } else {
                    // Mahv tồn tại trong cơ sở dữ liệu, tiếp tục kiểm tra các điều kiện khác
                    try {
                        LocalTime thoigianden = LocalTime.parse(Thoigianden.getText());
                        LocalTime thoigianve = null;

                        // Kiểm tra xem trường Thoigianve có được cung cấp giá trị hay không
                        if (Thoigianve.getText() != null && !Thoigianve.getText().isEmpty()) {
                            thoigianve = LocalTime.parse(Thoigianve.getText());

                            // Kiểm tra định dạng của Thoigianve
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                            String thoigianveFormatted = formatter.format(thoigianve);

                            // Kiểm tra xem giá trị nhập vào có khớp với định dạng đã định nghĩa hay không
                            if (!Thoigianve.getText().equals(thoigianveFormatted)) {
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Định dạng giờ không hợp lệ (Thời gian về). Vui lòng nhập theo định dạng 'giờ:phút:giây' (VD: 09:30:00)");
                                alert.showAndWait();
                                return;
                            }
                        }

                        // Kiểm tra xem giờ đến và giờ về có hợp lệ hay không (nếu được cung cấp)
                        if (thoigianve == null || thoigianve.isAfter(thoigianden)) {
                            String checkData = "SELECT * FROM Hoivien_Khutap WHERE Ngaytap = ? AND Mahv = ?";
                            prepare = connect.prepareStatement(checkData);
                            prepare.setDate(1, Date.valueOf(String.valueOf(Ngay.getValue())));
                            prepare.setString(2, Mahv.getText());
                            result = prepare.executeQuery();

                            if (result.next()) {
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Hội viên này đã đến trong ngày hôm nay rồi");
                                alert.showAndWait();
                            } else {
                                prepare = connect.prepareStatement(sql);
                                prepare.setString(1, Mahv.getText());
                                prepare.setString(2, Khutap.getSelectionModel().getSelectedItem().toString());
                                prepare.setDate(3, Date.valueOf(String.valueOf(Ngay.getValue())));
                                prepare.setTime(4, Time.valueOf(thoigianden));
                                if (thoigianve != null) {
                                    prepare.setTime(5, Time.valueOf(thoigianve));
                                } else {
                                    prepare.setNull(5, Types.TIME);
                                }
                                prepare.executeUpdate();
                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Thêm thành công!");
                                alert.showAndWait();
                            }
                        } else {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Thời gian về phải lớn hơn thời gian đến");
                            alert.showAndWait();
                        }
                    } catch (DateTimeParseException e) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Định dạng giờ không hợp lệ (Thời gian đến). Vui lòng nhập theo định dạng 'giờ:phút:giây' (VD: 09:30:00)");
                        alert.showAndWait();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        membersShowData();
    }










    public ObservableList<Thoigiantap> membersDataList() {
        ObservableList<Thoigiantap> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Hoivien_Khutap GROUP BY Mahv,Tenkhutap,Ngaytap,Thoigiandentap,Thoigianrave ORDER by Ngaytap desc";
        connect = com.example.demo1.ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Thoigiantap md = null;

            while (result.next()) {
                Hoivien hv = new Hoivien();
                hv.setMahoivien(result.getString("Mahv"));
                Khutap kt = new Khutap();
                kt.setTenkhutap(result.getString("Tenkhutap"));
                Thoigiantap thoigian = new Thoigiantap();
                thoigian.setHoivien(hv);
                thoigian.setKhutap(kt);
                thoigian.setNgay(result.getDate("Ngaytap"));
                thoigian.setThoigianden(result.getTime("Thoigiandentap"));
                thoigian.setThoigianve(result.getTime("Thoigianrave"));

                listData.add(thoigian);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;

    }
    private ObservableList<Thoigiantap> membersListData;
    public void membersShowData() {
        membersListData = membersDataList();
        thoigiantap_col_mahv.setCellValueFactory(new PropertyValueFactory<>("Mahoivien"));
        thoigiantap_col_khutap.setCellValueFactory(new PropertyValueFactory<>("Tenkhutap"));
        thoigiantap_col_thoigianden.setCellValueFactory(new PropertyValueFactory<>("Thoigianden"));
        thoigiantap_col_thoigianve.setCellValueFactory(new PropertyValueFactory<>("Thoigianve"));
        thoigiantap_col_ngay.setCellValueFactory(new PropertyValueFactory<>("Ngay"));
        thoigiantap_tableView.setItems(membersListData);
    }

    public ObservableList<Thoigiantap> membersDataList23() {
        ObservableList<Thoigiantap> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Hoivien_Khutap where Mahv = ? and Ngaytap = ?";
        connect = com.example.demo1.ConnectSQL.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, Mahvtk.getText());
            prepare.setString(2, String.valueOf(Ngaytk.getValue()));
            result = prepare.executeQuery();

            Thoigiantap md = null;

            while (result.next()) {
                Hoivien hv = new Hoivien();
                hv.setMahoivien(result.getString("Mahv"));
                Khutap kt = new Khutap();
                kt.setTenkhutap(result.getString("Tenkhutap"));
                Thoigiantap thoigian = new Thoigiantap();
                thoigian.setHoivien(hv);
                thoigian.setKhutap(kt);
                thoigian.setNgay(result.getDate("Ngaytap"));
                thoigian.setThoigianden(result.getTime("Thoigiandentap"));
                thoigian.setThoigianve(result.getTime("Thoigianrave"));

                listData.add(thoigian);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<Thoigiantap> membersListData2;
    public void membersShowData2() {
        try {
            Alert alert;

            if (Mahvtk.getText().isEmpty() || Ngaytk.getValue() == null ) {
                emptyFields();
            } else {
                membersListData2 = membersDataList23();

                if (membersListData2.isEmpty()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Không có dữ liệu để hiển thị!");
                    alert.showAndWait();
                } else {
                    thoigiantap_col_mahv.setCellValueFactory(new PropertyValueFactory<>("Mahoivien"));
                    thoigiantap_col_khutap.setCellValueFactory(new PropertyValueFactory<>("Tenkhutap"));
                    thoigiantap_col_thoigianden.setCellValueFactory(new PropertyValueFactory<>("Thoigianden"));
                    thoigiantap_col_thoigianve.setCellValueFactory(new PropertyValueFactory<>("Thoigianve"));
                    thoigiantap_col_ngay.setCellValueFactory(new PropertyValueFactory<>("Ngay"));

                    thoigiantap_tableView.setItems(membersListData2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDataInDatabase(Thoigiantap selectedItem) {
        connect = com.example.demo1.ConnectSQL.connectDB();
        try {
            Alert alert;
            // Kiểm tra sự tồn tại của Mahv trong cơ sở dữ liệu
            String checkMahvExistQuery = "SELECT * FROM Hoivien WHERE Mahv = ?";
            prepare = connect.prepareStatement(checkMahvExistQuery);
            prepare.setString(1, Mahv.getText());
            result = prepare.executeQuery();
            if (!result.next()) {
                // Mahv không tồn tại trong cơ sở dữ liệu
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Mã hội viên bạn vừa nhập không tồn tại trong danh sách hội viên!");
                alert.showAndWait();
                return;
            }
            // Kiểm tra định dạng của Thoigianden
            try {
                LocalTime thoigianden = LocalTime.parse(Thoigianden.getText());
                // Thực hiện câu lệnh UPDATE để cập nhật dữ liệu trong cơ sở dữ liệu
                String updateQuery = "UPDATE Hoivien_Khutap SET Mahv = ?, Tenkhutap = ?, Thoigiandentap = ?, Thoigianrave = ?, Ngaytap = ? WHERE Mahv = ? AND Ngaytap = ?";
                prepare = connect.prepareStatement(updateQuery);
                prepare.setString(1, Mahv.getText());
                prepare.setString(2, Khutap.getSelectionModel().getSelectedItem());
                prepare.setTime(3, Time.valueOf(Thoigianden.getText()));
                prepare.setTime(4, Time.valueOf(Thoigianve.getText()));
                prepare.setDate(5, Date.valueOf(Ngay.getValue()));
                prepare.setString(6, Mahv.getText());
                prepare.setDate(7, Date.valueOf(Ngay.getValue()));

                prepare.executeUpdate();

                // Hiển thị thông báo thành công
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Cập nhật thành công!");
                alert.showAndWait();
            } catch (DateTimeParseException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Định dạng giờ không hợp lệ (Thời gian đến). Vui lòng nhập theo định dạng 'giờ:phút:giây' (VD: 09:30:00)");
                alert.showAndWait();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Thực hiện hiển thị dữ liệu
        // ...
    }





    public void updateTableViewData() {
        Thoigiantap selectedItem = thoigiantap_tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String originalMahv = selectedItem.gethoivien().getMahoivien();

            // Kiểm tra sự tồn tại của Mahv trong cơ sở dữ liệu
            String checkMahvExistQuery = "SELECT * FROM Hoivien WHERE Mahv = ?";
            try {
                prepare = connect.prepareStatement(checkMahvExistQuery);
                prepare.setString(1, Mahv.getText());
                result = prepare.executeQuery();

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
                return;
            }

            // Kiểm tra định dạng của Thoigianden
            try {
                LocalTime thoigianden = LocalTime.parse(Thoigianden.getText());

                // Kiểm tra định dạng của Thoigianve (nếu được cung cấp)
                if (Thoigianve.getText() != null && !Thoigianve.getText().isEmpty()) {
                    try {
                        LocalTime thoigianve = LocalTime.parse(Thoigianve.getText());

                        // Kiểm tra định dạng của Thoigianve
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String thoigianveFormatted = formatter.format(thoigianve);

                        // Kiểm tra xem giá trị nhập vào có khớp với định dạng đã định nghĩa hay không
                        if (!Thoigianve.getText().equals(thoigianveFormatted)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Định dạng giờ không hợp lệ (Thời gian về). Vui lòng nhập theo định dạng 'giờ:phút:giây' (VD: 09:30:00)");
                            alert.showAndWait();
                            return;
                        }
                    } catch (DateTimeParseException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Định dạng giờ không hợp lệ (Thời gian về). Vui lòng nhập theo định dạng 'giờ:phút:giây' (VD: 09:30:00)");
                        alert.showAndWait();
                        return;
                    }
                }

                // Thực hiện các xử lý khác và cập nhật dữ liệu trong cơ sở dữ liệu
                selectedItem.gethoivien().setMahoivien(Mahv.getText());
                selectedItem.getKhutap().setTenkhutap(Khutap.getSelectionModel().getSelectedItem());
                selectedItem.setThoigianden(Time.valueOf(Thoigianden.getText()));
                selectedItem.setThoigianve(Time.valueOf(Thoigianve.getText()));
                selectedItem.setNgay(Date.valueOf(Ngay.getValue()));

                updateDataInDatabase(selectedItem);

                thoigiantap_tableView.refresh();
            } catch (DateTimeParseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Định dạng giờ không hợp lệ (Thời gian đến). Vui lòng nhập theo định dạng 'giờ:phút:giây' (VD: 09:30:00)");
                alert.showAndWait();
                return;
            }
        }
    }



    public void setTableViewClickListener() {
        thoigiantap_tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                Thoigiantap selectedItem = thoigiantap_tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    Mahv.setText(selectedItem.gethoivien().getMahoivien());
                    Khutap.getSelectionModel().select(selectedItem.getTenkhutap());
                    Thoigianden.setText(selectedItem.getThoigianden().toString());

                    // Kiểm tra giá trị của Thoigianve trước khi gán vào textfield
                    if (selectedItem.getThoigianve() != null) {
                        Thoigianve.setText(selectedItem.getThoigianve().toString());
                    } else {
                        Thoigianve.setText("");
                    }

                    Ngay.setValue(LocalDate.parse(selectedItem.getNgay().toString()));
                }
            }
        });
    }

    public void addcomboboxnv(){
        String listmanv = "SELECT Tenkhutap FROM Khutap";
        connect = com.example.demo1.ConnectSQL.connectDB();
        try {
            ObservableList listG = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listmanv);
            result = prepare.executeQuery();
            while (result.next()) {
                listG.add(result.getString("Tenkhutap"));
            }
            Khutap.setItems(listG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void membersClear() {
        membersShowData();
        thoigiantap_tableView.refresh();
        Mahv.setText("");
        Khutap.setValue(null);
        Thoigianden.setText("");
        Thoigianve.setText("");
        Ngay.setValue(LocalDate.now());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersShowData();
        setTableViewClickListener();

        addcomboboxnv();
        Ngay.setValue(LocalDate.now());

    }
}
