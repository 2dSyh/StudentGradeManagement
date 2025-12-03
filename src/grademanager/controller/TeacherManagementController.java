package grademanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import grademanager.dao.TeacherDAO;
import grademanager.model.Teacher;

public class TeacherManagementController {

    @FXML private TableView<Teacher> teacherTableView;
    @FXML private TableColumn<Teacher, Integer> idColumn;
    @FXML private TableColumn<Teacher, String> fullNameColumn;
    @FXML private TableColumn<Teacher, String> usernameColumn;

    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    private ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private Teacher selectedTeacher;

    @FXML
    private void initialize() {
        // Gắn dữ liệu cột với thuộc tính trong lớp Teacher
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());
        fullNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        usernameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserName()));

        // Load dữ liệu từ DB
        teacherList.addAll(teacherDAO.getAll());
        teacherTableView.setItems(teacherList);

        // Lắng nghe chọn dòng trong bảng
        teacherTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedTeacher = newSel;
                showTeacherDetails(newSel);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void showTeacherDetails(Teacher teacher) {
        fullNameField.setText(teacher.getFullName());
        usernameField.setText(teacher.getUserName());
        passwordField.setText(teacher.getPassWord());
    }

    @FXML
    private void handleAddButton() {
        Teacher teacher = new Teacher(
            0,
            usernameField.getText(),
            passwordField.getText(),
            fullNameField.getText()
        );
        if (teacherDAO.insert(teacher)) {
            teacherList.add(teacher);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm giáo viên thành công!");
            clearForm();
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm giáo viên!");
        }
    }

    @FXML
    private void handleUpdateButton() {
        if (selectedTeacher != null) {
            selectedTeacher.setFullName(fullNameField.getText());
            selectedTeacher.setUserName(usernameField.getText());
            selectedTeacher.setPassWord(passwordField.getText());
            if (teacherDAO.update(selectedTeacher)) {
                teacherTableView.refresh();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật giáo viên thành công!");
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật giáo viên!");
            }
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (selectedTeacher != null) {
            if (teacherDAO.delete(selectedTeacher.getUserId())) {
                teacherList.remove(selectedTeacher);
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa giáo viên thành công!");
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa giáo viên!");
            }
        }
    }

    @FXML
    private void handleClearButton() {
        clearForm();
    }

    private void clearForm() {
        fullNameField.clear();
        usernameField.clear();
        passwordField.clear();
        selectedTeacher = null;
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}