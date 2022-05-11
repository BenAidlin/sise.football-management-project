package Service;

import Domain.Enums.SignInUpStatus;
import Domain.Controllers.UserController;

import java.util.Date;

public class UserApplication {
    UserController controller = new UserController();
    public String signUp(String userId, String passWord, Date date, String name){
        SignInUpStatus signInUpStatus = controller.insertUser(userId, passWord, date.toString(), name);
        if (signInUpStatus == SignInUpStatus.Success) return "User signed up successfully, you can now log in";
        else return "oops, seems we encountered a bad status of: " + signInUpStatus.toString();
    }
    public String signIn(String userId, String password){
        SignInUpStatus signInUpStatus = controller.logIn(userId, password);
        if (signInUpStatus == SignInUpStatus.Success) return "User logged in successfully";
        else return "oops, seems we encountered a bad status of: " + signInUpStatus.toString();
    }
}
