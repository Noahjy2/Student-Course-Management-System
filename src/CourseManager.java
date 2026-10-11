import java.sql.SQLException;
import java.util.Scanner;

public class CourseManager {

    Scanner scanner;
    DatabaseManager database;

    public CourseManager(Scanner scanner, DatabaseManager database){
        this.scanner = scanner;
        this.database = database;
    }

    public void addCourse(){
        System.out.print("Enter Course Name: ");
        String name = scanner.nextLine();

        int credit = 0;
        while (true){

            System.out.print("Enter Credit: ");
            String value = scanner.nextLine();

            try {
                credit = Integer.parseInt(value);
                if (credit <= 0){
                    System.out.println("Credit Cannot Be Equal To or Less Than 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input. Please Enter a Valid Input (Numbers only).");
            }
        }

        try {
            database.addCourse(new Course(database.generateCourseId(), name, credit));
            System.out.println("Course Added Successfully.");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Failed To Add Course.");
        }
    }

    public void deleteCourse(){

        System.out.print("Enter Course ID to Delete: ");
        String id = scanner.nextLine();
        //how to control the format of id starting with c and 3 digits?

        Course course = database.getCourse(id);
        if (course == null) {
            System.out.println("Course Not Exist.");
            return;
        }

        try {
            database.deleteCourse(course);
            System.out.println("Course Removed Successfully.");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Failed To Remove Course.");
        }
    }

    public void searchCourse(){
        System.out.print("Enter Course ID: ");
        String id = scanner.nextLine();
        System.out.println();

        Course course = database.getCourse(id);

        if (course == null){
            System.out.println("Course Not Exist.");
            return;
        }

        System.out.println(course.toString());
    }

}
