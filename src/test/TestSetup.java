package test;

import grademanager.dao.UserDAO;
import grademanager.model.User;

public class TestSetup {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        // SỬA: Role phải là "ADMIN" (viết hoa toàn bộ) để khớp với ràng buộc trong Database
        User admin = new User(0, "admin", "123", "Quản Trị Viên", "ADMIN");
        
        if(dao.insert(admin)) {
            System.out.println("Đã tạo tài khoản Admin thành công!");
            System.out.println("User: admin | Pass: 123");
        } else {
            System.out.println("Tạo thất bại.");
        }
    }
}