package data;

import java.util.List;

public class ExamInfo {

    private List classes;
    private String exam;
    private String semester;
    private String slot;
    private String time;
    private String venue;

    public ExamInfo(String semester, String exam, String slot, String venue, String time, List classes) {
        this.classes = classes;
        this.exam = exam;
        this.semester = semester;
        this.slot = slot;
        this.time = time;
        this.venue = venue;
    }

    public List getClasses() {
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
