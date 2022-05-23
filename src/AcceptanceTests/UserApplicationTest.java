package AcceptanceTests;
import DataAccess.UserDao;
import Domain.Elements.User;
import Service.UserApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Domain.Enums.SignInUpStatus;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserApplicationTest {
    static UserApplication UserApp;
    static UserDao UserDao = (DataAccess.UserDao) DataAccess.UserDao.getInstance();
    static String newUserName = "newUserTest";
    static String existingUserName = "Katya";
    static String existingPasswrd = "Katya123";
    static String newPasswrd = "Test";
    static Date birthDate = new Date(2000, 11, 21);
    static String name = "Test";


    @BeforeAll
    public static void setUp(){
        UserApp = new UserApplication();
    }

    @Test
    void signUpSuccessTest(){
        String expected = "User signed up successfully, you can now log in";
        String actual = UserApp.signUp(newUserName, newPasswrd, birthDate, name);
        assertEquals(expected,actual);
    }

    @Test
    void signUpUserNameTakenTest(){
        String expected = "oops, seems we encountered a bad status of: " + SignInUpStatus.UserIdTaken;
        String actual = UserApp.signUp(existingUserName, existingPasswrd, birthDate, name);
        assertEquals(expected,actual);
    }

//    @Test
//    void signUpBadPasswordTest(){
//        passwrd = "wrongPassTest";
//        String expected = "oops, seems we encountered a bad status of: " + SignInUpStatus.BadPassword;
//        String actual = UserApp.signUp(existingUserName, passwrd, birthDate, name);
//        assertEquals(expected,actual);
//    }

    @Test
    void signInSuccessTest(){
        String expected = "User logged in successfully";
        String actual = UserApp.signIn(existingUserName, existingPasswrd);
        assertEquals(expected,actual);
    }

    @Test
    void signInWrongPasswordTest(){
        String expected = "oops, seems we encountered a bad status of: " + SignInUpStatus.BadPassword;
        String actual = UserApp.signIn(existingUserName, newPasswrd);
        assertEquals(expected,actual);
    }

    @AfterEach
    void reset(){
        User.loggedIn.clear();
    }

    @AfterAll
    static void clearDB (){
        HashMap<String , String> toDelete = new HashMap<>();
        toDelete.put("Id", newUserName);
        UserDao.delete(toDelete);
    }
}
