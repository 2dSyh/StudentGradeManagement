package grademanager; // Package gốc

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Đường dẫn này cực kỳ quan trọng, nó phải trỏ đúng tới file FXML
            // Dựa trên code cũ của bạn, đường dẫn có vẻ là: /grademanager/view/LoginScreen.fxml
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/grademanager/view/LoginScreen.fxml")));
            
            Scene scene = new Scene(root);
            
            // Thiết lập tiêu đề cửa sổ
            primaryStage.setTitle("Hệ thống Quản lý Điểm Học sinh");
            
            // Không cho phép thay đổi kích thước cửa sổ login (tùy chọn)
            primaryStage.setResizable(false);
            
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi không tìm thấy file FXML hoặc lỗi code JavaFX: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}