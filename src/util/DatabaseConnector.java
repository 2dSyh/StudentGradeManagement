package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:sqlite:database/quanlydiem.db";
    private static Connection conn = null;

    private DatabaseConnector() {}

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC"); // bắt buộc để load driver
                conn = DriverManager.getConnection(URL);
                System.out.println("thanh cong");
            } catch (ClassNotFoundException e) {
                System.out.println("khong tim thay driver: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println(" loi ket noi: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
