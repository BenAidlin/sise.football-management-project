package Tests.AcceptanceTests;

import Domain.Enums.ScheduelsPolicies;
import Service.GameConfigApplication;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    @DisplayName("UC5T1")
    void gamesScheduleSuccessOneGamePolicyTest(){
        int howManyBefore = 0;
        int howManyAfter = 0;
        try {
            howManyBefore = checkHowMany();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String expected = "Games successfully scheduled!";
        String actual = GameConfigApp.gamesSchedule(leagueName, season, "one game");
        howMany = 6;
        try {
            howManyAfter = checkHowMany();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        clearGames(howMany);
        assertEquals(howMany+howManyBefore, howManyAfter);
        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("UC5T2")
    void gamesScheduleSuccessTwoGamePolicyTest(){
        int howManyBefore = 0;
        int howManyAfter = 0;
        try {
            howManyBefore = checkHowMany();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String expected = "Games successfully scheduled!";
        String actual = GameConfigApp.gamesSchedule(leagueName, season, "two game");
        howMany = 12;
        try {
            howManyAfter = checkHowMany();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        clearGames(howMany);
        assertEquals(howMany+howManyBefore, howManyAfter);
        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("UC5T3")
    void gamesScheduleNoSuchPolicyTest(){
        String expected = "oops, seems we encountered a bad status of: " + ScheduelsPolicies.NoSuchPolicy;
        String actual = GameConfigApp.gamesSchedule(leagueName, season, "Test");
        assertEquals(expected,actual);
    }

    @BeforeEach
    void resetHowMany(){
        howMany = 0;
    }

    private void clearGames(int howMany){
        GameDao.execute(String.format("delete from Games where cast(id as int) > (select cast(max(id) as int)-%s from Games)", howMany));
    }

    private int checkHowMany() throws SQLException {
        ResultSet rs = GameDao.executeAndGet("select * from games");
        int i=0;
        while(rs.next()) i+=1;
        return i;
    }

}
