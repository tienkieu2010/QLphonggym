package com.example.demo1;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectSQL {
    public static Connection connectDB() {

        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;database=qlphonggym2;user=sa;password=grandzio3454;encrypt=true;trustServerCertificate=true";
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    public static void main(String[] args) {
        try {
            connectDB();
            System.out.println("kon lếch tịt");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
