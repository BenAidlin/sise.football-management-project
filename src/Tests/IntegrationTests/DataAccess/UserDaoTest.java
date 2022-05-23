package Tests.IntegrationTests.DataAccess;

import DataAccess.IUserDao;
import DataAccess.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    static IUserDao userDao;
    static String userId = "TestUserId";
    static String password = "TestUserPass";
    static String dateOfBirth = "TestUserBD";
    static String name = "TestUserName";
    @BeforeAll
    public static void setUp(){
        userDao = UserDao.getInstance();
    }

    @Test
    void saveThenGetThenDelete(){
        boolean success = false;
        String id = null;
        try{
            // check it was added
            HashMap<String, String> toSave = createUserInDb();
            List<HashMap<String,String>> fromGet = userDao.get(toSave);
            if(fromGet.size()==0) throw new Exception();
            id = fromGet.get(0).get("UserId");
        } catch (Exception e) {
        } finally {
            // no matter what happens, remove the record
            if(id!=null) {
                success = deleteUser();
            }
            else{success=false;}
        }
        assertTrue(success);
    }

    public static HashMap<String, String> createUserInDb(){
        // add game
        HashMap<String, String> toSave = new HashMap<>(){{
            put("UserId", userId);
            put("Password", password);
            put("DateOfBirth", dateOfBirth);
            put("Name", name);
            put("Id", userId);
        }};
        if(!userDao.save(toSave)) return null;
        return toSave;
    }

    public static boolean deleteUser(){
        HashMap<String, String> toDelete = new HashMap<>(){{
            put("Id", userId);
        }};
        if (!userDao.delete(toDelete)) return false;
        return true;
    }

}