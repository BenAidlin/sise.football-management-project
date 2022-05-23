package Tests.UnitTests.Domain;

import Domain.Controllers.UserController;
import Domain.Elements.User;
import Domain.Enums.SignInUpStatus;
import Tests.UnitTests.Mocks.UserDaoMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    static UserController userController;
    static UserDaoMock userDaoMock = new UserDaoMock();
    static String userId = "user";
    @BeforeAll
    public static void setUp(){
        userController = new UserController(userDaoMock);
    }

    // insert user tests

    @Test
    void insertUser_userIdTaken(){
        SignInUpStatus expected = SignInUpStatus.UserIdTaken;
        // set up user dao to return list of users containing the requested
        List<HashMap<String, String>> toReturnFromUser = getListWithUserId();
        userDaoMock.setReturnFromGetAll(toReturnFromUser);
        // insert the user
        SignInUpStatus actual = userController.insertUser(userId, "passwd", "12/3/92", "jon doe");
        // check when user id taken, cant insert
        assertEquals(expected,actual);
    }

    @Test
    void insertUser_somethingWentWrong(){
        SignInUpStatus expected = SignInUpStatus.SomethingWentWrong;
        // set up user dao to return list of users not containing the requested
        List<HashMap<String, String>> toReturnFromUser = getListOfUsers();
        userDaoMock.setReturnFromGetAll(toReturnFromUser);
        // set the dao to fail on save
        userDaoMock.setReturnFromSave(false);
        // insert the user
        SignInUpStatus actual = userController.insertUser(userId, "passwd", "12/3/92", "jon doe");
        // check when user id taken, cant insert
        assertEquals(expected,actual);
    }

    @Test
    void insertUser_success(){
        SignInUpStatus expected = SignInUpStatus.Success;
        // set up user dao to return list of users not containing the requested
        List<HashMap<String, String>> toReturnFromUser = getListOfUsers();
        userDaoMock.setReturnFromGetAll(toReturnFromUser);
        // set the dao to fail on save
        userDaoMock.setReturnFromSave(true);
        // insert the user
        SignInUpStatus actual = userController.insertUser(userId, "passwd", "12/3/92", "jon doe");
        // check when user id taken, cant insert
        assertEquals(expected,actual);
    }

    // log in tests

    @Test
    void logIn_noUser(){
        SignInUpStatus expected = SignInUpStatus.BadUserName;
        // set up user dao to return list of users not containing the requested
        List<HashMap<String, String>> toReturnFromUser = getListOfUsers();
        userDaoMock.setReturnFromGetAll(toReturnFromUser);
        // check that when user not exists, correct status
        SignInUpStatus actual = userController.logIn(userId, "blabla");
        assertEquals(expected, actual);
    }

    @Test
    void logIn_wrongPassword(){
        SignInUpStatus expected = SignInUpStatus.BadPassword;
        // set up user dao to return list of users not containing the requested
        List<HashMap<String, String>> toReturnFromUser = getListWithUserId();
        userDaoMock.setReturnFromGetAll(toReturnFromUser);
        // check that when user not exists, correct status
        SignInUpStatus actual = userController.logIn(userId, "blabla");
        assertEquals(expected, actual);
    }
    @Test
    void logIn_success(){
        SignInUpStatus expected = SignInUpStatus.Success;
        // set up user dao to return list of users containing the requested
        List<HashMap<String, String>> toReturnFromUser = getListWithUserId();
        userDaoMock.setReturnFromGetAll(toReturnFromUser);
        // check that user can log in
        SignInUpStatus actual = userController.logIn(userId, "blabla2");
        assertEquals(expected, actual);
    }
    @Test
    void logIn_alreadyLoggedIn(){
        SignInUpStatus expected = SignInUpStatus.AlreadyLoggedIn;
        // set up user dao to return list of users containing the requested
        List<HashMap<String, String>> toReturnFromUser = getListWithUserId();
        userDaoMock.setReturnFromGetAll(toReturnFromUser);
        // add user once
        userController.logIn(userId, "blabla2");
        // add user another time
        SignInUpStatus actual = userController.logIn(userId, "blabla2");
        assertEquals(expected, actual);
    }

    @AfterEach
    void reset(){
        User.loggedIn.clear();
    }

    private List<HashMap<String,String>> getListOfUsers(){
        List<HashMap<String, String>> toReturnFromUser = new ArrayList<>();
        HashMap<String, String> existing1 = new HashMap<>();
        existing1.put("UserId", "user1"); existing1.put("Name", "userName1"); existing1.put("Password", "blabla1");existing1.put("DateOfBirth", "userDOB1");
        HashMap<String, String> existing2 = new HashMap<>();
        existing2.put("UserId", "user2"); existing2.put("Name", "userName2"); existing2.put("Password", "blabla2");existing2.put("DateOfBirth", "userDOB2");
        toReturnFromUser.add(existing1); toReturnFromUser.add(existing2);
        return toReturnFromUser;
    }
    private List<HashMap<String,String>> getListWithUserId(){
        List<HashMap<String, String>> toReturnFromUser = getListOfUsers();
        HashMap<String, String> existing = new HashMap<>();
        existing.put("UserId", userId); existing.put("Name", "userName2"); existing.put("Password", "blabla2");existing.put("DateOfBirth", "userDOB2");
        toReturnFromUser.add(existing);
        return toReturnFromUser;
    }
}