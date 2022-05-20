package IntegrationTests.DataAccess;

import DataAccess.IUserDao;
import DataAccess.RefereeDao;
import DataAccess.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefereeDaoTest {
    static RefereeDao refereeDao;
    static IUserDao userDao;
    @BeforeAll
    public static void setUp(){
        refereeDao = RefereeDao.getInstance();
        userDao = UserDao.getInstance();
    }
}