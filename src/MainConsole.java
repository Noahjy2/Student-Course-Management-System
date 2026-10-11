import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MainConsole {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Connection connection = DatabaseConnection.getConnection();
        DatabaseManager database = new DatabaseManager(connection);
        StudentManager studentManager = new StudentManager(scanner,database);
        CourseManager courseManager = new CourseManager(scanner, database);
        EnrollmentManager enrollmentManager = new EnrollmentManager(scanner, database);


        if (connection != null){
            System.out.println("Database Connection Successful.");
        } else {
            System.out.println("Database Connection Failed.");
            return;
        }

        try {
            database.createStudentsTable();
            database.createCoursesTable();
            database.createEnrollmentTable();
        } catch (SQLException e){
            e.printStackTrace();
        }

        while (true){
            System.out.print("""
                ======== STUDENT COURSE MANAGEMENT SYSTEM ========
                
                1. Student
                2. Course
                3. Enrollment
                4. Exit
                """);
            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice){
                case "1" -> studentManagement(scanner, studentManager, database);
                case "2" -> courseManagement(scanner, courseManager, database);
                case "3" -> enrollmentManagement(scanner, enrollmentManager);
                case "4" -> System.out.println("Bye.");
                default -> System.out.println("Invalid choice. Please enter a valid input (1-4).");
            }
            if (choice.equals("4")){
                break;
            }
        }

    }
    public static void studentManagement(Scanner scanner, StudentManager studentManager, DatabaseManager database){

        while (true){
            System.out.print("""
                \n======== STUDENT MANAGEMENT ========
                1. Add Student
                2. Remove Student
                3. Search Student
                4. View All Students
                5. Back To Main Menu
                """);
            System.out.print("Enter your choice (1-5): ");
            String choice = scanner.nextLine();
            System.out.println();

            switch(choice) {
                case "1" -> studentManager.addStudent();
                case "2" -> studentManager.deleteStudent();
                case "3" -> studentManager.searchStudent();
                case "4" -> database.viewAllStudents();
                case "5" -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid Choice.");
            }

            if (choice.equals("5")){
                break;
            }
        }
    }

    public static void courseManagement(Scanner scanner, CourseManager courseManager, DatabaseManager database){

        while (true) {

            System.out.print("""
                \n========== COURSE MANAGEMENT ==========
                
                1. Add Course
                2. Remove Course
                3. Search Course
                4. View All Courses
                5. Return;
                """);
            System.out.print("Enter Your Choice (1-5): ");
            String choice = scanner.nextLine();
            System.out.println();

            switch (choice) {
                case "1" -> courseManager.addCourse();
                case "2" -> courseManager.deleteCourse();
                case "3" -> courseManager.searchCourse();
                case "4" -> database.viewAllCourses();
                case "5" -> System.out.println("Returning to Main Menu.");
                default -> System.out.println("Invalid Choice.");
            }

            if (choice.equals("5")){
                return;
            }
        }
    }

    public static void enrollmentManagement(Scanner scanner, EnrollmentManager enrollmentManager){

        while (true) {
            System.out.println("""
                \n========== ENROLLMENT MANAGEMENT ==========
                
                1. Enroll Student
                2. Drop Course
                3. Search Student Courses
                4. Search Course Students
                5. Exit
                """);
            System.out.print("Enter Your Choice: ");
            String choice = scanner.nextLine();

            switch (choice){
                case "1" -> enrollmentManager.EnrollStudent();
                case "2" -> enrollmentManager.dropCourse();
                case "3" -> enrollmentManager.searchStudentCourses();
                case "4" -> enrollmentManager.searchCourseStudents();
                case "5" -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid Choice.");
            }

            if (choice.equals("5")){
                return;
            }
        }

    }
}