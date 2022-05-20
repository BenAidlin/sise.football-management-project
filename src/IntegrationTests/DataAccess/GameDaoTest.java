package IntegrationTests.DataAccess;

import DataAccess.GameDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameDaoTest {
    static GameDao gameDao;
    static String homeTeam = "TestHome";
    static String awayTeam = "TestAway";
    static String date = "TestDate";
    static String referee = "TestRef";
    static String leagueName = "chempion";
    static int season = 0;

    @BeforeAll
    public static void setUp(){
        gameDao = GameDao.getInstance();
    }

    @Test
    void saveThenGetThenDelete(){
        boolean success = false;
        String id = null;
        try{
            // add game
            HashMap<String, String> toSave = new HashMap<>(){{
               put("HomeTeam", homeTeam);
               put("AwayTeam", awayTeam);
               put("Date", date);
               put("Referee", referee);
            }};
            if(!gameDao.save(toSave, leagueName, season)) throw new Exception();
            // check it was added
            List<HashMap<String,String>> fromGet = gameDao.get(toSave);
            id = fromGet.get(0).get("Id");
            if(fromGet.size()==0) throw new Exception();
            success = true;
        } catch (Exception e) {
            // still go to finally
        } finally {
            // no matter what happens, remove the record
            if(id!=null) {
                HashMap<String, String> toDelete = new HashMap<>();
                toDelete.put("Id", id);
                if (!gameDao.delete(toDelete)) success = false;
            }
        }
        assertTrue(success);
    }

}