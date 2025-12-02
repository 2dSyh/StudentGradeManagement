package grademanager.controller;

import grademanager.dao.GradeDAO;
import grademanager.model.Grade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

public class GradeManagementController {

    @FXML private ComboBox<String> classComboBox;
    @FXML private ComboBox<String> subjectComboBox;
    @FXML private Button loadButton;
    @FXML private Button saveButton;
    @FXML private Button avgButton;       // nút tính điểm trung bình môn
    @FXML private Button gpaButton;       // nút tính GPA

    @FXML private TableView<Grade> gradeTableView;
    @FXML private TableColumn<Grade, Integer> studentIdColumn;
    @FXML private TableColumn<Grade, String> studentNameColumn;
    @FXML private TableColumn<Grade, Double> midtermScoreColumn;
    @FXML private TableColumn<Grade, Double> finalScoreColumn;

    @FXML private Label avgLabel;         // hiển thị điểm trung bình môn
    @FXML private Label gpaLabel;         // hiển thị GPA

    private ObservableList<Grade> gradeList = FXCollections.observableArrayList();
    private GradeDAO gradeDAO = new GradeDAO();

    @FXML
    private void initialize() {
        // Gắn dữ liệu mẫu cho ComboBox
        classComboBox.setItems(FXCollections.observableArrayList("10A1", "11B2", "12C3"));
        subjectComboBox.setItems(FXCollections.observableArrayList("Toán", "Văn", "Anh"));

        // Gắn dữ liệu cột
        studentIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStudent()).asObject());
        studentNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty("HS#" + cellData.getValue().getStudent()));

        midtermScoreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getScore()).asObject());
        finalScoreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getScore()).asObject());

        // Cho phép chỉnh sửa trực tiếp điểm
        midtermScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        midtermScoreColumn.setOnEditCommit(event -> {
            Grade g = event.getRowValue();
            g.setScore(event.getNewValue());
        });

        finalScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        finalScoreColumn.setOnEditCommit(event -> {
            Grade g = event.getRowValue();
            g.setScore(event.getNewValue());
        });

        gradeTableView.setItems(gradeList);
        gradeTableView.setEditable(true);
    }

    @FXML
    private void handleClassSelection() {
        System.out.println("Lớp được chọn: " + classComboBox.getValue());
    }

    @FXML
    private void handleSubjectSelection() {
        System.out.println("Môn được chọn: " + subjectComboBox.getValue());
    }

    @FXML
    private void handleLoadGrades() {
        // Tải dữ liệu mẫu (thực tế sẽ lấy từ DAO/database)
        gradeList.clear();
        gradeList.add(new Grade(1, 101, 1, "Midterm", 7.5));
        gradeList.add(new Grade(2, 101, 1, "Final", 8.0));
        gradeList.add(new Grade(3, 101, 1, "Assignment", 9.0));
    }

    @FXML
    private void handleSaveChanges() {
        // Lưu dữ liệu (thực tế sẽ gọi DAO để update database)
        for (Grade g : gradeList) {
            System.out.println("Lưu điểm: " + g.getStudent() + " - " + g.getScore());
        }
    }

    // ✅ Tính điểm trung bình môn cho sinh viên được chọn
    @FXML
    private void handleCalculateAverage() {
        Grade selected = gradeTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            double avg = gradeDAO.getWeightedAverageBySubject(selected.getStudent(), selected.getSubject());
            avgLabel.setText("Điểm TB môn: " + String.format("%.2f", avg));
        } else {
            avgLabel.setText("Chọn một sinh viên để tính điểm TB môn");
        }
    }

    // ✅ Tính GPA cho sinh viên được chọn
    @FXML
    private void handleCalculateGPA() {
        Grade selected = gradeTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            double gpa = gradeDAO.getOverallAverage(selected.getStudent());
            gpaLabel.setText("GPA: " + String.format("%.2f", gpa));
        } else {
            gpaLabel.setText("Chọn một sinh viên để tính GPA");
        }
    }
}