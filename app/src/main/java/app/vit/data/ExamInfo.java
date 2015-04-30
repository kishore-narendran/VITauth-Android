package app.vit.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamInfo {

    @Expose
    @SerializedName("result")
    private Result result;

    @Expose
    @SerializedName("classes")
    private Class[] classes;

    @Expose
    @SerializedName("exam")
    private String exam;

    @Expose
    @SerializedName("semester")
    private String semester;

    @Expose
    @SerializedName("slot")
    private String slot;

    @Expose
    @SerializedName("time")
    private String time;

    @Expose
    @SerializedName("venue")
    private String venue;

    public ExamInfo(Result result, Class[] classes, String exam, String semester, String slot, String time, String venue) {
        this.result = result;
        this.classes = classes;
        this.exam = exam;
        this.semester = semester;
        this.slot = slot;
        this.time = time;
        this.venue = venue;
    }

    public Result getResult() {
        return result;
    }

    public Class[] getClasses() {
        return classes;
    }

    public String getExam() {
        return exam;
    }

    public String getSemester() {
        return semester;
    }

    public String getSlot() {
        return slot;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }
}
