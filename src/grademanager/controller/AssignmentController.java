package grademanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import grademanager.dao.*;
import grademanager.model.*;

public class AssignmentController {

    // SỬA: Dùng Object thực thay vì String để lấy ID chính xác
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
            assignmentList.add(assignment);
            showAlert("Thêm phân công thành công!");
        } else {
            showAlert("Lỗi khi thêm phân công (có thể do lỗi DB)!");
        }
    }

    @FXML
    private void handleDeleteButton() {
        Assignment selected = assignmentTableView.getSelectionModel().getSelectedItem();
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
        alert.show();
    }
}