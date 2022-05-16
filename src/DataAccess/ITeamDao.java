package DataAccess;

import java.util.HashMap;
import java.util.List;

public interface ITeamDao {
    boolean save(HashMap<String, String> teamData);
    List<HashMap<String, String>> GetTeamsForSeason(String leagueName, int season);
    List<HashMap<String, String>> get(HashMap<String, String> tableKey);
}
