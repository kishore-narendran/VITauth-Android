package data;

import java.util.List;

public class Class {
    private Integer classNumber;
    private List classStudents;

    public Class(Integer classNumber, List classStudents) {
        this.classNumber = classNumber;
        this.classStudents = classStudents;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public List getClassStudents() {
        return classStudents;
    }
}
