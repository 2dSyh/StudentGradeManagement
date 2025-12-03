package grademanager.controller;

import grademanager.dao.SubjectDAO;
import grademanager.model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SubjectManagementController {

    @FXML private TextField subjectNameField;
    @FXML private TableView<Subject> subjectTableView;
    @FXML private TableColumn<Subject, Integer> idColumn;
    @FXML private TableColumn<Subject, String> nameColumn;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    private SubjectDAO subjectDAO = new SubjectDAO();
    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();
    private Subject selectedSubject;

    @FXML
    private void initialize() {
        // Gắn dữ liệu cột
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getSubjectId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSubjectName()));

        // Load dữ liệu từ DB
        loadSubjects();

        // Lắng nghe chọn dòng trong bảng
        subjectTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedSubject = newSel;
                showSubjectDetails(newSel);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void loadSubjects() {
        subjectList.setAll(subjectDAO.getAll());
        subjectTableView.setItems(subjectList);
    }

    private void showSubjectDetails(Subject s) {
        subjectNameField.setText(s.getSubjectName());
    }

    @FXML
    private void handleAddButton() {
        Subject s = new Subject(0, subjectNameField.getText());
        if (subjectDAO.insert(s)) {
            subjectList.add(s);
            clearForm();
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm môn học!");
        }
    }

    @FXML
    private void handleUpdateButton() {
        if (selectedSubject != null) {
            selectedSubject.setSubjectName(subjectNameField.getText());
            if (subjectDAO.update(selectedSubject)) {
                subjectTableView.refresh();
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật môn học!");
            }
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (selectedSubject != null) {
            if (subjectDAO.delete(selectedSubject.getSubjectId())) {
                subjectList.remove(selectedSubject);
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa môn học!");
            }
        }
    }

    @FXML
    private void handleClearButton() {
        clearForm();
    }

    private void clearForm() {
        subjectNameField.clear();
        selectedSubject = null;
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