package Service;

import Domain.Status;
import Domain.Controllers.UserController;

import java.util.Date;

public class UserApplication {
    UserController controller = new UserController();
    public String signUp(String userId, String passWord, Date date, String name){
        Status status = controller.insertUser(userId, passWord, date.toString(), name);
        if (status == Status.Success) return "User signed up successfully, you can now log in";
        else return "oops, seems we encountered a bad status of: " + status.toString();
    }
    public String signIn(String userId, String password){
        Status status = controller.logIn(userId, password);
        if (status == Status.Success) return "User logged in successfully";
        else return "oops, seems we encountered a bad status of: " + status.toString();
    }
}
