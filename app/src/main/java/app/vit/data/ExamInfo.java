package app.vit.data;

import com.google.gson.annotations.SerializedName;

public class ExamInfo {

    @SerializedName("result")
    private Result result;
    @SerializedName("classes")
    private Class[] classes;
    @SerializedName("exam")
    private String exam;
    @SerializedName("semester")
    private String semester;
    @SerializedName("slot")
    private String slot;
    @SerializedName("time")
    private String time;
    @SerializedName("venue")
    private String venue;

    public ExamInfo(String semester, String exam, String slot, String venue, String time, Class[] classes) {
        this.classes = classes;
        this.exam = exam;
        this.semester = semester;
        this.slot = slot;
        this.time = time;
        this.venue = venue;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
