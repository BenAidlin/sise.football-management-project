package Service;

import Domain.Controllers.MatchScheduleController;
import Domain.Enums.GameScheduleStatus;
import Domain.Enums.ScheduelsPolicies;

public class GameConfigApplication {
    MatchScheduleController controller = new MatchScheduleController();
/*    public String gameSchedule(String homeTeamName, String awayTeamName, String Date){
        GameScheduleStatus s = controller.scheduleGame(homeTeamName, awayTeamName, Date);
        String toRet = "Game successfully scheduled!";
        if(s!=GameScheduleStatus.Success)
            toRet = "oops, seems we encountered a bad status of: " + s.toString();
        return toRet;
    }*/

    public String gamesSchedule(String leagueName, int season, String policy){
        ScheduelsPolicies p = ScheduelsPolicies.homeAndAway;
        if(policy.equals("one game")) p = ScheduelsPolicies.onlyHomeOrAway;
        GameScheduleStatus s = controller.scheduleGame(leagueName, season, p);
        String toRet = "Games successfully scheduled!";
        if(s!=GameScheduleStatus.Success)
            toRet = "oops, seems we encountered a bad status of: " + s.toString();
        return toRet;
    }
}
