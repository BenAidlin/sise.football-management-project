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

/*    public GameScheduleStatus scheduleGame(String homeTeamName, String awayTeamName, String date) {
        if(!checkDatesAvailable(homeTeamName, awayTeamName, date)) return GameScheduleStatus.DateTaken;
        String hTeamId = getTeamsIdIfExists(homeTeamName);
        String aTeamId = getTeamsIdIfExists(awayTeamName);
        if(hTeamId == null || aTeamId == null) return GameScheduleStatus.TeamNotExists;
        // TODO: ref scheduling ?
        boolean b = gameDao.save(new HashMap<String, String>() {{
            put("HomeTeam", hTeamId);
            put("AwayTeam", aTeamId);
            put("Date", date);
            put("Referee", null);
        }});
        if(!b) return GameScheduleStatus.ConnectionError;
        return GameScheduleStatus.Success;
    }*/

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
        if(policy == ScheduelsPolicies.simple)scheduled = randomSchedule(teamList, refList);
        else if (policy == ScheduelsPolicies.smart) scheduled = smartSchedule(teamList, refList);
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

    private List<Game> randomSchedule(List<Team> teams, List<String> refList){
        List<Game> games = new ArrayList<>();
        Date d = new Date();
        d.setDate(d.getDate() +  7);
        int numOfRefs = refList.size();
        int indexOfNextRef = 0;
        for (Team homeTeam: teams ) {
            for (Team awayTeam: teams) {
                if(homeTeam.getId().equals(awayTeam.getId())) continue;
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
    private List<Game> smartSchedule(List<Team> teams, List<String> refList){
        List<Game> games = new ArrayList<>();
        if(refList.size()<(teams.size()/2)) return null;
        Date d = new Date();
        d.setDate(d.getDate() +  30);
        for(int i=0; i<teams.size();i++){
            int indexOfNextRef = 0;
            d.setDate(d.getDate() +  7);
            for(int j=0;j<teams.size();j++){
                Team homeTeam = teams.get(j);
                int other = teams.size()-j-i-1;
                while(other<0)other+=teams.size();
                Team awayTeam = teams.get(other);
                Game g = new Game(homeTeam,awayTeam,refList.get(indexOfNextRef % refList.size()),d);
                games.add(g);
                indexOfNextRef+=1;
                if(j>=teams.size()/2)
                    d.setDate(d.getDate() +  7);
            }
        }
        return games;
    }
}
