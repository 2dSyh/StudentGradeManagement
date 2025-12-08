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
<<<<<<< HEAD
import java.util.List;

public class GradeManagementController {

    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Subject> subjectComboBox;
=======

public class GradeManagementController {

    // SỬA: Khai báo đúng theo yêu cầu mới (Chọn Học sinh thay vì chọn Lớp)
    @FXML private ComboBox<Student> studentComboBox; 
    @FXML private ComboBox<Subject> subjectComboBox; 
    
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
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
<<<<<<< HEAD
        setupTableColumns();
        setupComboBoxes();
    }

    private void setupTableColumns() {
        gradeIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getGradeId()).asObject());
        studentIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getStudent()).asObject());
        subjectIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getSubject()).asObject());
        gradeTypeColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGradeType()));
        scoreColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getScore()).asObject());

=======
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
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        // Sự kiện khi sửa xong một ô điểm -> Cập nhật vào model trong list (chưa lưu xuống DB)
        scoreColumn.setOnEditCommit(event -> {
            Grade g = event.getRowValue();
            g.setScore(event.getNewValue());
        });

        gradeTableView.setItems(gradeList);
<<<<<<< HEAD
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
=======
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
    }

    @FXML
    private void handleLoadGrades() {
        gradeList.clear();
        Student selectedStudent = studentComboBox.getValue();
        Subject selectedSubject = subjectComboBox.getValue();

<<<<<<< HEAD
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
=======
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
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
    }

    @FXML
    private void handleSaveChanges() {
        if (gradeList.isEmpty()) return;

        boolean success = true;
        for (Grade g : gradeList) {
<<<<<<< HEAD
            if (g.getGradeId() == 0) {
                gradeDAO.insert(g); // INSERT NEW
            } else {
                gradeDAO.update(g); // UPDATE EXISTING
            }
        }
        handleLoadGrades(); 
        showAlert("Đã lưu dữ liệu thành công!");
=======
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
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
    }

    @FXML
    private void handleCalculateAverage() {
<<<<<<< HEAD
        Student s = studentComboBox.getValue();
        Subject sb = subjectComboBox.getValue();
        if (s != null && sb != null) {
            double avg = gradeDAO.getWeightedAverageBySubject(s.getStudentId(), sb.getSubjectId());
            avgLabel.setText("Điểm TB: " + String.format("%.2f", avg));
=======
        Student selectedStudent = studentComboBox.getValue();
        Subject selectedSubject = subjectComboBox.getValue();

        if (selectedStudent != null && selectedSubject != null) {
            double avg = gradeDAO.getWeightedAverageBySubject(selectedStudent.getStudentId(), selectedSubject.getSubjectId());
            avgLabel.setText(String.format("%.2f", avg));
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Chọn Học sinh và Môn để tính điểm TB!");
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
        }
    }

    @FXML
    private void handleCalculateGPA() {
<<<<<<< HEAD
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
=======
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
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
    }
}