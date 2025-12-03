package grademanager.controller;

import java.util.List;

import grademanager.dao.StudentDAO;
import grademanager.model.SchoolClass;
import grademanager.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import grademanager.dao.ClassroomDAO;
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
    private StudentDAO studentDAO = new StudentDAO();
    private Student selectedStudent;

    @FXML
    private void initialize() {
        // Gắn dữ liệu cột
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStudentId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        dobColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDateOfBirth().toString()));
        genderColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGender()));
        addressColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAddress()));
        classColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getClassroom()).asObject());

        // Load dữ liệu từ DB
        studentList.addAll(studentDAO.getAll());
        studentTableView.setItems(studentList);

        // Gắn dữ liệu cho comboBox
        genderComboBox.setItems(FXCollections.observableArrayList("Nam", "Nữ"));

        ClassroomDAO classroomDAO = new ClassroomDAO();
        List<SchoolClass> classes = classroomDAO.getAll();
        List<Integer> classIds = classes.stream()
                                        .map(SchoolClass::getClassId)
                                        .toList();
        classComboBox.setItems(FXCollections.observableArrayList(classIds));

        // Lắng nghe chọn dòng
        studentTableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSel, newSel) -> {
                if (newSel != null) {
                    selectedStudent = newSel;
                    showStudentDetails(newSel);
                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            });
    }

    private void showStudentDetails(Student student) {
        nameField.setText(student.getFullName());
        dobPicker.setValue(student.getDateOfBirth());
        genderComboBox.setValue(student.getGender());
        addressField.setText(student.getAddress());
        classComboBox.setValue(student.getClassroom());
    }

    @FXML
    private void handleAddButton() {
        Student student = new Student(
            0,
            nameField.getText(),
            dobPicker.getValue(),
            genderComboBox.getValue(),
            addressField.getText(),
            classComboBox.getValue()
        );
        if (studentDAO.insert(student)) {
            studentList.add(student);
            clearForm();
        }
    }

    @FXML
    private void handleUpdateButton() {
        if (selectedStudent != null) {
            selectedStudent.setFullName(nameField.getText());
            selectedStudent.setDateOfBirth(dobPicker.getValue());
            selectedStudent.setGender(genderComboBox.getValue());
            selectedStudent.setAddress(addressField.getText());
            selectedStudent.setClass(classComboBox.getValue());
            if (studentDAO.update(selectedStudent)) {
                studentTableView.refresh();
                clearForm();
            }
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (selectedStudent != null) {
            if (studentDAO.delete(selectedStudent.getStudentId())) {
                studentList.remove(selectedStudent);
                clearForm();
            }
        }
    }

    @FXML
    private void handleClearButton() {
        clearForm();
    }

    private void clearForm() {
        nameField.clear();
        dobPicker.setValue(null);
        genderComboBox.getSelectionModel().clearSelection();
        addressField.clear();
        classComboBox.getSelectionModel().clearSelection();
        selectedStudent = null;
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
}