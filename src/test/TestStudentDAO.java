package test;

import grademanager.dao.StudentDAO;
import grademanager.model.Student;

import java.time.LocalDate;

public class TestStudentDAO {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        // 1. Thêm mới Student
        Student s1 = new Student(0, "Nguyen Truong Phuoc", LocalDate.of(2006, 4, 22), "Nam", "Hai Duong", 1);
        boolean inserted = dao.insert(s1);
        System.out.println("Insert: " + inserted);

        Student s2 = new Student(0, "Nguyen Duc Hoang Nam", LocalDate.of(2006, 12, 2), "Nam", "Ha Noi", 1);
        boolean inserted2 = dao.insert(s2);
        System.out.println("Insert: " + inserted2);

        Student s3 = new Student(0, "Huynh Quang Thinh", LocalDate.of(2006, 6, 20), "Nam", "Ha Noi", 1);
        boolean inserted3 = dao.insert(s3);
        System.out.println("Insert: " + inserted3);
    }
}