package grademanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    // Danh sách dữ liệu mẫu
    private ObservableList<Teacher> teacherList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Gắn dữ liệu cột với thuộc tính trong lớp Teacher
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());
        fullNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        usernameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUserName()));

        teacherTableView.setItems(teacherList);

        // Lắng nghe chọn dòng trong bảng
        teacherTableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> showTeacherDetails(newSelection));
    }

    @FXML
    private void handleAddButton() {
        Teacher teacher = new Teacher(
            teacherList.size() + 1,
            usernameField.getText(),
            passwordField.getText(),
            fullNameField.getText()
        );
        teacherList.add(teacher);
        clearForm();
    }

    @FXML
    private void handleUpdateButton() {
        Teacher selected = teacherTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setFullName(fullNameField.getText());
            selected.setUserName(usernameField.getText());
            selected.setPassWord(passwordField.getText());
            teacherTableView.refresh();
        }
    }

    @FXML
    private void handleDeleteButton() {
        Teacher selected = teacherTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            teacherList.remove(selected);
            clearForm();
        }
    }

    @FXML
    private void handleClearButton() {
        clearForm();
    }

    private void showTeacherDetails(Teacher teacher) {
        if (teacher != null) {
            fullNameField.setText(teacher.getFullName());
            usernameField.setText(teacher.getUserName());
            passwordField.setText(teacher.getPassWord());
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    private void clearForm() {
        fullNameField.clear();
        usernameField.clear();
        passwordField.clear();
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
}
