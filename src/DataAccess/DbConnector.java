package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static final String url = "jdbc:sqlite:./DB/footballDB.db";
    // single tone
    private static DbConnector instance = new DbConnector();
    public static DbConnector getInstance(){return instance;}
    // get the sql connection
    public Connection getConnection(){
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }
}
