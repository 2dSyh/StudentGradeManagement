package grademanager.model;

public class Admin extends User{
    public Admin(){
        super();
        setRole("ADMIN");
    }

    public Admin(int userId, String username, String password, String fullName){
        super(userId, username, password, fullName, "ADMIN");
    }

    @Override
    public String toString(){
        return "Admin: " + getFullName() + "(" + getUserName() + ")";
    }
}
