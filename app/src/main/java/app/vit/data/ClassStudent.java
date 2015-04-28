package app.vit.data;

import com.google.gson.annotations.SerializedName;

public class ClassStudent {

    @SerializedName("name")
    private String name;
    @SerializedName("register_number")
    private String registerNumber;
    @SerializedName("debarred")
    private boolean debarred;
    @SerializedName("seat")
    private String seat;

    @SerializedName("attendance")
    private boolean attendance;
    @SerializedName("malpractice")
    private boolean malpractice;
    @SerializedName("remarks")
    private String remarks;

    public ClassStudent(String name, String registerNumber, Boolean debarred, String seat) {
        this.name = name;
        this.registerNumber = registerNumber;
        this.debarred = debarred;
        this.seat = seat;

        attendance = false;
        malpractice = false;
        remarks = "";
    }

    public String getName() {
        return name;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public Boolean getDebarred() {
        return debarred;
    }

    public String getSeat() {
        return seat;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    public Boolean getMalpractice() {
        return malpractice;
    }

    public void setMalpractice(Boolean malpractice) {
        this.malpractice = malpractice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
