package grademanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class TeacherDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button gradeManagementButton;
    @FXML private Button logoutButton;
    @FXML private AnchorPane mainContentPane;

    @FXML
    private void handleGradeManagement() {
        loadView("/grademanager/view/GradeManagementView.fxml");
    }

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