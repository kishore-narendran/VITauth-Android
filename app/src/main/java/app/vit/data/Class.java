package app.vit.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class {

    @Expose
    @SerializedName("class_number")
    private int classNumber;

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("students")
    private Student students[];

    public Class(Integer classNumber, String code, String title, Student[] students) {
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

    public Student[] getStudents() {
        return students;
    }
}
