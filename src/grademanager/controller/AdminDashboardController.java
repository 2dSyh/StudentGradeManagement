package grademanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
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
        loadView("/grademanager/view/ClassManagementView.fxml");
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
    @FXML
    private void handleLogout() {
        System.out.println("Đăng xuất thành công!");
        // TODO: load lại màn hình login
        loadView("/grademanager/view/designLoginScreen.fxml");
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