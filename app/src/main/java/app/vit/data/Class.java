package app.vit.data;

import com.google.gson.annotations.SerializedName;

public class Class {

    @SerializedName("class_number")
    private int classNumber;
    @SerializedName("code")
    private String code;
    @SerializedName("title")
    private String title;
    @SerializedName("students")
    private ClassStudent students[];

    public Class(Integer classNumber, String code, String title, ClassStudent[] students) {
        this.classNumber = classNumber;
        this.code = code;
        this.title = title;
        this.students = students;
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

    public ClassStudent[] getStudents() {
        return students;
    }
}
