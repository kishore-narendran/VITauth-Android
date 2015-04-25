package app.vit.data;

public class Class {

    private int classNumber;
    private String code;
    private String title;
    private ClassStudent classStudents[];

    public Class(Integer classNumber, String code, String title, ClassStudent[] classStudents) {
        this.classNumber = classNumber;
        this.code = code;
        this.title = title;
        this.classStudents = classStudents;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public ClassStudent[] getClassStudents() {
        return classStudents;
    }
}
