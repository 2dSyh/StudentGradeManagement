package grademanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import grademanager.model.Assignment;

public class AssignmentController {

    @FXML private ComboBox<String> teacherComboBox;
    @FXML private ComboBox<String> classComboBox;
    @FXML private ComboBox<String> subjectComboBox;

    @FXML private TableView<Assignment> assignmentTableView;
    @FXML private TableColumn<Assignment, String> teacherNameColumn;
    @FXML private TableColumn<Assignment, String> classNameColumn;
    @FXML private TableColumn<Assignment, String> subjectNameColumn;

    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Gắn dữ liệu mẫu cho ComboBox
        teacherComboBox.setItems(FXCollections.observableArrayList("GV01 - Nguyễn Văn A", "GV02 - Trần Thị B"));
        classComboBox.setItems(FXCollections.observableArrayList("10A1", "11B2", "12C3"));
        subjectComboBox.setItems(FXCollections.observableArrayList("Toán", "Văn", "Anh"));

        // Gắn dữ liệu cột (hiển thị tên thay vì ID)
        teacherNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty("Teacher#" + cellData.getValue().getTeacherId()));
        classNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty("Class#" + cellData.getValue().getClassId()));
        subjectNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty("Subject#" + cellData.getValue().getSubjectId()));

        assignmentTableView.setItems(assignmentList);
    }

    @FXML
    private void handleAddButton() {
        if (teacherComboBox.getValue() != null && classComboBox.getValue() != null && subjectComboBox.getValue() != null) {
            Assignment assignment = new Assignment(
                assignmentList.size() + 1,
                teacherComboBox.getSelectionModel().getSelectedIndex() + 1, // giả định ID
                classComboBox.getSelectionModel().getSelectedIndex() + 1,
                subjectComboBox.getSelectionModel().getSelectedIndex() + 1
            );
            assignmentList.add(assignment);
        }
    }

    @FXML
    private void handleDeleteButton() {
        Assignment selected = assignmentTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            assignmentList.remove(selected);
        }
    }
}