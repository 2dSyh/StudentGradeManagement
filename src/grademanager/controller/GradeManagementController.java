package grademanager.controller;

import grademanager.dao.GradeDAO;
import grademanager.dao.SubjectDAO;
import grademanager.dao.StudentDAO;
import grademanager.model.Grade;
import grademanager.model.Student;
import grademanager.model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.util.List;

public class GradeManagementController {

    @FXML private ComboBox<Integer> studentComboBox;   // chọn học sinh theo ID
    @FXML private ComboBox<String> subjectComboBox;    // chọn môn theo tên
    @FXML private Button loadButton;
    @FXML private Button saveButton;
    @FXML private Button avgButton;
    @FXML private Button gpaButton;

    @FXML private TableView<Grade> gradeTableView;
    @FXML private TableColumn<Grade, Integer> gradeIdColumn;
    @FXML private TableColumn<Grade, Integer> studentIdColumn;
    @FXML private TableColumn<Grade, Integer> subjectIdColumn;
    @FXML private TableColumn<Grade, String> gradeTypeColumn;
    @FXML private TableColumn<Grade, Double> scoreColumn;

    @FXML private Label avgLabel;
    @FXML private Label gpaLabel;

    private ObservableList<Grade> gradeList = FXCollections.observableArrayList();
    private GradeDAO gradeDAO = new GradeDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private StudentDAO studentDAO = new StudentDAO();

    // Lưu danh sách để tiện tìm lại ID theo tên
    private List<Student> students;
    private List<Subject> subjects;

    @FXML
    private void initialize() {
        // Gắn dữ liệu cột
        gradeIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getGradeId()).asObject());
        studentIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStudent()).asObject());
        subjectIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getSubject()).asObject());
        gradeTypeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGradeType()));
        scoreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getScore()).asObject());

        // Cho phép chỉnh sửa trực tiếp điểm
        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        scoreColumn.setOnEditCommit(event -> {
            Grade g = event.getRowValue();
            g.setScore(event.getNewValue());
            gradeDAO.update(g);
        });

        gradeTableView.setItems(gradeList);
        gradeTableView.setEditable(true);

        // Nạp danh sách học sinh và môn từ DAO
        students = studentDAO.getAll();
        subjects = subjectDAO.getAll();

        studentComboBox.setItems(FXCollections.observableArrayList(
            students.stream().map(Student::getStudentId).toList()
        ));
        subjectComboBox.setItems(FXCollections.observableArrayList(
            subjects.stream().map(Subject::getSubjectName).toList()
        ));
    }

    @FXML
    private void handleLoadGrades() {
        gradeList.clear();

        Integer studentId = studentComboBox.getValue();
        String subjectName = subjectComboBox.getValue();

        if (studentId == null || subjectName == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn học sinh và môn học trước khi tải điểm!");
            alert.showAndWait();
            return;
        }

        // Tìm subjectId từ danh sách subjects
        int subjectId = subjects.stream()
                .filter(s -> s.getSubjectName().equals(subjectName))
                .findFirst()
                .map(Subject::getSubjectId)
                .orElse(-1);

        gradeList.addAll(gradeDAO.getGradesByStudentAndSubject(studentId, subjectId));
    }

    @FXML
    private void handleSaveChanges() {
        for (Grade g : gradeList) {
            gradeDAO.update(g);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Đã lưu thay đổi điểm thành công!");
        alert.showAndWait();
    }

    @FXML
    private void handleCalculateAverage() {
        Integer studentId = studentComboBox.getValue();
        String subjectName = subjectComboBox.getValue();

        if (studentId != null && subjectName != null) {
            int subjectId = subjects.stream()
                    .filter(s -> s.getSubjectName().equals(subjectName))
                    .findFirst()
                    .map(Subject::getSubjectId)
                    .orElse(-1);

            double avg = gradeDAO.getWeightedAverageBySubject(studentId, subjectId);
            avgLabel.setText("Điểm TB môn: " + String.format("%.2f", avg));
        }
    }

    @FXML
    private void handleCalculateGPA() {
        Integer studentId = studentComboBox.getValue();
        if (studentId != null) {
            double gpa = gradeDAO.getOverallAverage(studentId);
            gpaLabel.setText("GPA: " + String.format("%.2f", gpa));
        }
    }
}