package data;

public class Class {

    private int classNumber;
    private ClassStudent classStudents[];

    public Class(Integer classNumber, ClassStudent[] classStudents) {
        this.classNumber = classNumber;
        this.classStudents = classStudents;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public ClassStudent[] getClassStudents() {
        return classStudents;
    }
}
