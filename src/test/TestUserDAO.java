package test;

import grademanager.dao.UserDAO;
import grademanager.model.User;

import java.util.ArrayList;
import java.util.List;

public class TestUserDAO {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        List<User> list = new ArrayList<>();
        String[] teacher = {"gv01", "gv02", "gv03"};
        String[] name = {"Nguyen Van A", "Nguyen Van B", "Nguyen Van C"};
        for(int i=0; i<3; i++){
            list.add(new User(0, teacher[i], "123456", name[i], "TEACHER"));
        }

        for(User u: list){
            if(dao.insert(u)){
                System.out.println("Thêm giáo viên: " + u.getFullName() + " (ID=" + u.getUserId() + ")");
            } else {
                System.out.println("Thất bại khi thêm " + u.getFullName());
            }
        }
    }
}