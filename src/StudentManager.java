import java.sql.SQLException;
import java.util.Scanner;

public class StudentManager {

    Scanner scanner;
    DatabaseManager database;

    public StudentManager(Scanner scanner, DatabaseManager database){
        this.scanner = scanner;
        this.database = database;
    }

    public void addStudent(){

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        int age = 0;
        while (true) {
            System.out.print("Enter Age: ");
            String value = scanner.nextLine();

            try {
                age = Integer.parseInt(value);
                if (age <= 0){
                    System.out.println("Age Cannot Be Equal or Less Than 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input. Please Enter a Valid Input (Numbers only).");
            }
        }


        System.out.print("Enter Student Email:");
        String email = scanner.nextLine();

        if (database.emailExist(email)){
            System.out.println("Student With The Same EMAIL Already Exist.");
            return;
        }

        Student student = new Student(database.generateStudentId(), name, age,email);
        try {
            database.addStudent(student);
            System.out.println("Student Added Successfully.");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Failed To Add Student.");
        }

    }

    public void deleteStudent(){
        System.out.print("Enter Student ID to Delete: ");
        String id = scanner.nextLine();

        Student student = database.getStudent(id);

        if (student == null){
            System.out.println("Student Not Exist.");
            return;
        }

        try {
            database.deleteStudent(student.getId());
            System.out.println("Student Removed Successfully.");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Failed To Remove Student.");
        }
    }

    public void searchStudent(){
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.println();

        Student student = database.getStudent(id);

        if (student == null){
            System.out.println("Student Not Exist.");
            return;
        }

        System.out.println(student.toString());
    }
}
