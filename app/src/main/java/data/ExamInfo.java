package data;

public class ExamInfo {

    private Class[] classes;
    private String exam;
    private String semester;
    private String slot;
    private String time;
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
}
