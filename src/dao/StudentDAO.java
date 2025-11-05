package dao;

import model.Students;
import util.DatabaseConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class StudentDAO implements DAO<Students>{
    @Override
    public boolean insert(Students s){
        String sql = "INSERT INTO Students(student_id, full_name, date_of_birth, gender, address, class_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, s.getStudentId());
            pstmt.setString(2, s.getFullName());
            pstmt.setDate(3, Date.valueOf(s.getDateOfBirth()));
            pstmt.setString(4, s.getGender());
            pstmt.setString(5, s.getAddress());
            pstmt.setInt(6, s.getClassroom().getClassId());
            return pstmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("Loi khi cap nhat hoc sinh: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean update(Students s){
        String sql = "UPDATE Students SET full_name = ?, date_of_birth = ?, gender = ?, address = ?, class_id = ? WHERE student_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(6, s.getStudentId());
            pstmt.setString(1, s.getFullName());
            pstmt.setDate(2, Date.valueOf(s.getDateOfBirth()));
            pstmt.setString(3, s.getGender());
            pstmt.setString(4, s.getAddress());
            pstmt.setInt(5, s.getClassroom().getClassId());
            return pstmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("loi khi sua hoc sinh: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean delete(int id){
        String sql = "DELETE FROM Students WHERE Student_id = ?";
        try(Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("loi khi xoa hoc sinh: " + e.getMessage());
            return false;
        }
    }
    @Override
    public Students getById(int id){
        String sql = "SELECT * FROM Students WHERE Student_id = ?";
        try(Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Students(rs.getInt("student_id"), rs.getString("full_name"), rs.getDate("date_of_birth").toLocalDate(), rs.getString("gender"), rs.getString("address"), null);
            }
        }catch(SQLException e){
            System.out.println("loi khi tim kiem: " + e.getMessage());
        }
        return null;
    }
    @Override
    public List<Students> getAll(){
        List<Students> list = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try(Connection conn = DatabaseConnector.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                list.add(new Students(rs.getInt("student_id"),rs.getString("full_name"),rs.getDate("date_of_birth").toLocalDate(),rs.getString("gender"),rs.getString("address"),null));
            }
            return list;
        }catch(SQLException e){
            System.out.println("loi khi lay thong tin: " + e.getMessage());
        }
        return list;
    }
}
