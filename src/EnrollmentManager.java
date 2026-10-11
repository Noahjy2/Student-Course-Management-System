import java.sql.SQLException;
import java.util.Scanner;

public class EnrollmentManager {

    Scanner scanner;
    DatabaseManager database;

    public EnrollmentManager(Scanner scanner, DatabaseManager database){
        this.scanner = scanner;
        this.database = database;
    }


    public void EnrollStudent(){

        System.out.print("Enter Student ID: ");
        String s_id = scanner.nextLine();

        Student student = database.getStudent(s_id);

        if (student == null){
            System.out.println("Student Not Exist.");
            return;
        }

        System.out.println("================= Student Information =================");
        System.out.println(student.toString());
        System.out.println("=======================================================");

        do {
            System.out.print("Enter Course ID to Add: ");
            String c_id = scanner.nextLine();

            Course course = database.getCourse(c_id);

            if (course == null){
                System.out.println("Course Not Exist.");
                return;
            }

            Enrollment checkEnrollment = database.getEnrollment(s_id, c_id);

            if (checkEnrollment != null){
                System.out.println("Student Already Enrolled The Course.");
                return;
            }

            //database addCourse with student name to enrollment table.
            try {
                database.enrollStudent(student, course);
                System.out.println("Enrolled Successfully.");
            } catch (SQLException e){
                e.printStackTrace();
                System.out.println("Failed to Enroll Course.");
            }


            while (true) {
                System.out.print("Add Another Course? (y/n): ");
                String choice = scanner.nextLine().toLowerCase().trim();
                if (choice.equals("n") || choice.equals("no")){
                    return;
                } else if (choice.equals("y") || choice.equals("yes")) {
                    break;
                } else {
                    System.out.println("Please Enter Either 'y' or 'n'.");
                }
            }

        } while (true);
    }

    public void dropCourse(){
        System.out.print("Enter Student ID: ");
        String s_id = scanner.nextLine();

        Student student = database.getStudent(s_id);

        if (student == null){
            System.out.println("Student Not Exist.");
            return;
        }

        System.out.print("Enter Course ID to Drop: ");
        String c_id = scanner.nextLine();

        Course course = database.getCourse(c_id);
        Enrollment enrollment = database.getEnrollment(s_id,c_id);

        if (course == null){
            System.out.println("Course Not Exist.");
            return;
        } else if (enrollment == null){
            System.out.println("Student Does Not Enroll The Course.");
            return;
        }
        System.out.println("Course Name : " + course.getName());

        while (true) {
            System.out.print("\nConfirm to Drop Course? (y/n): ");
            String choice = scanner.nextLine().toLowerCase().trim();
            if (choice.equals("n") || choice.equals("no")){
                return;
            } else if (choice.equals("y") || choice.equals("yes")) {
                break;
            } else {
                System.out.println("Please Enter Either 'y' or 'n'.");
            }
        }

        try {
            database.dropCourse(enrollment);
            System.out.println("Student " + s_id + " Dropped Course (" + c_id + ") Successfully.");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Student " + s_id + " Failed to Dropped The Course.");
        }
    }


    public void searchStudentCourses(){
        System.out.print("Enter Student ID to Search:");
        String s_id = scanner.nextLine();

        Student student = database.getStudent(s_id);

        if (student == null){
            System.out.println("Student Not Exist.");
            return;
        }

        try {
            database.displayStudentEnrollment(student);
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Failed to Display Enrollment Information.");
        }

    }

    public void searchCourseStudents(){
        System.out.print("Enter Course ID to search all students: ");
        String c_id = scanner.nextLine();

        Course course = database.getCourse(c_id);
        if (course == null){
            System.out.println("Course Not Exist.");
            return;
        }

        try {
            database.displayCourseEnrollment(course);
        } catch (SQLException e){
            System.out.println("Failed to Display Enrollment Information.");
        }
    }

}
