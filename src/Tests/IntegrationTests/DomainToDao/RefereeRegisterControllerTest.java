package Tests.IntegrationTests.DomainToDao;

import DataAccess.RefereeDao;
import Domain.Controllers.RefereeRegisterController;
import Domain.Enums.AddRefereeToLeagueStatus;
import Domain.Enums.RegisterRefereeStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RefereeRegisterControllerTest {
    static RefereeRegisterController refereeRegisterController;
    static RefereeDao refereeDao;
    static String refName = "IntegrationTestRef";
    @BeforeAll
    static void setUp(){
        refereeRegisterController = new RefereeRegisterController();
        refereeDao = RefereeDao.getInstance();
    }

    @Test
    void registerReferee(){
        assertTrue(insertFakeUser());
        RegisterRefereeStatus status = refereeRegisterController.registerReferee(refName);
        try {
            assertTrue(checkRefereeInserted());
        } catch (SQLException throwables) {
            assertTrue(false);
        }
        finally {
            removeReferee();
        }
        assertTrue(status == RegisterRefereeStatus.Success);
    }

    @Test
    void addRefereeToLeague(){
        assertTrue(insertFakeUser());
        RegisterRefereeStatus regStatus = refereeRegisterController.registerReferee(refName);
        AddRefereeToLeagueStatus addStatus = refereeRegisterController.addRefereeToLeague(refName, "0");
        try {
            assertTrue(checkRefereeInLeague());
        } catch (SQLException throwables) {
            assertTrue(false);
        }
        finally {
            removeReferee();
        }
    }

    boolean insertFakeUser(){
        return refereeDao.execute(String.format("insert into Users values('%s','','','')", refName));
    }
    boolean checkRefereeInserted() throws SQLException {
        ResultSet rs = refereeDao.executeAndGet(String.format("select * from Referee Where Id = '%s'", refName));
        int i=0;
        while(rs.next()) i+=1;
        return i>0;
    }
    boolean removeReferee(){
        return (refereeDao.execute(String.format("delete from Referee where Id = '%s'", refName))
            && refereeDao.execute(String.format("delete from Users where UserId = '%s'", refName))
            && refereeDao.execute(String.format("delete from RefereeInLeague where RefereeId = '%s'", refName)));
    }
    boolean checkRefereeInLeague() throws SQLException {
        ResultSet rs = refereeDao.executeAndGet(String.format("select * from RefereeInLeague Where RefereeId = '%s'", refName));
        int i=0;
        while(rs.next()) i+=1;
        return i>0;
    }
}