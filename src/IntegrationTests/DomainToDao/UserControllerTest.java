package IntegrationTests.DomainToDao;

import DataAccess.UserDao;
import Domain.Controllers.UserController;
import Domain.Enums.SignInUpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    static UserController userController;
    static UserDao userDao;
    String id = "TestId";
    String pass = "TestPass";
    String bd = "TestBD";
    String name = "TestName";

    @BeforeAll
    static void setUp(){
        userController = new UserController();
        userDao = new UserDao();
    }

    @Test
    void insertThenRemove(){
        // check sign up
        SignInUpStatus actualSignUpStatus = userController.insertUser(id, pass, bd, name);
        assertEquals(actualSignUpStatus, SignInUpStatus.Success);
        // check sign up with Taken user id
        actualSignUpStatus = userController.insertUser(id, pass, bd, name);
        assertEquals(actualSignUpStatus, SignInUpStatus.UserIdTaken);
        // check log in with bad password
        actualSignUpStatus = userController.logIn(id, "wrongPassword");
        assertEquals(actualSignUpStatus, SignInUpStatus.BadPassword);
        // check log in
        actualSignUpStatus = userController.logIn(id, pass);
        assertEquals(actualSignUpStatus, SignInUpStatus.Success);
        // check log in with already log in user
        actualSignUpStatus = userController.logIn(id, pass);
        assertEquals(actualSignUpStatus, SignInUpStatus.AlreadyLoggedIn);
        HashMap<String, String> toDelete = new HashMap<>(){{
            put("Id", id);
        }};
        try{
            boolean b = userDao.delete(toDelete);
            if(!b) throw new Exception();
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

}