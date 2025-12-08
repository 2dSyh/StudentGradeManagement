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

public class GradeManagementController {

    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Subject> subjectComboBox;
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

    @FXML
    private void initialize() {
        setupTableColumns();
        setupComboBoxes();
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
        List<Student> students = studentDAO.getAll();
        studentComboBox.setItems(FXCollections.observableArrayList(students));
        studentComboBox.setConverter(new StringConverter<Student>() {
            @Override public String toString(Student s) { return (s == null) ? "" : s.getFullName(); }
            @Override public Student fromString(String string) { return null; }
        });

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

        // 1. Lấy điểm thật từ DB
        List<Grade> dbGrades = gradeDAO.getGradesByStudentAndSubject(studentId, subjectId);
        
        // 2. Tạo khung chuẩn 5 dòng (Template)
        List<String> requiredTypes = List.of(
            "assignment", "assignment", "assignment", // 3 đầu điểm hệ số 1
            "midterm",    // 1 đầu điểm giữa kỳ
            "final"       // 1 đầu điểm cuối kỳ
        );

        // 3. Thuật toán "Trộn": Lấp đầy bảng
        // Với mỗi loại điểm bắt buộc, tìm xem trong DB đã có chưa?
        // - Có rồi: Dùng điểm từ DB (ID thật).
        // - Chưa có: Tạo dòng ảo (ID = 0).
        
        // Copy danh sách DB ra để đánh dấu những cái đã dùng
        List<Grade> remainingDbGrades = new java.util.ArrayList<>(dbGrades);

        for (String type : requiredTypes) {
            Grade found = null;
            
            // Tìm trong đống điểm DB xem có loại này không
            for (Grade g : remainingDbGrades) {
                if (g.getGradeType().equalsIgnoreCase(type)) {
                    found = g;
                    break;
                }
            }

            if (found != null) {
                // Nếu có: Thêm vào bảng hiển thị và xóa khỏi danh sách chờ
                gradeList.add(found);
                remainingDbGrades.remove(found);
            } else {
                // Nếu không có: Tạo dòng mới (ID = 0)
                gradeList.add(new Grade(0, studentId, subjectId, type, 0.0));
            }
        }
        
        // (Tùy chọn) Nếu DB có nhiều điểm hơn chuẩn (ví dụ lỗi dư thừa), add nốt vào
        gradeList.addAll(remainingDbGrades);
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
        handleLoadGrades(); 
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