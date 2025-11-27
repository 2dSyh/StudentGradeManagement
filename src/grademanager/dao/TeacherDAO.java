package grademanager.dao;

import grademanager.model.Teacher;
import grademanager.util.DatabaseConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO implements DAO<Teacher> {
    

    @Override
    public boolean insert(Teacher t){

        String sql = "INSERT INTO Users(user_id, username, password, full_name, role) VALUES (?,?,?,?,?)";

        try(Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, t.getUserId());
            pstmt.setString(2, t.getUserName());
            pstmt.setString(3, t.getPassWord());
            pstmt.setString(4, t.getFullName());
            pstmt.setString(5, "TEACHER");

            return pstmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("loi khi them giao vien: " + e.getMessage());
            return false;
        }
    }



    public boolean update(Teacher t){
        String sql = "UPDATE Users SET username = ?, password = ?, full_name = ?, role = ? WHERE user_id = ?";

        try(Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(5, t.getUserId());
            pstmt.setString(1, t.getUserName());
            pstmt.setString(2, t.getPassWord());
            pstmt.setString(3, t.getFullName());
            pstmt.setString(4, "TEACHER");

            return pstmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("loi khi sua giao vien: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id){

        String sql = "DELETE FROM Users WHERE user_id = ?";
        try(Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("loi khi xoa giao vien: " + e.getMessage());
            return false;
        }
    }

    public Teacher getById(int id){
        String sql = "SELECT * FROM Users WHERE user_id = ?";

        try(Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Teacher(id, rs.getString("username"), rs.getString("password"), rs.getString("full_name"));
            }
        }catch(SQLException e){
            System.out.println("loi khi lay tim kiem giao vien: " + e.getMessage());
        }
        return null;
    }


    public List<Teacher> getAll(){
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE role = 'TEACHER'";
        try(Connection conn = DatabaseConnector.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                list.add(new Teacher(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("full_name")));
            }

            return list;
        }catch(SQLException e){
            System.out.println("loi khi lay thong tin: " + e.getMessage());
        }
        return list;
    }
}
