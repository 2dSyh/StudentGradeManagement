package test;

import grademanager.util.DatabaseConnector;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseResetTest {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // Xóa dữ liệu trong các bảng
            stmt.executeUpdate("DELETE FROM Users");
            stmt.executeUpdate("DELETE FROM Classes");
            stmt.executeUpdate("DELETE FROM Students");
            stmt.executeUpdate("DELETE FROM Subjects");
            stmt.executeUpdate("DELETE FROM Grades");
            stmt.executeUpdate("DELETE FROM Assignments");

            // Reset lại ID về 1 bằng cách xóa sequence
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Users'");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Classes'");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Students'");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Subjects'");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Grades'");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Assignments'");

            System.out.println("Đã xóa toàn bộ dữ liệu và reset ID về 1 thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}