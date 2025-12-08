package grademanager.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
<<<<<<< HEAD
import java.util.List;
=======

>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
import grademanager.dao.*;
import grademanager.model.*;

public class AssignmentController {

<<<<<<< HEAD
=======
    // SỬA: Dùng Object thực thay vì String để lấy ID chính xác
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
    @FXML private ComboBox<Teacher> teacherComboBox;
    @FXML private ComboBox<SchoolClass> classComboBox;
    @FXML private ComboBox<Subject> subjectComboBox;

    @FXML private TableView<Assignment> assignmentTableView;
    @FXML private TableColumn<Assignment, Integer> teacherNameColumn; // Tạm thời để hiện ID, muốn hiện tên cần join bảng hoặc map dữ liệu
    @FXML private TableColumn<Assignment, Integer> classNameColumn;
    @FXML private TableColumn<Assignment, Integer> subjectNameColumn;

    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    
<<<<<<< HEAD
    private List<Teacher> teachers;
    private List<SchoolClass> classes;
    private List<Subject> subjects;

    @FXML
    private void initialize() {
        loadDataForComboBoxes();
        setupTableColumns();
        loadTableData();
    }

    private void loadDataForComboBoxes() {
        TeacherDAO teacherDAO = new TeacherDAO();
        teachers = teacherDAO.getAll();
        teacherComboBox.setItems(FXCollections.observableArrayList(teachers));
        teacherComboBox.setConverter(new StringConverter<Teacher>() {
            @Override public String toString(Teacher t) { return t != null ? t.getFullName() : ""; }
            @Override public Teacher fromString(String string) { return null; }
        });

        ClassroomDAO classroomDAO = new ClassroomDAO();
        classes = classroomDAO.getAll();
        classComboBox.setItems(FXCollections.observableArrayList(classes));
        classComboBox.setConverter(new StringConverter<SchoolClass>() {
            @Override public String toString(SchoolClass c) { return c != null ? c.getClassName() : ""; }
            @Override public SchoolClass fromString(String string) { return null; }
        });

        SubjectDAO subjectDAO = new SubjectDAO();
        subjects = subjectDAO.getAll();
        subjectComboBox.setItems(FXCollections.observableArrayList(subjects));
        subjectComboBox.setConverter(new StringConverter<Subject>() {
            @Override public String toString(Subject s) { return s != null ? s.getSubjectName() : ""; }
            @Override public Subject fromString(String string) { return null; }
        });
    }

    private void setupTableColumns() {
        teacherNameColumn.setCellValueFactory(cell -> {
            int id = cell.getValue().getTeacherId();
            String name = teachers.stream().filter(t -> t.getUserId() == id)
                          .findFirst().map(Teacher::getFullName).orElse("ID: " + id);
            return new SimpleStringProperty(name);
        });

        classNameColumn.setCellValueFactory(cell -> {
            int id = cell.getValue().getClassId();
            String name = classes.stream().filter(c -> c.getClassId() == id)
                          .findFirst().map(SchoolClass::getClassName).orElse("ID: " + id);
            return new SimpleStringProperty(name);
        });

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
=======
    private TeacherDAO teacherDAO = new TeacherDAO();
    private ClassroomDAO classroomDAO = new ClassroomDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

    @FXML
    private void initialize() {
        // 1. Setup TableView (Cột hiện tại đang bind vào ID, trong thực tế nên convert sang tên)
        teacherNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getTeacherId()).asObject());
        classNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getClassId()).asObject());
        subjectNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getSubjectId()).asObject());
        
        loadData();
        setupComboBoxes();
    }

    private void loadData() {
        assignmentList.clear();
        assignmentList.addAll(assignmentDAO.getAll());
        assignmentTableView.setItems(assignmentList);
    }

    // TỐI ƯU: Hiển thị tên nhưng giữ giá trị là Object để lấy ID chuẩn
    private void setupComboBoxes() {
        // Teacher
        teacherComboBox.setItems(FXCollections.observableArrayList(teacherDAO.getAll()));
        teacherComboBox.setConverter(new StringConverter<Teacher>() {
            @Override
            public String toString(Teacher object) { return object == null ? "" : object.getFullName(); }
            @Override
            public Teacher fromString(String string) { return null; }
        });

        // Class
        classComboBox.setItems(FXCollections.observableArrayList(classroomDAO.getAll()));
        classComboBox.setConverter(new StringConverter<SchoolClass>() {
            @Override
            public String toString(SchoolClass object) { return object == null ? "" : object.getClassName(); }
            @Override
            public SchoolClass fromString(String string) { return null; }
        });

        // Subject
        subjectComboBox.setItems(FXCollections.observableArrayList(subjectDAO.getAll()));
        subjectComboBox.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject object) { return object == null ? "" : object.getSubjectName(); }
            @Override
            public Subject fromString(String string) { return null; }
        });
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
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

<<<<<<< HEAD
        Assignment assignment = new Assignment(0, t.getUserId(), c.getClassId(), s.getSubjectId());
        
        if (assignmentDAO.insert(assignment)) {
            loadTableData(); 
            showAlert("Thêm phân công thành công!");
=======
        // SỬA: Lấy ID từ Object (An toàn hơn logic index + 1)
        Assignment assignment = new Assignment(0, t.getUserId(), c.getClassId(), s.getSubjectId());
        
        if (assignmentDAO.insert(assignment)) {
            assignmentList.add(assignment);
            showAlert("Thêm phân công thành công!");
        } else {
            showAlert("Lỗi khi thêm phân công (có thể do lỗi DB)!");
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
        }
    }

    @FXML
    private void handleDeleteButton() {
        Assignment selected = assignmentTableView.getSelectionModel().getSelectedItem();
<<<<<<< HEAD
        if (selected != null && assignmentDAO.delete(selected.getAssignmentId())) {
            assignmentList.remove(selected);
            showAlert("Đã xóa phân công!");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
=======
        if (selected != null) {
            if (assignmentDAO.delete(selected.getAssignmentId())) {
                assignmentList.remove(selected);
                showAlert("Đã xóa thành công!");
            }
        } else {
            showAlert("Vui lòng chọn dòng cần xóa!");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
>>>>>>> f1bfd79888f9083ed8b95dffb1c5c7460f02e4f3
        alert.show();
    }
}