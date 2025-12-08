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
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class GradeManagementController {

    // SỬA: Khai báo đúng theo yêu cầu mới (Chọn Học sinh thay vì chọn Lớp)
    @FXML private ComboBox<Student> studentComboBox; 
    @FXML private ComboBox<Subject> subjectComboBox; 
    
    @FXML private Button loadButton;
    @FXML private Button saveButton;
    @FXML private Button avgButton; // Nút tính TB
    @FXML private Button gpaButton; // Nút tính GPA
    @FXML private Label avgLabel;
    @FXML private Label gpaLabel;

    @FXML private TableView<Grade> gradeTableView;
    // SỬA: Cột hiển thị loại điểm (Midterm, Final...)
    @FXML private TableColumn<Grade, String> gradeTypeColumn; 
    // SỬA: Cột hiển thị điểm số
    @FXML private TableColumn<Grade, Double> scoreColumn;     

    private ObservableList<Grade> gradeList = FXCollections.observableArrayList();
    private GradeDAO gradeDAO = new GradeDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private StudentDAO studentDAO = new StudentDAO();

    @FXML
    private void initialize() {
        setupComboBoxes();
        setupTable();
    }

    // Hàm load danh sách vào ComboBox và hiển thị Tên thay vì ID
    private void setupComboBoxes() {
        // Load danh sách học sinh
        studentComboBox.setItems(FXCollections.observableArrayList(studentDAO.getAll()));
        studentComboBox.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student s) {
                // Hiển thị: Nguyễn Văn A (ID: 1)
                return s != null ? s.getFullName() + " (ID: " + s.getStudentId() + ")" : "";
            }
            @Override
            public Student fromString(String string) { return null; }
        });

        // Load danh sách môn học
        subjectComboBox.setItems(FXCollections.observableArrayList(subjectDAO.getAll()));
        subjectComboBox.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject s) {
                return s != null ? s.getSubjectName() : "";
            }
            @Override
            public Subject fromString(String string) { return null; }
        });
    }

    private void setupTable() {
        // Gắn dữ liệu cột
        gradeTypeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGradeType()));
        scoreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getScore()).asObject());

        // Cho phép sửa điểm trực tiếp trên bảng
        gradeTableView.setEditable(true);
        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        // Sự kiện khi sửa xong một ô điểm -> Cập nhật vào model trong list (chưa lưu xuống DB)
        scoreColumn.setOnEditCommit(event -> {
            Grade g = event.getRowValue();
            g.setScore(event.getNewValue());
        });

        gradeTableView.setItems(gradeList);
    }

    @FXML
    private void handleLoadGrades() {
        gradeList.clear();

        Student selectedStudent = studentComboBox.getValue();
        Subject selectedSubject = subjectComboBox.getValue();

        // Validate
        if (selectedStudent == null || selectedSubject == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn Học sinh và Môn học!");
            return;
        }

        // LOGIC MỚI: Load điểm theo ID Student và ID Subject
        // (Dựa vào hàm getGradesByStudentAndSubject có sẵn trong GradeDAO)
        gradeList.addAll(gradeDAO.getGradesByStudentAndSubject(
            selectedStudent.getStudentId(), 
            selectedSubject.getSubjectId()
        ));
        
        // Nếu chưa có điểm nào, có thể thông báo
        if (gradeList.isEmpty()) {
             showAlert(Alert.AlertType.INFORMATION, "Thông tin", "Học sinh này chưa có đầu điểm nào cho môn " + selectedSubject.getSubjectName());
        }
    }

    @FXML
    private void handleSaveChanges() {
        if (gradeList.isEmpty()) return;

        boolean success = true;
        for (Grade g : gradeList) {
            // Gọi hàm update cho từng dòng điểm
            if (!gradeDAO.update(g)) {
                success = false;
            }
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã lưu cập nhật điểm!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi lưu dữ liệu.");
        }
    }

    @FXML
    private void handleCalculateAverage() {
        Student selectedStudent = studentComboBox.getValue();
        Subject selectedSubject = subjectComboBox.getValue();

        if (selectedStudent != null && selectedSubject != null) {
            double avg = gradeDAO.getWeightedAverageBySubject(selectedStudent.getStudentId(), selectedSubject.getSubjectId());
            avgLabel.setText(String.format("%.2f", avg));
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Chọn Học sinh và Môn để tính điểm TB!");
        }
    }

    @FXML
    private void handleCalculateGPA() {
        Student selectedStudent = studentComboBox.getValue();
        if (selectedStudent != null) {
            double gpa = gradeDAO.getOverallAverage(selectedStudent.getStudentId());
            gpaLabel.setText(String.format("%.2f", gpa));
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Chọn Học sinh để tính GPA!");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}