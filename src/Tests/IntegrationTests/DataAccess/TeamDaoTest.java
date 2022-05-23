package Tests.IntegrationTests.DataAccess;

import DataAccess.TeamDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamDaoTest {
    static TeamDao teamDao;
    static String teamName = "TestFC";

    @BeforeAll
    static void setUp(){
        teamDao = TeamDao.getInstance();
    }
    @Test
    void saveThenGetThenDelete(){
        boolean success = false;
        String id = null;
        try{
            // add game
            HashMap<String, String> toSave = new HashMap<>(){{
                put("Name", teamName);
            }};
            if(!teamDao.save(toSave)) throw new Exception();
            // check it was added
            List<HashMap<String,String>> fromGet = teamDao.get(toSave);
            if(fromGet.size()==0) throw new Exception();
            id = fromGet.get(0).get("Id");
            success = true;
        } catch (Exception e) {
            // still go to finally
        } finally {
            if(id!=null) {
                // no matter what happens, remove the record
                HashMap<String, String> toDelete = new HashMap<>();
                toDelete.put("Id", id);
                success = teamDao.delete(toDelete);
            }
            else success = false;
        }
        assertTrue(success);
    }


}