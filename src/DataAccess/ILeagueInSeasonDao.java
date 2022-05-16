package DataAccess;

import java.util.HashMap;
import java.util.List;

public interface ILeagueInSeasonDao {

    public List<HashMap<String, String>> get(HashMap<String, String> tableKey);
    public List<HashMap<String, String>> getById(HashMap<String, String> tableKey);
}
