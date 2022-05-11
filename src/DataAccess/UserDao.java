package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserDao extends Dao{
    // single tone
    private static UserDao instance = new UserDao();
    public static UserDao getInstance(){return instance;}

    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        String id = tableKey.get("Id");
        List<HashMap<String, String>> games = new ArrayList<>();
        String query = String.format("SELECT * FROM Users WHERE Id = '%s'", id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList(
                                                                "UserId", "Name", "Password", "DateOfBirth")));
    }

    public List<HashMap<String, String>> getAll() {
        String query = String.format("SELECT * FROM Users");
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs,new ArrayList<String>(Arrays.asList(
                "UserId", "Name", "Password", "DateOfBirth")));
    }

    public boolean save(HashMap<String, String> userData) {
        String Id = userData.get("UserId");
        String Password = userData.get("Password");
        String DateOfBirth = userData.get("DateOfBirth");
        String Name = userData.get("Name");
        String query = String.format("INSERT INTO Users VALUES('%s', '%s', '%s', '%s')",
                                    Id, Password, DateOfBirth, Name);
        boolean b = this.execute(query);
        return b;
    }

    public boolean update(HashMap<String, String> userData) {
        String Id = userData.get("Id");
        String Password = userData.get("Password");
        String DateOfBirth = userData.get("DateOfBirth");
        String Name = userData.get("Name");
        String query = String.format("UPDATE Users SET Password = %s, DateOfBirth = %s, Name = %s" +
                                     "WHERE  UserId = %s",
                                      Password, DateOfBirth, Name , Id);
        boolean b = this.execute(query);
        return b;
    }

    public boolean delete(HashMap<String, String> userData) {
        String Id = userData.get("Id");
        String query = String.format("DELETE FROM Users" + "WHERE  Id = %s", Id);
        boolean b = this.execute(query);
        return b;
    }

}
