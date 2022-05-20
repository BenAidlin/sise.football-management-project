package UnitTests.Domain;

import Domain.Controllers.RefereeRegisterController;
import Domain.Enums.AddRefereeToLeagueStatus;
import Domain.Enums.RegisterRefereeStatus;
import UnitTests.Mocks.LeagueInSeasonMock;
import UnitTests.Mocks.RefereeDaoMock;
import UnitTests.Mocks.UserDaoMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RefereeRegisterControllerTest {
    static RefereeRegisterController refereeRegisterController;
    static RefereeDaoMock refereeDaoMock = new RefereeDaoMock();
    static UserDaoMock userDaoMock = new UserDaoMock();
    static LeagueInSeasonMock leagueInSeasonMock = new LeagueInSeasonMock();
    static String userId = "Referee";
    static String leagueId = "myLeague";

    @BeforeAll
    public static void setUp(){
        refereeRegisterController = new RefereeRegisterController(refereeDaoMock, userDaoMock, leagueInSeasonMock);
    }

    // register referee tests

    @Test
    void registerReferee_userNotInSys(){
        RegisterRefereeStatus expected = RegisterRefereeStatus.NoSuchUser;
        // set up user dao to claim there is no such user
        userDaoMock.setReturnFromGet(getListWithoutRow());
        // check when user not exists - can't register ref
        String userId = "Referee";
        RegisterRefereeStatus actual = refereeRegisterController.registerReferee(userId);
        assertEquals(expected, actual);
    }

    @Test
    void registerReferee_refereeAlreadyExists(){
        RegisterRefereeStatus expected = RegisterRefereeStatus.AlreadyExists;
        // set up user dao to claim user exists
        userDaoMock.setReturnFromGet(getListWithRow());
        // set up ref dao to claim ref exists
        refereeDaoMock.setReturnsLst(getListWithRow());
        // check when ref already exists - can't add him
        RegisterRefereeStatus actual = refereeRegisterController.registerReferee(userId);
        assertEquals(expected, actual);
    }

    @Test
    void registerReferee_success(){
        RegisterRefereeStatus expected = RegisterRefereeStatus.Success;
        // set up user dao to claim user exists
        userDaoMock.setReturnFromGet(getListWithRow());
        // set up ref dao to claim ref doesn't exist
        refereeDaoMock.setReturnsLst(getListWithoutRow());
        // check referee adding is enabled when user exists but ref not
        refereeDaoMock.setB(true);
        RegisterRefereeStatus actual = refereeRegisterController.registerReferee(userId);
        assertEquals(expected, actual);
    }

    @Test
    void registerReferee_somethingWentWrong(){
        RegisterRefereeStatus expected = RegisterRefereeStatus.SomethingWentWrong;
        // set up user dao to claim user exists
        userDaoMock.setReturnFromGet(getListWithRow());
        // set up ref dao to claim ref doesn't exist
        refereeDaoMock.setReturnsLst(getListWithoutRow());
        // set up saving to be false
        refereeDaoMock.setB(false);
        RegisterRefereeStatus actual = refereeRegisterController.registerReferee(userId);
        assertEquals(expected, actual);
    }

    // add referee to league tests

    @Test
    void addRefereeToLeague_refNotExists(){
        AddRefereeToLeagueStatus expected = AddRefereeToLeagueStatus.RefereeNotExists;
        // set up ref dao to claim there is no such user
        refereeDaoMock.setReturnsLst(getListWithoutRow());
        // check that when ref not exist cant add to league
        AddRefereeToLeagueStatus actual = refereeRegisterController.addRefereeToLeague(userId, leagueId);
        assertEquals(expected, actual);
    }

    @Test
    void addRefereeToLeague_leagueNotExists(){
        // set up referee to claim such referee exists
        AddRefereeToLeagueStatus expected = AddRefereeToLeagueStatus.LeagueNotExists;
        refereeDaoMock.setReturnsLst(getListWithRow());
        // set up league to claim league doesn't exist
        leagueInSeasonMock.setReturnFromMock(getListWithoutRow());
        // check that when league not exists cant add ref
        AddRefereeToLeagueStatus actual = refereeRegisterController.addRefereeToLeague(userId, leagueId);
        assertEquals(expected, actual);
    }

    @Test
    void addRefereeToLeague_refAlreadyInLeague(){
        AddRefereeToLeagueStatus expected = AddRefereeToLeagueStatus.RefAlreadyInLeague;
        // set up ref dao to claim ref exists
        refereeDaoMock.setReturnsLst(getListWithRow());
        // set up league to claim league exists
        leagueInSeasonMock.setReturnFromMock(getListWithRow());
        // set up referee already in league
        refereeDaoMock.setReturnsLstFromRefInLeague(getListWithRow());
        // check when ref in league cant add him
        AddRefereeToLeagueStatus actual = refereeRegisterController.addRefereeToLeague(userId, leagueId);
        assertEquals(expected, actual);
    }

    @Test
    void addRefereeToLeague_success(){
        AddRefereeToLeagueStatus expected = AddRefereeToLeagueStatus.Success;
        // set up ref dao to claim ref exists
        refereeDaoMock.setReturnsLst(getListWithRow());
        // set up league to claim league exists
        leagueInSeasonMock.setReturnFromMock(getListWithRow());
        // set up referee already in league
        refereeDaoMock.setReturnsLstFromRefInLeague(getListWithoutRow());
        // check when ref not in league you can add it successfully
        refereeDaoMock.setB(true);
        AddRefereeToLeagueStatus actual = refereeRegisterController.addRefereeToLeague(userId, leagueId);
        assertEquals(expected, actual);
    }

    @Test
    void addRefereeToLeague_somethingWentWrong(){
        AddRefereeToLeagueStatus expected = AddRefereeToLeagueStatus.SomethingWentWrong;
        // set up ref dao to claim ref exists
        refereeDaoMock.setReturnsLst(getListWithRow());
        // set up league to claim league exists
        leagueInSeasonMock.setReturnFromMock(getListWithRow());
        // set up referee already in league
        refereeDaoMock.setReturnsLstFromRefInLeague(getListWithoutRow());
        // check when ref not in league you can add it successfully, but something went wrong
        refereeDaoMock.setB(false);
        AddRefereeToLeagueStatus actual = refereeRegisterController.addRefereeToLeague(userId, leagueId);
        assertEquals(expected, actual);
    }

    private List<HashMap<String, String>> getListWithRow(){
        List<HashMap<String, String>> toReturnFromMock = new ArrayList<>();
        HashMap<String, String> hm = new HashMap<>();
        hm.put(userId, leagueId);
        toReturnFromMock.add(hm);
        return toReturnFromMock;
    }

    private List<HashMap<String, String>> getListWithoutRow(){
        List<HashMap<String, String>> toReturnFromMock = new ArrayList<>();
        return toReturnFromMock;
    }
}