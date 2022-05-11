package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TeamDao extends Dao{
    //single tone
    private static TeamDao instance = new TeamDao();
    public static TeamDao getInstance(){return instance;}
    private TeamDao(){}

    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        String id = tableKey.get("Id");
        String query = String.format("SELECT * FROM Teams WHERE Id = '%s'" , id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList("Id", "Name")));
    }

    public List<HashMap<String, String>> getAll() {
        String query = String.format("SELECT * FROM Teams");
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList("Id", "Name")));
    }

    public boolean save(HashMap<String, String> teamData) {
        String Name = teamData.get("Name");
        String query = String.format("INSERT INTO Teams VALUES('%s')", Name);
        boolean b = this.execute(query);
        return b;
    }

    public boolean update(HashMap<String, String> teamData) {
        String Id = teamData.get("Id");
        String Name = teamData.get("Name");
        String query = String.format("UPDATE Teams SET Name = %s" + "WHERE Id = %s", Id, Name);
        boolean b = this.execute(query);
        return b;
    }

    public boolean delete(HashMap<String, String> teamData) {
        String Id = teamData.get("Id");
        String query = String.format("DELETE FROM Teams WHERE Id = %s", Id);
        boolean b = this.execute(query);
        return b;
    }

    public List<String> getAllGameDatesOfTeam(String teamName){
        String query =
                String.format("SELECT date as date from Games inner join Teams on " +
                        "Games.AwayTeam = Teams.Id or Games.HomeTeam = Teams.Id " +
                        "WHERE Teams.Name = '%s' And Games.Date is not NULL", teamName);
        ResultSet rs = this.executeAndGet(query);
        List<HashMap<String, String>> hmLst = this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList("date")));
        List<String> dates = new ArrayList<>();
        for (HashMap<String, String> hm: hmLst
             ) {
            dates.add(hm.get("date"));
        }
        return dates;
    }

    public String getTeamId(String teamName) {
        String query = String.format("SELECT Id FROM Teams WHERE Name = '%s'", teamName);
        ResultSet rs = this.executeAndGet(query);
        List<HashMap<String, String>> ids = extractDataFromResult(rs, new ArrayList<>( Arrays.asList("id")));
        if (ids.size() == 0) return null;
        else return ids.get(0).get("Id");
    }

    public List<HashMap<String, String>> GetTeamsForSeason(String leagueName, int season) {
        String query = String.format(
                "select teamId, teamName from get_teams_data_for_league " +
                        "where leagueName = '%s' and leagueSeason = %s", leagueName, season);
        ResultSet rs = this.executeAndGet(query);
        List<HashMap<String, String>> teams = extractDataFromResult(rs, new ArrayList<>( Arrays.asList("teamId", "teamName")));
        return teams;
    }
}
