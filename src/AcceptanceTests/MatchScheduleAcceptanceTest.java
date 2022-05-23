package AcceptanceTests;

import Domain.Elements.User;
import Domain.Enums.ScheduelsPolicies;
import Service.GameConfigApplication;
import Service.RefereeRegisterApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchScheduleAcceptanceTest {
    static GameConfigApplication GameConfigApp;
    static DataAccess.GameDao GameDao = (DataAccess.GameDao) DataAccess.GameDao.getInstance();
    static String leagueName = "chempion";
    static int season = 2020;
    static int howMany = 0;


    @BeforeAll
    public static void setUp(){
        GameConfigApp = new GameConfigApplication();
    }

    @Test
    void gamesScheduleSuccessOneGamePolicyTest(){
        String expected = "Games successfully scheduled!";
        String actual = GameConfigApp.gamesSchedule(leagueName, season, "one game");
        howMany += 6;
        assertEquals(expected,actual);
    }

    @Test
    void gamesScheduleSuccessTwoGamePolicyTest(){
        String expected = "Games successfully scheduled!";
        String actual = GameConfigApp.gamesSchedule(leagueName, season, "two game");
        howMany += 12;
        assertEquals(expected,actual);
    }

    @Test
    void gamesScheduleNoSuchPolicyTest(){
        String expected = "oops, seems we encountered a bad status of: " + ScheduelsPolicies.NoSuchPolicy;
        String actual = GameConfigApp.gamesSchedule(leagueName, season, "Test");
        assertEquals(expected,actual);
    }

    @AfterAll
    static void clearDB (){
        GameDao.execute(String.format("delete from Games where cast(id as int) > (select cast((max(id) as int)-%s from Games)", howMany));
    }

}
