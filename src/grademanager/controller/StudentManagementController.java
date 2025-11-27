package grademanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import grademanager.model.Student;

public class StudentManagementController {

    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> dobColumn;
    @FXML private TableColumn<Student, String> genderColumn;
    @FXML private TableColumn<Student, String> addressColumn;
    @FXML private TableColumn<Student, Integer> classColumn;

    @FXML private TextField nameField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField addressField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private ComboBox<Integer> classComboBox;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Gắn dữ liệu cột
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStudentId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        dobColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDateOfBirth().toString()));
        genderColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGender()));
        addressColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAddress()));
        classColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getClassroom()).asObject());

        studentTableView.setItems(studentList);

        // Gắn dữ liệu mẫu cho comboBox
        genderComboBox.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
        classComboBox.setItems(FXCollections.observableArrayList(10, 11, 12));

        // Lắng nghe chọn dòng
        studentTableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> showStudentDetails(newSelection));
    }

    @FXML
    private void handleAddButton() {
        Student student = new Student(
            studentList.size() + 1,
            nameField.getText(),
            dobPicker.getValue(),
            genderComboBox.getValue(),
            addressField.getText(),
            classComboBox.getValue()
        );
        studentList.add(student);
        clearForm();
    }

    @FXML
    private void handleUpdateButton() {
        Student selected = studentTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setFullName(nameField.getText());
            selected.setDateOfBirth(dobPicker.getValue());
            selected.setGender(genderComboBox.getValue());
            selected.setAddress(addressField.getText());
            selected.setClass(classComboBox.getValue());
            studentTableView.refresh();
        }
    }

    @FXML
    private void handleDeleteButton() {
        Student selected = studentTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            studentList.remove(selected);
            clearForm();
        }
    }

    @FXML
    private void handleClearButton() {
        clearForm();
    }

    private void showStudentDetails(Student student) {
        if (student != null) {
            nameField.setText(student.getFullName());
            dobPicker.setValue(student.getDateOfBirth());
            genderComboBox.setValue(student.getGender());
            addressField.setText(student.getAddress());
            classComboBox.setValue(student.getClassroom());
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    private void clearForm() {
        nameField.clear();
        dobPicker.setValue(null);
        genderComboBox.setValue(null);
        addressField.clear();
        classComboBox.setValue(null);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
}