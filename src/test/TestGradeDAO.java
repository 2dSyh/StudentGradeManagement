package test;

import grademanager.dao.GradeDAO;
import grademanager.model.Grade;

public class TestGradeDAO {
    public static void main(String[] args) {
        GradeDAO dao = new GradeDAO();

        // 1. Thêm mới Grade
        Grade g1 = new Grade(1, 101, 201, "Midterm", 7.5); // giả định ID
        boolean inserted = dao.insert(g1);
        System.out.println("Insert: " + inserted);

        // 2. Lấy tất cả Grade
        System.out.println("Danh sách điểm:");
        for (Grade g : dao.getAll()) {
            System.out.println("GradeID:" + g.getGradeId()
                    + " - Student:" + g.getStudent()
                    + " - Subject:" + g.getSubject()
                    + " - Type:" + g.getGradeType()
                    + " - Score:" + g.getScore());
        }

        // 3. Cập nhật Grade
        g1.setScore(8.0); // đổi điểm
        boolean updated = dao.update(g1);
        System.out.println("Update: " + updated);

        // 4. Lấy Grade theo ID
        Grade found = dao.getById(1);
        if (found != null) {
            System.out.println("Found Grade: " + found.getGradeId()
                    + " - Score:" + found.getScore());
        }

        // 5. Xóa Grade
        boolean deleted = dao.delete(1);
        System.out.println("Delete: " + deleted);
    }
}