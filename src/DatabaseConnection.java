import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection(){

        String url = "jdbc:mysql://localhost:3306/student_course_management_system";
        String username = "root";
        String password = "Your_Password";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
