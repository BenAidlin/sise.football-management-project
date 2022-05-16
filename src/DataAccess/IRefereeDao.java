package DataAccess;

import java.util.HashMap;
import java.util.List;

public interface IRefereeDao {
    List<HashMap<String, String>> get(HashMap<String, String> tableKey);
    boolean save(HashMap<String, String> refereeData);
    List<HashMap<String, String>> GetRefereesForSeason(String leagueName, int season);
    List<HashMap<String, String>> GetRefereeInLeague(String refereeId, String leagueId);
    boolean AddRefereeToLeague(String refereeId, String leagueId);
}
