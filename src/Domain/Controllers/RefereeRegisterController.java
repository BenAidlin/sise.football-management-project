package Domain.Controllers;

import DataAccess.LeagueInSeasonDao;
import DataAccess.RefereeDao;
import DataAccess.UserDao;
import Domain.Elements.User;
import Domain.Enums.AddRefereeToLeagueStatus;
import Domain.Enums.RegisterRefereeStatus;

import java.util.HashMap;
import java.util.List;

public class RefereeRegisterController {
    RefereeDao refereeDao;
    UserDao userDao;
    LeagueInSeasonDao leagueInSeasonDao;

    public RefereeRegisterController() {
        this.refereeDao = RefereeDao.getInstance();
        this.userDao = UserDao.getInstance();
        this.leagueInSeasonDao = LeagueInSeasonDao.getInstance();
    }

    public RegisterRefereeStatus registerReferee(String userId) {
        HashMap<String, String> idToInsert = new HashMap<>();
        idToInsert.put("Id", userId);
        // check user in sys
        List<HashMap<String, String>> returnedFromUser = userDao.get(idToInsert);
        if(returnedFromUser.size()==0) return  RegisterRefereeStatus.NoSuchUser;
        // check no such ref
        List<HashMap<String, String>> returnedFromRef = refereeDao.get(idToInsert);
        if(returnedFromRef.size()!=0) return  RegisterRefereeStatus.AlreadyExists;

        System.out.println("sending email to "+userId+"...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("mail was sent!");
        if(refereeDao.save(idToInsert)) return RegisterRefereeStatus.Success;
        return RegisterRefereeStatus.SomethingWentWrong;
    }

    public AddRefereeToLeagueStatus addRefereeToLeague(String refereeId, String leagueId) {
        // check referee exists
        HashMap<String, String> queryData = new HashMap<>();
        queryData.put("Id", refereeId);
        List<HashMap<String, String>> returnedFromRef = refereeDao.get(queryData);
        if(returnedFromRef.size()==0) return AddRefereeToLeagueStatus.RefereeNotExists;
        // check league exists
        HashMap<String,String> queryDataLeague = new HashMap<String,String>();
        queryDataLeague.put("Id", leagueId);
        List<HashMap<String, String>> leagueData = leagueInSeasonDao.getById(queryDataLeague);
        if(leagueData.size()==0) return  AddRefereeToLeagueStatus.LeagueNotExists;
        // check referee not in league
        List<HashMap<String, String>> refInLeague = refereeDao.GetRefereeInLeague(refereeId, leagueId);
        if(refInLeague.size()!=0) return AddRefereeToLeagueStatus.RefAlreadyInLeague;

        if(refereeDao.AddRefereeToLeague(refereeId, leagueId))
            return AddRefereeToLeagueStatus.Success;
        else return AddRefereeToLeagueStatus.SomethingWentWrong;
    }
}
