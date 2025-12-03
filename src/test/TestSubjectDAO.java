package test;

import grademanager.dao.SubjectDAO;
import grademanager.model.Subject;
import java.util.ArrayList;
import java.util.List;


public class TestSubjectDAO {
    public static void main(String[] args) {
        SubjectDAO dao = new SubjectDAO();

        List<Subject> list = new ArrayList<>();
        String[] monHoc = {"Toan", "Ly",  "Hoa", "Anh", "Van", "Su", "Dia"};
        for(String s: monHoc){
            list.add(new Subject(0, s));
        }
        for(Subject sb: list){
            if(dao.insert(sb)){
                System.out.println("them " + sb.getSubjectName());
            }
            else{
                System.out.println("that bai");
            }
        }

        
    }
}