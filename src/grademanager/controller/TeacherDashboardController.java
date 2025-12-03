package grademanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Label;

public class TeacherDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button gradeManagementButton;
    @FXML private Button logoutButton;
    @FXML private AnchorPane mainContentPane;

    @FXML
    private void handleGradeManagement() {
        // Mở màn hình nhập/xem điểm
        loadView("/grademanager/view/GradeManagementView.fxml");
    }

    @FXML
    private void handleLogout() {
        // Quay lại màn hình đăng nhập
        loadView("/grademanager/view/LoginScreen.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}