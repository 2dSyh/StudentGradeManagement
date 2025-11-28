package test;

import grademanager.dao.AssignmentDAO;
import grademanager.model.Assignment;

public class TestAssignmentDAO {
    public static void main(String[] args) {
        AssignmentDAO dao = new AssignmentDAO();

        // 1. Thêm mới Assignment
        Assignment a1 = new Assignment(1, 101, 201, 301); // giả định ID
        boolean inserted = dao.insert(a1);
        System.out.println("Insert: " + inserted);

        // 2. Lấy tất cả Assignment
        System.out.println("Danh sách Assignment:");
        for (Assignment a : dao.getAll()) {
            System.out.println(a.getAssignmentId() + " - Teacher:" + a.getTeacherId()
                    + " - Class:" + a.getClassId() + " - Subject:" + a.getSubjectId());
        }

        // 3. Cập nhật Assignment
        a1.setTeacherId(102); // đổi giáo viên
        boolean updated = dao.update(a1);
        System.out.println("Update: " + updated);

        // 4. Lấy Assignment theo ID
        Assignment found = dao.getById(1);
        if (found != null) {
            System.out.println("Found Assignment: " + found.getAssignmentId()
                    + " - Teacher:" + found.getTeacherId());
        }

        // 5. Xóa Assignment
        boolean deleted = dao.delete(1);
        System.out.println("Delete: " + deleted);

    }
}