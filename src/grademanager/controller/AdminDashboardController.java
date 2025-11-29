package grademanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class AdminDashboardController {

    @FXML private Button studentManagementButton;
    @FXML private Button teacherManagementButton;
    @FXML private Button classManagementButton;
    @FXML private Button subjectManagementButton;
    @FXML private Button assignmentButton;
    @FXML private Button logoutButton;

    @FXML private AnchorPane mainContentPane;

    // Mở màn hình quản lý sinh viên
    @FXML
    private void handleStudentManagement() {
        loadView("/grademanager/view/StudentManagementView.fxml");
    }

    // Mở màn hình quản lý giáo viên
    @FXML
    private void handleTeacherManagement() {
        loadView("/grademanager/view/TeacherManagementView.fxml");
    }

    // Mở màn hình quản lý lớp học
    @FXML
    private void handleClassManagement() {
        loadView("/grademanager/view/SchoolClassManagementView.fxml");
    }

    // Mở màn hình quản lý môn học
    @FXML
    private void handleSubjectManagement() {
        loadView("/grademanager/view/SubjectManagementView.fxml");
    }

    // Mở màn hình phân công giảng dạy
    @FXML
    private void handleAssignment() {
        loadView("/grademanager/view/AssignmentView.fxml");
    }

    // Đăng xuất
    // Đăng xuất - Cần thay đổi toàn bộ Scene chứ không chỉ loadView
    @FXML
    private void handleLogout() {
        try {
            // 1. Load file FXML của màn hình Login
            // Lưu ý: Đảm bảo đường dẫn đúng là LoginScreen.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/grademanager/view/LoginScreen.fxml"));
            
            // 2. Tạo Scene mới
            Scene scene = new Scene(root);
            
            // 3. Lấy Stage (cửa sổ) hiện tại từ nút Logout
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            
            // 4. Set Scene mới cho Stage (thay thế hoàn toàn giao diện cũ)
            stage.setScene(scene);
            stage.centerOnScreen(); // Căn giữa màn hình cho đẹp
            
            System.out.println("Đăng xuất thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm tiện ích để load view khác vào mainContentPane
    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}