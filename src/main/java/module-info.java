module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires java.desktop;


    opens com.example.demo1 to javafx.fxml;
    //exports com.example.demo1;
    exports com.example.demo1.Class;
    opens com.example.demo1.Class to javafx.fxml;
    exports com.example.demo1;
    //opens com.example.demo1.Event to javafx.fxml;
    //exports com.example.demo1;
}