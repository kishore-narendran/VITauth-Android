package data;

public class ClassStudent {

    private String name;
    private String registerNumber;
    private boolean debarred;
    private String seat;

    private boolean attendance;
    private boolean malpractice;
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
