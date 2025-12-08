package grademanager.controller;

import grademanager.dao.GradeDAO;
import grademanager.dao.SubjectDAO;
import grademanager.dao.StudentDAO;
import grademanager.model.Grade;
import grademanager.model.Student;
import grademanager.model.Subject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import java.util.List;
import java.util.stream.Collectors;

public class GradeManagementController {

    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Subject> subjectComboBox;
    @FXML private Button loadButton;
    @FXML private Button saveButton;
    @FXML private Button avgButton;
    @FXML private Button gpaButton;
    @FXML private Label avgLabel;
    @FXML private Label gpaLabel;


    @FXML private TableView<Grade> gradeTableView;
    // Khai báo các cột để fix lỗi Type Mismatch
    @FXML private TableColumn<Grade, Integer> gradeIdColumn;
    @FXML private TableColumn<Grade, Integer> studentIdColumn;
    @FXML private TableColumn<Grade, Integer> subjectIdColumn;
    @FXML private TableColumn<Grade, String> gradeTypeColumn; 
    @FXML private TableColumn<Grade, Double> scoreColumn;

    private ObservableList<Grade> gradeList = FXCollections.observableArrayList();
    private GradeDAO gradeDAO = new GradeDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private StudentDAO studentDAO = new StudentDAO();

    @FXML
    private void initialize() {
        setupComboBoxes();
        setupTableColumns();
    }

    private void setupTableColumns() {
        gradeIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getGradeId()).asObject());
        studentIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getStudent()).asObject());
        subjectIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getSubject()).asObject());
        gradeTypeColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGradeType()));
        scoreColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getScore()).asObject());

        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        scoreColumn.setOnEditCommit(event -> {
            Grade g = event.getRowValue();
            g.setScore(event.getNewValue());
        });

        gradeTableView.setItems(gradeList);
        gradeTableView.setEditable(true);
    }

    private void setupComboBoxes() {
        // Load danh sách học sinh
        List<Student> students = studentDAO.getAll();
        studentComboBox.setItems(FXCollections.observableArrayList(students));
        studentComboBox.setConverter(new StringConverter<Student>() {
            @Override public String toString(Student s) { return (s == null) ? "" : s.getFullName(); }
            @Override public Student fromString(String string) { return null; }
        });

        // Load danh sách môn học
        List<Subject> subjects = subjectDAO.getAll();
        subjectComboBox.setItems(FXCollections.observableArrayList(subjects));
        subjectComboBox.setConverter(new StringConverter<Subject>() {
            @Override public String toString(Subject s) { return (s == null) ? "" : s.getSubjectName(); }
            @Override public Subject fromString(String string) { return null; }
        });
    }

    @FXML
    private void handleLoadGrades() {
        gradeList.clear();
        Student selectedStudent = studentComboBox.getValue();
        Subject selectedSubject = subjectComboBox.getValue();

        if (selectedStudent == null || selectedSubject == null) {
            showAlert("Vui lòng chọn Học sinh và Môn học!");
            return;
        }

        int studentId = selectedStudent.getStudentId();
        int subjectId = selectedSubject.getSubjectId();

        // 1. Get Real Grades from DB
        List<Grade> dbGrades = gradeDAO.getGradesByStudentAndSubject(studentId, subjectId);
        
        // 2. Define the required structure (5 slots total: 3x1, 1x2, 1x3)
        List<String> requiredStructure = List.of(
            "assignment", "assignment", "assignment", 
            "midterm",    
            "final"       
        );

        // 3. The Mixing Logic: Find existing grades and fill gaps with new Grade(0) objects
        List<Grade> tempDbList = new java.util.ArrayList<>(dbGrades);

        for (String type : requiredStructure) {
            Grade foundGrade = null;

            // Search for a matching real grade in the temp list
            for (Grade g : tempDbList) {
                if (g.getGradeType().equalsIgnoreCase(type)) {
                    foundGrade = g;
                    break;
                }
            }

            if (foundGrade != null) {
                gradeList.add(foundGrade);
                tempDbList.remove(foundGrade); // Mark as used
            } else {
                // Create a Ghost Grade (ID: 0) to fill the empty slot
                gradeList.add(new Grade(0, studentId, subjectId, type, 0.0));
            }
        }
    }

    @FXML
    private void handleSaveChanges() {
        for (Grade g : gradeList) {
            if (g.getGradeId() == 0) {
                gradeDAO.insert(g); // INSERT NEW
            } else {
                gradeDAO.update(g); // UPDATE EXISTING
            }
        }
        handleLoadGrades(); // Reload to update IDs (Ghost -> Real)
        showAlert("Đã lưu dữ liệu thành công!");
    }

    @FXML
    private void handleCalculateAverage() {
        Student s = studentComboBox.getValue();
        Subject sb = subjectComboBox.getValue();
        if (s != null && sb != null) {
            double avg = gradeDAO.getWeightedAverageBySubject(s.getStudentId(), sb.getSubjectId());
            avgLabel.setText("Điểm TB: " + String.format("%.2f", avg));
        }
    }

    @FXML
    private void handleCalculateGPA() {
        Student s = studentComboBox.getValue();
        if (s != null) {
            double gpa = gradeDAO.getOverallAverage(s.getStudentId());
            gpaLabel.setText("GPA: " + String.format("%.2f", gpa));
        }
    }
    
    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.show();
    }
}