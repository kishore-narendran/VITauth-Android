package app.vit.data;

public class Class {

    private int class_number;
    private String code;
    private String title;
    private ClassStudent students[];

    public Class(Integer class_number, String code, String title, ClassStudent[] students) {
        this.class_number = class_number;
        this.code = code;
        this.title = title;
        this.students = students;
    }

    public Integer getClass_number() {
        return class_number;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public ClassStudent[] getStudents() {
        return students;
    }
}
