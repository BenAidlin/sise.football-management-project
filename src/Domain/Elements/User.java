package Domain.Elements;

import java.util.ArrayList;
import java.util.List;

public class User {
    String UserId;
    String Password;
    String DateOfBirth;
    String Name;
    public static List<User> loggedIn = new ArrayList<>();

    public User(String userId, String password, String dateOfBirth, String name) {
        UserId = userId;
        Password = password;
        DateOfBirth = dateOfBirth;
        Name = name;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
