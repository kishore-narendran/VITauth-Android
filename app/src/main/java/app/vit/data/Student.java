package app.vit.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("register_number")
    private String registerNumber;

    @Expose
    @SerializedName("fingerprint")
    private String fingerprint;

    @Expose
    @SerializedName("debarred")
    private boolean debarred;

    @Expose
    @SerializedName("seat")
    private String seat;

    private boolean attendance;

    private boolean malpractice;

    private String remarks;

    public Student(String name, String registerNumber, String fingerprint, boolean debarred, String seat) {
        this.name = name;
        this.registerNumber = registerNumber;
        this.fingerprint = fingerprint;
        this.debarred = debarred;
        this.seat = seat;

        this.attendance = false;
        this.malpractice = false;
        this.remarks = "";
    }

    public String getName() {
        return name;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public boolean isDebarred() {
        return debarred;
    }

    public String getSeat() {
        return seat;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = !debarred && attendance;
    }

    public boolean isMalpractice() {
        return malpractice;
    }

    public void setMalpractice(boolean malpractice) {
        this.malpractice = malpractice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
