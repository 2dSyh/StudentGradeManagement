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
            loadSubjects();
            clearForm();
        }
    }

    @FXML
    private void handleUpdateButton() {
        if (selectedSubject != null) {
            selectedSubject.setSubjectName(subjectNameField.getText());
            if (subjectDAO.update(selectedSubject)) {
                loadSubjects();
                clearForm();
            }
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (selectedSubject != null) {
            if (subjectDAO.delete(selectedSubject.getSubjectId())) {
                loadSubjects();
                clearForm();
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
}