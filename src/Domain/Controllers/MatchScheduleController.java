package Domain.Controllers;

import DataAccess.GameDao;
import DataAccess.RefereeDao;
import DataAccess.TeamDao;
import Domain.Elements.Game;
import Domain.Elements.Team;
import Domain.Enums.GameScheduleStatus;
import Domain.Enums.ScheduelsPolicies;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MatchScheduleController {
    GameDao gameDao;
    TeamDao teamDao;
    RefereeDao refDao;

    public MatchScheduleController() {
        this.gameDao = GameDao.getInstance();
        this.teamDao = TeamDao.getInstance();
        this.refDao = RefereeDao.getInstance();
    }

    public String getTeamsIdIfExists(String teamName){
        return teamDao.getTeamId(teamName);
    }

    public boolean checkDatesAvailable(String homeTeamName, String awayTeamName, String date) {
        List<String> awayDates = this.teamDao.getAllGameDatesOfTeam(awayTeamName);
        List<String> homeDates = this.teamDao.getAllGameDatesOfTeam(homeTeamName);
        awayDates.addAll(homeDates);
        for (String d: awayDates
        ) {
            if(d.equals(date)){
                return false;
            }
        }
        return true;
    }

    public GameScheduleStatus scheduleGame(String leagueName, int season, ScheduelsPolicies policy) {
        List<HashMap<String, String>> refs = refDao.GetRefereesForSeason(leagueName, season);
        List<String> refList = new ArrayList<>();
        for (HashMap<String, String> refId:refs ) {
            refList.add(refId.get("RefereeId"));
        }
        List<HashMap<String, String>> teams = teamDao.GetTeamsForSeason(leagueName, season);
        List<Team> teamList = new ArrayList<>();
        for (HashMap<String, String> team:teams ) {
            teamList.add(new Team(team.get("teamId"), team.get("teamName")));
        }
        List<Game> scheduled;
        if(policy == ScheduelsPolicies.homeAndAway)scheduled = MatchSchedule(teamList, refList, false);
        else if (policy == ScheduelsPolicies.onlyHomeOrAway) scheduled = MatchSchedule(teamList, refList, true);
        else return GameScheduleStatus.NoSuchPolicy;
        if(scheduled == null) return GameScheduleStatus.NotEnoughData;

        for (Game g: scheduled) {
            HashMap<String, String> dataGame = new HashMap<String, String>(){
                {
                    put("HomeTeam", g.getHomeTeamId());
                    put("AwayTeam", g.getAwayTeamId());
                    put("Date", g.getDate().toString());
                    put("Referee", g.getRef());
                }
            };
            boolean b = gameDao.save(dataGame, leagueName, season); // TODO: many db access. need to execute all insertions together
            if(!b) return GameScheduleStatus.ConnectionError;
        }
        return GameScheduleStatus.Success;
    }

    private List<Game> MatchSchedule(List<Team> teams, List<String> refList, boolean oneMatch){
        List<Game> games = new ArrayList<>();
        Date d = new Date();
        d.setDate(d.getDate() +  7);
        int numOfRefs = refList.size();
        int indexOfNextRef = 0;
        for (int i=0;i< teams.size();i++) {
            Team homeTeam = teams.get(i);
            int j;
            if(oneMatch) j=i+1; else j=0;
            for (j=j;j< teams.size();j++) {
                if (i==j) continue;
                Team awayTeam = teams.get(j);
                if(indexOfNextRef==numOfRefs) indexOfNextRef=0;
                Date d_to_set = new Date();
                d_to_set.setDate(d.getDate());
                Game g = new Game(homeTeam,awayTeam,refList.get(indexOfNextRef),d_to_set);
                d.setDate(d.getDate() +  7);
                games.add(g);
                indexOfNextRef++;
            }
        }
        return  games;
    }
}
