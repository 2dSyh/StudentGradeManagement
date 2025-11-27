package grademanager.model;

public class Admin extends User{
    public Admin(){
        super();
        setRole("Admin");
    }

    public Admin(int userId, String username, String password, String fullName){
        super(userId, username, password, fullName, "Admin");
    }

    @Override
    public String toString(){
        return "Admin: " + getFullName() + "(" + getUserName() + ")";
    }
}
