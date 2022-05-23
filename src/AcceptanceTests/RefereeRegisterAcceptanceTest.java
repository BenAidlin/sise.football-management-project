package AcceptanceTests;

import DataAccess.UserDao;
import Domain.Enums.RegisterRefereeStatus;
import Service.RefereeRegisterApplication;
import Service.UserApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefereeRegisterAcceptanceTest {
    static RefereeRegisterApplication RefApp;
    static DataAccess.RefereeDao RefDao = (DataAccess.RefereeDao) DataAccess.RefereeDao.getInstance();
    static String newRefereeId = "Katya";
    static String existingRefereeId = "Maxim";

    @BeforeAll
    public static void setUp(){
        RefApp = new RefereeRegisterApplication();
    }

    @Test
    void registerNewRefereeToSystemSuccessTest(){
        String expected = "Referee successfully registered";
        String actual = RefApp.registerRefereeToSystem(newRefereeId);
        assertEquals(expected,actual);
    }

    @Test
    void registerExistingRefereeToSystemTest(){
        String expected = "oops, seems we encountered a bad status of: " + RegisterRefereeStatus.AlreadyExists;
        String actual = RefApp.registerRefereeToSystem(existingRefereeId);
        assertEquals(expected,actual);
    }

    @AfterEach
    void clearDB(){
        HashMap<String , String> toDelete = new HashMap<>();
        toDelete.put("Id", newRefereeId);
        RefDao.delete(toDelete);
    }
}
