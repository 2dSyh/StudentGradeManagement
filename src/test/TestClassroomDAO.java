package test;

import grademanager.dao.ClassroomDAO;
import grademanager.model.SchoolClass;

public class TestClassroomDAO {
    public static void main(String[] args) {
        ClassroomDAO dao = new ClassroomDAO();

        // 1. Thêm lớp mới
        SchoolClass c1 = new SchoolClass(1, "10A1", "2024-2025", 1);
        boolean inserted = dao.insert(c1);
        System.out.println("Insert: " + inserted);

        // 2. Lấy danh sách tất cả lớp
        System.out.println("Danh sách lớp:");
        for (SchoolClass c : dao.getAll()) {
            System.out.println(c.getClassId() + " - " + c.getClassName());
        }

        // 3. Sửa thông tin lớp
        c1.setClassName("10A1 - Updated");
        boolean updated = dao.update(c1);
        System.out.println("Update: " + updated);

        // 4. Lấy lớp theo ID
        SchoolClass found = dao.getById(1);
        System.out.println("Found: " + found.getClassName());

        // 5. Xóa lớp
        boolean deleted = dao.delete(1);
        System.out.println("Delete: " + deleted);
    }
}