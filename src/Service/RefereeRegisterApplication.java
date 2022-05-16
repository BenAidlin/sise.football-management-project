package Service;

import Domain.Controllers.RefereeRegisterController;
import Domain.Enums.AddRefereeToLeagueStatus;
import Domain.Enums.RegisterRefereeStatus;

public class RefereeRegisterApplication {
    RefereeRegisterController refereeRegisterController = new RefereeRegisterController();
    public String registerRefereeToSystem(String UserId){
        // the referee must already be a user in the sys
        RegisterRefereeStatus registerRefereeStatus = refereeRegisterController.registerReferee(UserId);
        if(registerRefereeStatus == RegisterRefereeStatus.Success)
            return "Referee successfully registered";
        else
            return "oops, seems we encountered a bad status of: " + registerRefereeStatus.toString();
    }
    public String addRefereeToLeague(String RefereeId, String LeagueId){
        AddRefereeToLeagueStatus status = refereeRegisterController.addRefereeToLeague(RefereeId, LeagueId);
        if(status == AddRefereeToLeagueStatus.Success)
            return "Referee was successfully added to the league";
        else return "oops, seems we encountered a bad status of: " + status.toString();
    }

}
