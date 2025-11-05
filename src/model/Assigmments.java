package model;

public class Assigmments{
    private int assigmmentId;
    private Users teacher;
    private Classes classroom;
    private Subjects subject;

    public Assigmments(){}

    public Assigmments(int assigmmentId, Users teacher, Classes classroom, Subjects subject){
        this.assigmmentId = assigmmentId;
        this.teacher = teacher;
        this.classroom = classroom;
        this.subject = subject;
    }

    public void setAssigmmentId(int assigmmentId){ this.assigmmentId = assigmmentId;}
    public int getAssigmmentId(){ return assigmmentId;}

    public void setTeacher(Users teacher){ this.teacher = teacher;}
    public Users getTeacher(){ return teacher;}

    public void setClassroom(Classes classroom){ this.classroom = classroom;}
    public Classes getClassroom(){ return classroom;}

    public void setSubject(Subjects subject){ this.subject = subject;}
    public Subjects getSubject(){ return subject;}

    @Override
    public String toString(){
        return "Assigmment{ID: " + assigmmentId +
        ", Teacher: " + (teacher!=null?teacher.getFullName():"N/A") +
        ", Class: " + (classroom!=null?classroom.getClassName():"N/A") +
        ", Subject: " + (subject!=null?subject.getSubjectName():"N/A") +
        "}";
    }
}