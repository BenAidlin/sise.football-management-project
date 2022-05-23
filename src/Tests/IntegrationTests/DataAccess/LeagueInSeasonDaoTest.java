package Tests.IntegrationTests.DataAccess;

import DataAccess.LeagueInSeasonDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeagueInSeasonDaoTest {
    static LeagueInSeasonDao leagueInSeasonDao;
    @BeforeAll
    public static void setUp(){
        leagueInSeasonDao = LeagueInSeasonDao.getInstance();
    }

    @Test
    void getById_exists(){
        // check when id exists
        HashMap<String, String> tableKey = new HashMap<>(){{
            put("Id", "0");
        }};
        List<HashMap<String, String>> result = leagueInSeasonDao.getById(tableKey);
        assertTrue(result.size()==1);
    }
    @Test
    void getById_notExists(){
        // check when id exists
        HashMap<String, String> tableKey = new HashMap<>(){{
            put("Id", "1");
        }};
        List<HashMap<String, String>> result = leagueInSeasonDao.getById(tableKey);
        assertTrue(result.size()==0);
    }
}