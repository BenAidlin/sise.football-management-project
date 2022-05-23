package Service;

import Domain.Controllers.MatchScheduleController;
import Domain.Enums.GameScheduleStatus;
import Domain.Enums.ScheduelsPolicies;

public class GameConfigApplication {
    MatchScheduleController controller = new MatchScheduleController();
    public String gamesSchedule(String leagueName, int season, String policy){
        ScheduelsPolicies p = ScheduelsPolicies.NoSuchPolicy;
        if(policy.equals("one game")) p = ScheduelsPolicies.onlyHomeOrAway;
        else if(policy.equals("two game")) p = ScheduelsPolicies.homeAndAway;
        GameScheduleStatus s = controller.scheduleGame(leagueName, season, p);
        String toRet = "Games successfully scheduled!";
        if(s!=GameScheduleStatus.Success)
            toRet = "oops, seems we encountered a bad status of: " + s.toString();
        return toRet;
    }
}
