import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    Connection connection;
    public DatabaseManager(Connection connection){
        this.connection = connection;
    }

    public void createStudentsTable() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS students(" +
                "s_id VARCHAR(20) PRIMARY KEY, " +
                "s_name VARCHAR(100) NOT NULL, " +
                "age INT NOT NULL, " +
                "email VARCHAR(80) NOT NULL)";

        Statement st = connection.createStatement();
        st.executeUpdate(sql);
    }

    public void createCoursesTable() throws SQLException{

        String sql = "CREATE TABLE IF NOT EXISTS courses(" +
                "c_id VARCHAR(20) PRIMARY KEY, " +
                "c_name VARCHAR(80) NOT NULL, " +
                "credit INT NOT NULL)";

        Statement st = connection.createStatement();
        st.executeUpdate(sql);
    }

    public void createEnrollmentTable() throws SQLException{

        String sql = "CREATE TABLE IF NOT EXISTS enrollment(" +
                "e_id VARCHAR(20) PRIMARY KEY, " +
                "s_id VARCHAR(20) NOT NULL, " +
                "c_id VARCHAR(20) NOT NULL, " +
                "FOREIGN KEY (s_id) REFERENCES students(s_id), " +
                "FOREIGN KEY (c_id) REFERENCES courses(c_id))";

        Statement st = connection.createStatement();
        st.executeUpdate(sql);
    }

    //========================= StudentsManager (Start)  =========================

    public String generateStudentId(){
        String sql = "SELECT s_id FROM students ORDER BY s_id DESC LIMIT 1";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                String id = rs.getString("s_id");
                int idValue = Integer.parseInt(id.substring(1));
                idValue += 1;
                return String.format("s%04d",idValue);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "s0001";
    }

    public boolean emailExist(String email){
        String sql = "SELECT email FROM students WHERE email = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return true;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Student getStudent(String id){

        Student student = null;
        String sql = "SELECT * FROM students WHERE s_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String s_id = rs.getString("s_id");
                String name = rs.getString("s_name");
                int age = rs.getInt("age");
                String email = rs.getString("email");

                student = new Student(s_id, name, age, email);
            }
            return student;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return student;
    }

    public void addStudent(Student student) throws SQLException{

            String sql = "INSERT INTO students(s_id, s_name, age, email) VALUES(?,?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,student.getId());
            ps.setString(2,student.getName());
            ps.setInt(3,student.getAge());
            ps.setString(4,student.getEmail());

            ps.executeUpdate();
    }

    public void deleteStudent(String id) throws SQLException{

        String enrollmentStudentSQL = "DELETE FROM enrollment WHERE s_id = ?";

        String studentSQL = "DELETE FROM students WHERE s_id = ?";

        PreparedStatement deleteEnrollmentStudent = connection.prepareStatement(enrollmentStudentSQL);
        deleteEnrollmentStudent.setString(1,id);
        deleteEnrollmentStudent.executeUpdate();

        PreparedStatement deleteStudent = connection.prepareStatement(studentSQL);
        deleteStudent.setString(1, id);
        deleteStudent.executeUpdate();
    }

    public void viewAllStudents(){

        String sql = "SELECT * FROM students";

        System.out.print("""
                =============================================================================
                ID         Name                                Age      Email
                =============================================================================
                """);
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                String id = rs.getString("s_id");
                String name = rs.getString("s_name");
                int age = rs.getInt("age");
                String email = rs.getString("email");

                System.out.printf("%-10s %-35s %-8d %-23s\n",
                        id, name, age, email);
            }
            return;
        } catch (SQLException e){
            System.out.println();
        }
        System.out.println("No Student Exist.");
    }

    // ================== StudentManager (End) ====================

    // =================== CourseManager (Start) ===================

    public Course getCourse(String id){

        String sql = "SELECT * FROM courses WHERE c_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String c_id = rs.getString("c_id");
                String name = rs.getString("c_name");
                int credit = rs.getInt("credit");

                return new Course(c_id, name, credit);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String generateCourseId(){

        String sql = "SELECT c_id FROM courses ORDER BY c_id DESC LIMIT 1";

        try {

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                String id = rs.getString("c_id");
                int idValue = Integer.parseInt(id.substring(1));
                idValue += 1;

                return String.format("c%03d",idValue);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "c001";
    }

    public void addCourse(Course course) throws SQLException{

        String sql = "INSERT INTO courses (c_id, c_name, credit) VALUES (?,?,?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, course.getId());
        ps.setString(2, course.getName());
        ps.setInt(3,course.getCredit());

        ps.executeUpdate();
    }

    public void deleteCourse(Course course) throws SQLException {

        String enrollmentCourseSQL = "DELETE FROM enrollment WHERE c_id = ?";
        String courseSQL = "DELETE FROM courses WHERE c_id = ?";

        PreparedStatement deleteEnrollmentCourse = connection.prepareStatement(enrollmentCourseSQL);
        deleteEnrollmentCourse.setString(1, course.getId());

        PreparedStatement deleteCourse = connection.prepareStatement(courseSQL);
        deleteCourse.setString(1, course.getId());


        deleteEnrollmentCourse.executeUpdate();
        deleteCourse.executeUpdate();
    }

    public void viewAllCourses(){

        String sql = "SELECT * FROM courses";

        System.out.print("""
                ===========================================================
                ID         Course Name                             Credit
                ===========================================================
                """);

        try {

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                String id = rs.getString("c_id");
                String name = rs.getString("c_name");
                int credit = rs.getInt("credit");

                System.out.printf("%-10s %-39s %-6d\n", id, name, credit);
            }
            return;

        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("No Courses Exists.");
    }

    // ================== CourseManager (End) ==========================

    // ================== EnrollmentManager (Start) ==========================

    public Enrollment getEnrollment(String studentId, String courseId){

        String sql = "SELECT * FROM enrollment WHERE s_id = ? AND c_id =?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String e_id = rs.getString("e_id");
                String s_id = rs.getString("s_id");
                String c_id = rs.getString("c_id");

                return new Enrollment(e_id, s_id, c_id);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String generateEnrollmentId(){

        String sql = "SELECT e_id FROM enrollment ORDER BY e_id DESC LIMIT 1";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("e_id");
                int idValue = Integer.parseInt(id.substring(1));

                idValue += 1;
                return String.format("e%04d",idValue);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return "e0001";
    }

    public void enrollStudent(Student student, Course course) throws SQLException{

        String sql = "INSERT INTO enrollment (e_id, s_id, c_id) VALUES (?,?,?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, generateEnrollmentId());
        ps.setString(2, student.getId());
        ps.setString(3, course.getId());

        ps.executeUpdate();
    }

    public void dropCourse(Enrollment enrollment) throws SQLException{

        String sql = "DELETE FROM enrollment WHERE s_id = ? AND c_id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,enrollment.getStudentId());
        ps.setString(2, enrollment.getCourseId());

        ps.executeUpdate();
    }

    public void displayStudentEnrollment(Student student) throws SQLException{

        int totalCourses = 0;
        int totalCredit = 0;

        String sql = "SELECT c.c_name, c.credit " +
                "FROM enrollment e " +
                "JOIN courses c ON e.c_id = c.c_id " +
                "WHERE e.s_id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, student.getId());
        ResultSet rs = ps.executeQuery();

        System.out.println("================================================================================");
        System.out.printf("Student ID:   %s\nStudent Name: %s\n\nCourses Taken: \n", student.getId(),student.getName());

        while (rs.next()){
            totalCourses += 1;

            String courseName = rs.getString("c_name");
            int credit = rs.getInt("credit");

            System.out.printf("%d. %-30s (%d Credits)\n", totalCourses,  courseName, credit);
            totalCredit += credit;
        }
        if (totalCourses == 0 && totalCredit == 0){
            System.out.println("No courses enrolled.");
        }

        System.out.println("--------------------------------------------------------------------------------");

        System.out.printf("Total Courses Taken: %d\nTotal Credit: %d\n", totalCourses,totalCredit);

        System.out.println("================================================================================");

    }

    public void displayCourseEnrollment(Course course) throws SQLException{

        int totalStudents = 0;

        String sql = "SELECT e.s_id, s.s_name " +
                "FROM students s " +
                "JOIN enrollment e ON e.s_id = s.s_id " +
                "WHERE e.c_id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, course.getId());
        ResultSet rs = ps.executeQuery();

        System.out.println("\n========================== Course Information =================================");
        System.out.println(course.toString());

        System.out.print("""
                ================================================================================
                ID       Name
                ================================================================================
                """);

        while (rs.next()){
            totalStudents += 1;

            String s_id = rs.getString("s_id");
            String s_name = rs.getString("s_name");

            System.out.printf("%-8s %-30s", s_id, s_name);
        }

        if (totalStudents == 0){
            System.out.println("No Student Exist.");
        }

        System.out.println("\n\n--------------------------------------------------------------------------------");
        System.out.println("Total Students : " + totalStudents);

    }

    // ========================== EnrollmentManager (End) =====================================
}


