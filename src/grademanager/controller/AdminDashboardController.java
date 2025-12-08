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

    @FXML private void handleStudentManagement() { loadView("/grademanager/view/StudentManagementView.fxml"); }
    @FXML private void handleTeacherManagement() { loadView("/grademanager/view/TeacherManagementView.fxml"); }
    @FXML private void handleClassManagement() { loadView("/grademanager/view/SchoolClassManagementView.fxml"); }
    @FXML private void handleSubjectManagement() { loadView("/grademanager/view/SubjectManagementView.fxml"); }
    @FXML private void handleAssignment() { loadView("/grademanager/view/AssignmentView.fxml"); }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/grademanager/view/LoginScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            // --- FIX: ANCHOR (STRETCH TO FIT) ---
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
            // ------------------------------------
            mainContentPane.getChildren().setAll(view);
        } catch (IOException e) { e.printStackTrace(); }
    }
}