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

    @FXML private ComboBox<Teacher> teacherComboBox;
    @FXML private ComboBox<SchoolClass> classComboBox;
    @FXML private ComboBox<Subject> subjectComboBox;

    @FXML private TableView<Assignment> assignmentTableView;
    @FXML private TableColumn<Assignment, String> teacherNameColumn;
    @FXML private TableColumn<Assignment, String> classNameColumn;
    @FXML private TableColumn<Assignment, String> subjectNameColumn;

    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    
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