package test;

import grademanager.dao.ClassroomDAO;
import grademanager.model.SchoolClass;

public class TestClassroomDAO {
    public static void main(String[] args) {
        ClassroomDAO dao = new ClassroomDAO();

        // Thêm lớp mới với teacher_id đã tồn tại trong DB
        SchoolClass c1 = new SchoolClass(0, "10A", "2024-2025", 1);
        if (dao.insert(c1)) {
            System.out.println("Thêm lớp: " + c1.getClassName() + " (ID=" + c1.getClassId() + ")");
        } else {
            System.out.println("Thêm lớp 10A thất bại!");
        }

        SchoolClass c2 = new SchoolClass(0, "10B", "2024-2025", 2);
        if (dao.insert(c2)) {
            System.out.println("Thêm lớp: " + c2.getClassName() + " (ID=" + c2.getClassId() + ")");
        } else {
            System.out.println("Thêm lớp 10B thất bại!");
        }

        SchoolClass c3 = new SchoolClass(0, "10C", "2024-2025", 3);
        if (dao.insert(c3)) {
            System.out.println("Thêm lớp: " + c3.getClassName() + " (ID=" + c3.getClassId() + ")");
        } else {
            System.out.println("Thêm lớp 10C thất bại!");
        }
    }
}