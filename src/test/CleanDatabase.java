package test; // Or package grademanager; if you put it in the main folder

import grademanager.util.DatabaseConnector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CleanDatabase {
    public static void main(String[] args) {
        System.out.println("Dang don dep du lieu...");
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. Xóa sạch dữ liệu (Theo thứ tự để tránh lỗi khóa ngoại)
            stmt.executeUpdate("DELETE FROM Grades");
            stmt.executeUpdate("DELETE FROM Assignments");
            stmt.executeUpdate("DELETE FROM Students");
            stmt.executeUpdate("DELETE FROM Classes");
            stmt.executeUpdate("DELETE FROM Subjects");
            stmt.executeUpdate("DELETE FROM Users"); // Xóa hết user cũ

            // 2. Reset bộ đếm ID về 1 (Cho đẹp)
            stmt.executeUpdate("DELETE FROM sqlite_sequence");

            // 3. QUAN TRỌNG: Tạo lại tài khoản Admin mặc định
            // Để thầy cô giáo có thể đăng nhập được lần đầu tiên
            String createAdmin = "INSERT INTO Users(username, password, full_name, role) " +
                                 "VALUES ('admin', '123', 'System Administrator', 'ADMIN')";
            stmt.executeUpdate(createAdmin);

            System.out.println("=========================================");
            System.out.println("THANH CONG! Database da sach se.");
            System.out.println("Tai khoan mac dinh:");
            System.out.println("User: admin");
            System.out.println("Pass: 123");
            System.out.println("=========================================");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Co loi xay ra: " + e.getMessage());
        }
    }
}