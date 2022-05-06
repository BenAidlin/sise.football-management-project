package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GameDao extends Dao{
    // single tone
    private static GameDao instance = new GameDao();
    public static GameDao getInstance(){return instance;}
    private static int nextId = 0;

    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        String Id = tableKey.get("Id");
        List<HashMap<String, String>> games = new ArrayList<>();
        String query = String.format("SELECT * FROM Games WHERE Id = '%s'", Id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs);
    }

    public List<HashMap<String, String>> getAll() {
        String query = String.format("SELECT * FROM Games");
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs);
    }

    public boolean save(HashMap<String, String> gameData) {
        String homeTeamId = gameData.get("HomeTeam");
        String awayTeamId = gameData.get("AwayTeam");
        String date = gameData.get("Date");
        String refId = gameData.get("Referee");
        String query = String.format("INSERT INTO Games VALUES('%s','%s','%s','%s','%s')",
                                     nextId, homeTeamId, awayTeamId, refId, date);
        boolean b = this.execute(query);
        if(b){nextId++;}
        return b;
    }

    public boolean update(HashMap<String, String> gameData) {
        String Id = gameData.get("Id");
        String homeTeamId = gameData.get("HomeTeam");
        String awayTeamId = gameData.get("AwayTeam");
        String date = gameData.get("Date");
        String refId = gameData.get("Referee");
        String query = String.format("UPDATE Games SET Referee = %s, HomeTeam = %s, AwayTeam = %s, Date = %s" +
                                     "WHERE  Id = %s",
                                      refId, homeTeamId, awayTeamId , date, Id);
        boolean b = this.execute(query);
        return b;
    }

    public boolean delete(HashMap<String, String> gameData) {
        String Id = gameData.get("Id");
        String query = String.format("DELETE FROM Games WHERE  Id = %s", Id);
        boolean b = this.execute(query);
        return b;
    }

    @Override
    List<HashMap<String, String>> extractDataFromResult(ResultSet rs) {
        if (rs == null){return null;}
        List<HashMap<String, String>> games = new ArrayList<>();
        try {
            while(rs.next()){
                HashMap<String, String> gameData = new HashMap<>();
                gameData.put("Id", rs.getString("Id"));
                gameData.put("HomeTeam", rs.getString("HomeTeam"));
                gameData.put("AwayTeam", rs.getString("AwayTeam"));
                gameData.put("Referee",rs.getString("Referee"));
                gameData.put("Date", rs.getString("Date"));
                games.add(gameData);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return games;
    }
}
