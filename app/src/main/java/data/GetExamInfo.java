package data;

/**
 * Created by aarthy on 21/4/15.
 */
public class GetExamInfo {
    public int employee_id;
    public String time;
    public String venue;
    public String slot;
    public String exam;
    public String semester;

    public GetExamInfo(int employee_id, String time, String venue, String slot, String exam, String semester) {
        this.employee_id = employee_id;
        this.time = time;
        this.venue = venue;
        this.slot = slot;
        this.exam = exam;
        this.semester = semester;

    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
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
