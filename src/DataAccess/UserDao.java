package DataAccess;

import Domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User>{
    // single tone
    private static UserDao instance = new UserDao();
    public static UserDao getInstance(){return instance;}
    // get sql connection
    DbConnector connector = DbConnector.getInstance();

    @Override
    public List<User> get(String id) {
        List<User> users = new ArrayList<>();
        String query = String.format("SELECT * FROM Users WHERE UserId = '%s'" , id);
        try {
            ResultSet rs = connector.getConnection().createStatement().executeQuery(query);
            while(rs.next()){
                users.add(new User(rs.getString("UserId"), rs.getString("Password"), rs.getString("DateOfBirth"), rs.getString("Name")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try {
            ResultSet rs = connector.getConnection().createStatement().executeQuery(query);
            while(rs.next()){
                users.add(new User(rs.getString("UserId"), rs.getString("Password"), rs.getString("DateOfBirth"), rs.getString("Name")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean save(User user) {
        String query = String.format("INSERT INTO Users VALUES('%s','%s','%s','%s')",
                user.getUserId(), user.getPassword(), user.getDateOfBirth(), user.getName());
        try {
            connector.getConnection().createStatement().executeUpdate(query);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        String query = String.format("UPDATE Users SET UserId = %s, Password = %s, DateOfBirth = %s, Name = %s" +
                        "WHERE UserId = %s",
                user.getUserId(), user.getPassword(), user.getDateOfBirth(), user.getName(), user.getUserId());
        try {
            connector.getConnection().createStatement().executeUpdate(query);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        String query = String.format("DELETE FROM Users" +
                        "WHERE UserId = %s",
                user.getUserId());
        try {
            connector.getConnection().createStatement().executeUpdate(query);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
