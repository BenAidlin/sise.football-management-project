package IntegrationTests.DataAccess;

import DataAccess.RefereeDao;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RefereeDaoTest {
    static RefereeDao refereeDao;
    static String refId;
    static HashMap<String, String> refData;
    static String leagueId = "0";
    @BeforeAll
    public static void setUp(){
        refereeDao = RefereeDao.getInstance();
        UserDaoTest.setUp();
    }

    @AfterEach
    void deleteRef(){
        if(refId == null)return;
        HashMap<String, String> toDelete = new HashMap<>(){{
            put("Id", refId);
        }};
        refereeDao.delete(toDelete);
        UserDaoTest.deleteUser();
    }

    @BeforeEach
    void createRef(){
        refData = UserDaoTest.createUserInDb();
        refId = refData.get("Id");
        refereeDao.save(refData);
    }

    @Test
    void saveThenGetThenDelete(){
        boolean success = false;
        String id = null;
        try{
            List<HashMap<String,String>> fromGet = refereeDao.get(refData);
            refId = fromGet.get(0).get("Id");
            if(fromGet.size()==0) throw new Exception();
            success = true;
        } catch (Exception e) {
            // still go to finally
        }

        assertTrue(success);

    }

    @Test
    void addRefToLeagueThenGetThenDelete(){
        boolean success = false;
        String id = null;
        try{
            boolean b = refereeDao.AddRefereeToLeague(refId, leagueId);
            if(!b) throw new Exception();
            List<HashMap<String,String>> fromGet = refereeDao.GetRefereeInLeague(refId, leagueId);
            if(fromGet.size()==0) throw new Exception();
            id = fromGet.get(0).get("RefereeId");
        } catch (Exception e) {
        }
        finally {
            if (id != null) {
                success = refereeDao.DeleteRefereeFromLeague(refId, leagueId);
            }
            else{success=false;}
        }
        assertTrue(success);
    }


}