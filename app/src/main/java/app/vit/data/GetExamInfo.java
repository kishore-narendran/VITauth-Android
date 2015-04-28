package app.vit.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aarthy on 21/4/15.
 */
public class GetExamInfo {

    @SerializedName("employee_id")
    private int employeeID;
    @SerializedName("time")
    private String time;
    @SerializedName("venue")
    private String venue;
    @SerializedName("slot")
    private String slot;
    @SerializedName("exam")
    private String exam;
    @SerializedName("semester")
    private String semester;

    public GetExamInfo(int employeeID, String time, String venue, String slot, String exam, String semester) {
        this.employeeID = employeeID;
        this.time = time;
        this.venue = venue;
        this.slot = slot;
        this.exam = exam;
        this.semester = semester;

    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
