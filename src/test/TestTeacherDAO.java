package test;

import grademanager.dao.TeacherDAO;
import grademanager.model.Teacher;

public class TestTeacherDAO {
    public static void main(String[] args) {
        TeacherDAO dao = new TeacherDAO();

        // 1. Thêm giáo viên mới
        Teacher t1 = new Teacher(1, "teacher01", "123456", "Nguyen Van A");
        boolean inserted = dao.insert(t1);
        System.out.println("Insert: " + inserted);

        // 2. Lấy tất cả giáo viên
        System.out.println("Danh sách giáo viên:");
        for (Teacher t : dao.getAll()) {
            System.out.println("ID:" + t.getUserId()
                    + " - Username:" + t.getUserName()
                    + " - Password:" + t.getPassWord()
                    + " - FullName:" + t.getFullName());
        }

        // 3. Cập nhật thông tin giáo viên
        t1.setUserName("teacher01_updated");
        t1.setPassWord("654321");
        t1.setFullName("Nguyen Van A - Updated");
        boolean updated = dao.update(t1);
        System.out.println("Update: " + updated);

        // 4. Lấy giáo viên theo ID
        Teacher found = dao.getById(1);
        if (found != null) {
            System.out.println("Found Teacher: " + found.getUserId()
                    + " - Username:" + found.getUserName()
                    + " - FullName:" + found.getFullName());
        }

        // 5. Xóa giáo viên
        boolean deleted = dao.delete(1);
        System.out.println("Delete: " + deleted);
    }
}