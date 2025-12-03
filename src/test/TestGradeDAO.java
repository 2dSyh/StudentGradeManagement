package test;

import grademanager.dao.GradeDAO;
import grademanager.model.Grade;

public class TestGradeDAO {
    public static void main(String[] args) {
        GradeDAO dao = new GradeDAO();

        // 1. Thêm mới Grade
        Grade g1 = new Grade(0, 1, 1, "midterm", 7.5);
        boolean inserted = dao.insert(g1);
        System.out.println("Insert: " + inserted);

        Grade g2 = new Grade(0, 1, 1, "final", 9.0);
        boolean inserted2 = dao.insert(g2);
        System.out.println("Insert: " + inserted2);

        Grade g3 = new Grade(0, 1, 1, "assignment", 8.0);
        boolean inserted3 = dao.insert(g3);
        System.out.println("Insert: " + inserted3);
        
    }
}