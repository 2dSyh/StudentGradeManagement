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

    }
}