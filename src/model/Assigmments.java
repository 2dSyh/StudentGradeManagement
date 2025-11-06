package model;

public class Assigmments{
    private int assigmmentId;
    private int teacherId;
    private int classId;
    private int subjectId;

    public Assigmments(){}

    public Assigmments(int assigmmentId, int teacherId, int classId, int subjectId){
        this.assigmmentId = assigmmentId;
        this.teacherId = teacherId;
        this.classId = classId;
        this.subjectId = subjectId;
    }

    public void setAssigmmentId(int assigmmentId){ this.assigmmentId = assigmmentId;}
    public int getAssigmmentId(){ return assigmmentId;}

    public void setTeacherId(int teacherId){ this.teacherId = teacherId;}
    public int getTeacherId(){ return teacherId;}

    public void setClassId(int classId){ this.classId = classId;}
    public int getClassId(){ return classId;}

    public void setSubjectId(int subjectId){ this.subjectId = subjectId;}
    public int getSubjectId(){ return subjectId;}

    @Override
    public String toString(){
        return "Assigmment{ID: " + assigmmentId +
        ", TeacherId: " + teacherId +
        ", Class: " + classId +
        ", SubjectId: " + subjectId +
        "}";
    }
}