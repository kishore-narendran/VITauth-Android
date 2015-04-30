package app.vit.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInfo {

    @Expose
    @SerializedName("employee_id")
    private int employeeID;

    @Expose
    @SerializedName("time")
    private String time;

    @Expose
    @SerializedName("venue")
    private String venue;

    @Expose
    @SerializedName("slot")
    private String slot;

    @Expose
    @SerializedName("exam")
    private String exam;

    @Expose
    @SerializedName("semester")
    private String semester;

    public LoginInfo(int employeeID, String time, String venue, String slot, String exam, String semester) {
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

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }

    public String getSlot() {
        return slot;
    }

    public String getExam() {
        return exam;
    }

    public String getSemester() {
        return semester;
    }
}
