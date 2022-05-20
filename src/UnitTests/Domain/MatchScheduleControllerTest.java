package UnitTests.Domain;

import DataAccess.GameDao;
import DataAccess.IGameDao;
import DataAccess.IRefereeDao;
import DataAccess.ITeamDao;
import Domain.Controllers.MatchScheduleController;
import Domain.Enums.GameScheduleStatus;
import Domain.Enums.ScheduelsPolicies;
import Domain.Enums.SignInUpStatus;
import UnitTests.Mocks.GameDaoMock;
import UnitTests.Mocks.RefereeDaoMock;
import UnitTests.Mocks.TeamDaoMock;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScheduleControllerTest {
    static MatchScheduleController matchScheduleController;
    static TeamDaoMock teamDaoMock = new TeamDaoMock();
    static GameDaoMock gameDaoMock = new GameDaoMock();
    static RefereeDaoMock refereeDaoMock = new RefereeDaoMock();

    @BeforeAll
    public static void setUp(){
        matchScheduleController = new MatchScheduleController(gameDaoMock, teamDaoMock, refereeDaoMock);
    }

    @Test
    void scheduleGame_unexistingPolicy(){
        GameScheduleStatus expected = GameScheduleStatus.NoSuchPolicy;
        GameScheduleStatus existing =  MatchScheduleControllerTest.matchScheduleController
                                        .scheduleGame("", 0, ScheduelsPolicies.NoSuchPolicy);
        assertEquals(expected, existing);
    }

    @Test
    void scheduleGame_numberOfGamesTestHomeAndAway(){
        scheduleGame_numberOfGamesHelper(ScheduelsPolicies.homeAndAway, 12, true, GameScheduleStatus.Success);
    }
    @Test
    void scheduleGame_numberOfGamesTestOnlyHomeOrAway(){
        scheduleGame_numberOfGamesHelper(ScheduelsPolicies.onlyHomeOrAway, 6, true, GameScheduleStatus.Success);
    }
    @Test
    void scheduleGame_connectionError(){
        scheduleGame_numberOfGamesHelper(ScheduelsPolicies.onlyHomeOrAway, 6, false, GameScheduleStatus.ConnectionError);
    }

    void scheduleGame_numberOfGamesHelper(ScheduelsPolicies s, int expected, boolean saveSuccess, GameScheduleStatus expectedStatus){
        // prepare teams data
        List<HashMap<String, String>> teamsToSet = new ArrayList<>();
        for(int i=0;i<4;i++){
            HashMap<String, String> toInsert = new HashMap<>();
            toInsert.put(String.valueOf(i), String.valueOf(i));
            teamsToSet.add(toInsert);
        }
        teamDaoMock.setReturnsLst(teamsToSet);
        // prepare referees data
        List<HashMap<String, String>> refereesToSet = new ArrayList<>();
        for(int i=0;i<2;i++){
            HashMap<String, String> toInsert = new HashMap<>();
            toInsert.put(String.valueOf(i), String.valueOf(i));
            refereesToSet.add(toInsert);
        }
        refereeDaoMock.setReturnsLst(refereesToSet);

        gameDaoMock.setB(saveSuccess);
        GameScheduleStatus returnStatus =  MatchScheduleControllerTest.matchScheduleController
                .scheduleGame("", 0, s);
        int actual = gameDaoMock.getNum();
        assertEquals(expected, actual);
        assertEquals(returnStatus, expectedStatus);
    }

    @AfterEach
    public void reset(){
        gameDaoMock.setNum(0);
    }
}

