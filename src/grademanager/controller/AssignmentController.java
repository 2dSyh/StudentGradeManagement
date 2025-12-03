package grademanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

import grademanager.dao.*;
import grademanager.model.*;

public class AssignmentController {

    @FXML private ComboBox<String> teacherComboBox;
    @FXML private ComboBox<String> classComboBox;
    @FXML private ComboBox<String> subjectComboBox;

    @FXML private TableView<Assignment> assignmentTableView;
    @FXML private TableColumn<Assignment, String> teacherNameColumn;
    @FXML private TableColumn<Assignment, String> classNameColumn;
    @FXML private TableColumn<Assignment, String> subjectNameColumn;

    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();

    @FXML
    private void initialize() {
        assignmentList.addAll(assignmentDAO.getAll());
        assignmentTableView.setItems(assignmentList);

        //Lấy tên giáo viên
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teachers = teacherDAO.getAll();
        List<String> name =  new ArrayList<>();
        for(Teacher t: teachers){
            name.add(t.getFullName());
        }
        teacherComboBox.setItems(FXCollections.observableArrayList(name));
        
        //Lấy tên lớp
        ClassroomDAO classroomDAO = new ClassroomDAO();
        List<SchoolClass> classes = classroomDAO.getAll();
        List<String> classNames = new ArrayList<>();
        for(SchoolClass c: classes){
            classNames.add(c.getClassName());
        }
        classComboBox.setItems(FXCollections.observableArrayList(classNames));
        
        //Lấy tên môn học
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjects = subjectDAO.getAll();
        List<String> subjectNames = new ArrayList<>();
        for(Subject s: subjects){
            subjectNames.add(s.getSubjectName());
        }
        subjectComboBox.setItems(FXCollections.observableArrayList(subjectNames));
    }

    @FXML
    private void handleAddButton() {
        Assignment assignment = new Assignment(0,
            teacherComboBox.getSelectionModel().getSelectedIndex() + 1,
            classComboBox.getSelectionModel().getSelectedIndex() + 1,
            subjectComboBox.getSelectionModel().getSelectedIndex() + 1);
        if (assignmentDAO.insert(assignment)) {
            assignmentList.add(assignment);
        }
    }

    @FXML
    private void handleDeleteButton() {
        Assignment selected = assignmentTableView.getSelectionModel().getSelectedItem();
        if (selected != null && assignmentDAO.delete(selected.getAssignmentId())) {
            assignmentList.remove(selected);
        }
    }
}