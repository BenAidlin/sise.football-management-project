package Tests.IntegrationTests.DomainToDao;

import DataAccess.GameDao;
import Domain.Controllers.MatchScheduleController;
import Domain.Enums.GameScheduleStatus;
import Domain.Enums.ScheduelsPolicies;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MatchScheduleControllerTest {
    static MatchScheduleController matchScheduleController;
    static String leagueName = "chempion";
    static int season = 2020;

    static GameDao gameDao;
    @BeforeAll
    static void setUp(){
        matchScheduleController = new MatchScheduleController();
        gameDao = GameDao.getInstance();
    }

    @Test
    void testScheduleGame_onlyHomeOrAway(){
        boolean success = insertAndClearByPolicy(6, ScheduelsPolicies.onlyHomeOrAway);
        assertTrue(success);
    }

    @Test
    void testScheduleGame_homeAndAway(){
        boolean success = insertAndClearByPolicy(12, ScheduelsPolicies.homeAndAway);
        assertTrue(success);
    }


    private boolean insertAndClearByPolicy(int expectedAmountOfGames, ScheduelsPolicies policy){
        int howManyBefore = 0;
        try {
            howManyBefore = checkHowMany();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        GameScheduleStatus status = matchScheduleController.scheduleGame(leagueName, season, policy);
        int howManyAfter = 0;
        try {
            howManyAfter = checkHowMany();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        clearGames(expectedAmountOfGames);
        if(howManyAfter != howManyBefore+expectedAmountOfGames) return false;
        if(status!=GameScheduleStatus.Success) return false;

        return true;
    }

    private void clearGames(int howMany){
        gameDao.execute(String.format("delete from Games where cast(id as int) > (select cast(max(id) as int)-%s from Games)", howMany));
    }

    private int checkHowMany() throws SQLException {
        ResultSet rs = gameDao.executeAndGet("select * from games");
        int i=0;
        while(rs.next()) i+=1;
        return i;
    }
}