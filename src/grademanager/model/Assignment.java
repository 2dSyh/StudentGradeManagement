package grademanager.model;

public class Assignment{
    private int assignmentId;
    private int teacherId;
    private int classId;
    private int subjectId;

    public Assignment() {}

    public Assignment(int assignmentId, int teacherId, int classId, int subjectId){
        this.assignmentId = assignmentId;
        this.teacherId = teacherId;
        this.classId = classId;
        this.subjectId = subjectId;
    }

    public void setAssignmentId(int assignmentId){ this.assignmentId = assignmentId;}
    public int getAssignmentId(){ return assignmentId;}

    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    public int getTeacherId() { return teacherId; }

    public void setClassId(int classId) { this.classId = classId; }
    public int getClassId() { return classId; }

    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    public int getSubjectId() { return subjectId; }

    @Override
    public String toString(){
        return "Assignment{ID: " + assignmentId +
        ", TeacherId: " + teacherId +
        ", Class: " + classId +
        ", SubjectId: " + subjectId +
        "}";
    }
}