package grademanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Label;
public class TeacherDashboardController {
    @FXML private Label welcomeLabel;
    @FXML private Button gradeManagementButton;
    @FXML private Button logoutButton;
    @FXML private AnchorPane mainContentPane;

    @FXML
    private void handleGradeManagement(){
        System.out.println("Mở màn hình nhập/xem điểm");
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
            System.out.println("Đăng xuất thành công");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlPath){
        try{
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContentPane.getChildren().setAll(view);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
