package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GameDao extends Dao{
    // single tone
    private static GameDao instance = new GameDao();
    public static GameDao getInstance(){return instance;}
    private GameDao(){}
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        String Id = tableKey.get("Id");
        List<HashMap<String, String>> games = new ArrayList<>();
        String query = String.format("SELECT * FROM Games WHERE Id = '%s'", Id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList( "Id","HomeTeam", "AwayTeam", "Date", "Referee")));
    }

    public List<HashMap<String, String>> getAll() {
        String query = String.format("SELECT * FROM Games");
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList("Id","HomeTeam", "AwayTeam", "Date", "Referee")));
    }

    public boolean save(HashMap<String, String> gameData, String leagueName, int season) {
        String homeTeamId = gameData.get("HomeTeam");
        String awayTeamId = gameData.get("AwayTeam");
        String date = gameData.get("Date");
        String refId = gameData.get("Referee");

        String query = String.format("INSERT INTO Games VALUES(%s,%s,'%s','%s',ifnull ((SELECT\n" +
                "  max(Id) from Games) + 1,0), (select Id from LeagueInSeason where Name = '%s' and Season = %s))",
                homeTeamId, awayTeamId, refId, date, leagueName, season);
        return this.execute(query);
    }

    public boolean update(HashMap<String, String> gameData) {
        String Id = gameData.get("Id");
        String homeTeamId = gameData.get("HomeTeam");
        String awayTeamId = gameData.get("AwayTeam");
        String date = gameData.get("Date");
        String refId = gameData.get("Referee");
        String query = String.format("UPDATE Games SET Referee = '%s', HomeTeam = '%s', AwayTeam = '%s', Date = '%s'" +
                                     "WHERE  Id = '%s'",
                                      refId, homeTeamId, awayTeamId , date, Id);
        boolean b = this.execute(query);
        return b;
    }

    public boolean delete(HashMap<String, String> gameData) {
        String Id = gameData.get("Id");
        String query = String.format("DELETE FROM Games WHERE  Id = '%s'", Id);
        boolean b = this.execute(query);
        return b;
    }
}
