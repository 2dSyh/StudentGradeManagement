package test;

import grademanager.dao.SubjectDAO;
import grademanager.model.Subject;

public class TestSubjectDAO {
    public static void main(String[] args) {
        SubjectDAO dao = new SubjectDAO();

        // 1. Thêm môn học mới
        Subject s1 = new Subject(1, "Toán học");
        boolean inserted = dao.insert(s1);
        System.out.println("Insert: " + inserted);

        // 2. Lấy tất cả môn học
        System.out.println("Danh sách môn học:");
        for (Subject s : dao.getAll()) {
            System.out.println("ID: " + s.getSubjectId() + " - Tên: " + s.getSubjectName());
        }

        // 3. Cập nhật tên môn học
        s1.setSubjectName("Toán nâng cao");
        boolean updated = dao.update(s1);
        System.out.println("Update: " + updated);

        // 4. Lấy môn học theo ID
        Subject found = dao.getById(1);
        if (found != null) {
            System.out.println("Found: " + found.getSubjectId() + " - " + found.getSubjectName());
        }

        // 5. Xóa môn học
        boolean deleted = dao.delete(1);
        System.out.println("Delete: " + deleted);
    }
}