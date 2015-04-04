package data;

public class ClassStudents {

    private String registerNumber;
    private Boolean debarred;
    private String seat;

    private Boolean attendance;
    private Boolean malpractice;
    private String remarks;

    public ClassStudents(String registerNumber, Boolean debarred, String seat) {
        this.registerNumber = registerNumber;
        this.debarred = debarred;
        this.seat = seat;

        attendance = false;
        malpractice = false;
        remarks = "";
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