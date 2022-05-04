package Service;
import java.sql.*;
public class Main {

    public static void main(String[] args) {
        // temporary just to check the connection
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:./DB/footballDB.db");
            conn.createStatement().executeQuery("DELETE FROM Refere where id = 'uri'");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}