package DataAccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LeagueInSeasonDao extends Dao implements ILeagueInSeasonDao{
    // single tone
    private static LeagueInSeasonDao instance = new LeagueInSeasonDao();
    public static LeagueInSeasonDao getInstance(){return instance;}
    private LeagueInSeasonDao(){}

    public List<HashMap<String, String>> getById(HashMap<String, String> tableKey) {
        String id = tableKey.get("Id");
        String query = String.format("SELECT * FROM LeagueInSeason WHERE Id = '%s'", id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList(
                "Id", "Name", "Season")));
    }
}
