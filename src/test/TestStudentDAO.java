package test;

import grademanager.dao.StudentDAO;
import grademanager.model.Student;

import java.time.LocalDate;

public class TestStudentDAO {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        // 1. Thêm mới Student
        Student s1 = new Student(1, "Nguyen Van A", LocalDate.of(2007, 5, 20), "Nam", "Ha Noi", 101);
        boolean inserted = dao.insert(s1);
        System.out.println("Insert: " + inserted);

        // 2. Lấy tất cả Student
        System.out.println("Danh sách học sinh:");
        for (Student s : dao.getAll()) {
            System.out.println("ID:" + s.getStudentId()
                    + " - Name:" + s.getFullName()
                    + " - DOB:" + s.getDateOfBirth()
                    + " - Gender:" + s.getGender()
                    + " - Address:" + s.getAddress()
                    + " - ClassID:" + s.getClassroom());
        }

        // 3. Cập nhật Student
        s1.setFullName("Nguyen Van A - Updated");
        s1.setAddress("Hai Phong");
        boolean updated = dao.update(s1);
        System.out.println("Update: " + updated);

        // 4. Lấy Student theo ID
        Student found = dao.getById(1);
        if (found != null) {
            System.out.println("Found Student: " + found.getStudentId()
                    + " - Name:" + found.getFullName()
                    + " - Address:" + found.getAddress());
        }

        // 5. Xóa Student
        boolean deleted = dao.delete(1);
        System.out.println("Delete: " + deleted);
    }
}