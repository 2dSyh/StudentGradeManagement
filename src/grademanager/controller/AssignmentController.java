package grademanager.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.util.List;
import grademanager.dao.*;
import grademanager.model.*;

public class AssignmentController {

    // SỬA: Dùng Object thực thay vì String để lấy ID chính xác
    @FXML private ComboBox<Teacher> teacherComboBox;
    @FXML private ComboBox<SchoolClass> classComboBox;
    @FXML private ComboBox<Subject> subjectComboBox;

    @FXML private TableView<Assignment> assignmentTableView;
    // Cột được định nghĩa là String vì code sẽ lookup tên giáo viên/lớp
    @FXML private TableColumn<Assignment, String> teacherNameColumn;
    @FXML private TableColumn<Assignment, String> classNameColumn;
    @FXML private TableColumn<Assignment, String> subjectNameColumn;

    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    
    // Cache lists để tra cứu tên giáo viên/lớp
    private List<Teacher> teachers;
    private List<SchoolClass> classes;
    private List<Subject> subjects;
    private TeacherDAO teacherDAO = new TeacherDAO();
    private ClassroomDAO classroomDAO = new ClassroomDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();


    @FXML
    private void initialize() {
        loadDataForComboBoxes();
        setupTableColumns();
        loadTableData();
    }

    private void loadDataForComboBoxes() {
        // Teacher
        teachers = teacherDAO.getAll();
        teacherComboBox.setItems(FXCollections.observableArrayList(teachers));
        teacherComboBox.setConverter(new StringConverter<Teacher>() {
            @Override public String toString(Teacher t) { return t != null ? t.getFullName() : ""; }
            @Override public Teacher fromString(String string) { return null; }
        });

        // Class
        classes = classroomDAO.getAll();
        classComboBox.setItems(FXCollections.observableArrayList(classes));
        classComboBox.setConverter(new StringConverter<SchoolClass>() {
            @Override public String toString(SchoolClass c) { return c != null ? c.getClassName() : ""; }
            @Override public SchoolClass fromString(String string) { return null; }
        });

        // Subject
        subjects = subjectDAO.getAll();
        subjectComboBox.setItems(FXCollections.observableArrayList(subjects));
        subjectComboBox.setConverter(new StringConverter<Subject>() {
            @Override public String toString(Subject s) { return s != null ? s.getSubjectName() : ""; }
            @Override public Subject fromString(String string) { return null; }
        });
    }

    private void setupTableColumns() {
        // FIX LỖI HIỂN THỊ: Ánh xạ ID sang Tên Giáo viên
        teacherNameColumn.setCellValueFactory(cell -> {
            int id = cell.getValue().getTeacherId();
            String name = teachers.stream().filter(t -> t.getUserId() == id)
                          .findFirst().map(Teacher::getFullName).orElse("ID: " + id);
            return new SimpleStringProperty(name);
        });

        // FIX LỖI HIỂN THỊ: Ánh xạ ID sang Tên Lớp
        classNameColumn.setCellValueFactory(cell -> {
            int id = cell.getValue().getClassId();
            String name = classes.stream().filter(c -> c.getClassId() == id)
                          .findFirst().map(SchoolClass::getClassName).orElse("ID: " + id);
            return new SimpleStringProperty(name);
        });

        // FIX LỖI HIỂN THỊ: Ánh xạ ID sang Tên Môn
        subjectNameColumn.setCellValueFactory(cell -> {
            int id = cell.getValue().getSubjectId();
            String name = subjects.stream().filter(s -> s.getSubjectId() == id)
                          .findFirst().map(Subject::getSubjectName).orElse("ID: " + id);
            return new SimpleStringProperty(name);
        });
    }

    private void loadTableData() {
        assignmentList.clear();
        assignmentList.addAll(assignmentDAO.getAll());
        assignmentTableView.setItems(assignmentList);
    }

    @FXML
    private void handleAddButton() {
        Teacher t = teacherComboBox.getValue();
        SchoolClass c = classComboBox.getValue();
        Subject s = subjectComboBox.getValue();

        if (t == null || c == null || s == null) {
            showAlert("Vui lòng chọn đầy đủ thông tin!");
            return;
        }

        // SỬA: Lấy ID từ Object (An toàn hơn logic index + 1)
        Assignment assignment = new Assignment(0, t.getUserId(), c.getClassId(), s.getSubjectId());
        
        if (assignmentDAO.insert(assignment)) {
            loadTableData(); 
            showAlert("Thêm phân công thành công!");
        }
    }

    @FXML
    private void handleDeleteButton() {
        Assignment selected = assignmentTableView.getSelectionModel().getSelectedItem();
        if (selected != null && assignmentDAO.delete(selected.getAssignmentId())) {
            assignmentList.remove(selected);
            showAlert("Đã xóa phân công!");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}