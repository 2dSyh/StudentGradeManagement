package grademanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:sqlite:database/quanlydiem.db";
    

    private DatabaseConnector() {}

    public static Connection getConnection() {
    
        try {
            Class.forName("org.sqlite.JDBC"); // bắt buộc để load driver
            Connection conn = DriverManager.getConnection(URL);
            if (conn != null && !conn.isClosed()){
                System.out.println("thanh cong");    
            }
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("khong tim thay driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(" loi ket noi: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
