public class Enrollment {
    private String id;
    private String studentId;
    private String courseId;

    public Enrollment(String id, String studentId, String courseId){
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public String getId(){
        return this.id;
    }
    public String getStudentId(){
        return this.studentId;
    }
    public String getCourseId(){
        return this.courseId;
    }
}
