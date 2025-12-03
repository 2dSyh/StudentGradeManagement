package grademanager.controller;

import grademanager.dao.ClassroomDAO;
import grademanager.model.SchoolClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import grademanager.model.*;
import grademanager.dao.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolClassManagementController {

    @FXML private TableView<SchoolClass> classTableView;
    @FXML private TableColumn<SchoolClass, Integer> idColumn;
    @FXML private TableColumn<SchoolClass, String> classNameColumn;
    @FXML private TableColumn<SchoolClass, String> academicYearColumn;
    @FXML private TableColumn<SchoolClass, String> teacherNameColumn;

    @FXML private TextField classNameField;
    @FXML private TextField academicYearField;
    @FXML private ComboBox<String> homeroomTeacherComboBox;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    private ClassroomDAO classroomDAO = new ClassroomDAO();
    private ObservableList<SchoolClass> classList = FXCollections.observableArrayList();
    private SchoolClass selectedClass;

    @FXML
    private void initialize() {
        // Gắn dữ liệu cột
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getClassId()).asObject());
        classNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getClassName()));
        academicYearColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAcademicYear()));
        teacherNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty("GV#" + cellData.getValue().getTeacherId()));

        // Load dữ liệu từ DB
        loadClasses();

        // TODO: thay bằng TeacherDAO.getAllNames() nếu có

        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teachers = teacherDAO.getAll();
        List<String> name =  new ArrayList<>();
        for(Teacher t: teachers){
            name.add(t.getFullName());
        }
        homeroomTeacherComboBox.setItems(FXCollections.observableArrayList(name));

        // Lắng nghe chọn dòng trong bảng
        classTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedClass = newSel;
                showClassDetails(newSel);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });
    }

    private void loadClasses() {
        classList.setAll(classroomDAO.getAll());
        classTableView.setItems(classList);
    }

    private void showClassDetails(SchoolClass c) {
        classNameField.setText(c.getClassName());
        academicYearField.setText(c.getAcademicYear());
        homeroomTeacherComboBox.setValue("GV#" + c.getTeacherId());
    }

    @FXML
    private void handleAddButton() {
        SchoolClass c = new SchoolClass(
            0,
            classNameField.getText(),
            academicYearField.getText(),
            homeroomTeacherComboBox.getSelectionModel().getSelectedIndex() + 1 // giả định ID GV
        );
        if (classroomDAO.insert(c)) {
            loadClasses();
            clearForm();
        }
    }

    @FXML
    private void handleUpdateButton() {
        if (selectedClass != null) {
            selectedClass.setClassName(classNameField.getText());
            selectedClass.setAcademicYear(academicYearField.getText());
            selectedClass.setTeacherId(homeroomTeacherComboBox.getSelectionModel().getSelectedIndex() + 1);
            if (classroomDAO.update(selectedClass)) {
                loadClasses();
                clearForm();
            }
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (selectedClass != null) {
            if (classroomDAO.delete(selectedClass.getClassId())) {
                loadClasses();
                clearForm();
            }
        }
    }

    @FXML
    private void handleClearButton() {
        clearForm();
    }

    private void clearForm() {
        classNameField.clear();
        academicYearField.clear();
        homeroomTeacherComboBox.getSelectionModel().clearSelection();
        selectedClass = null;
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
}