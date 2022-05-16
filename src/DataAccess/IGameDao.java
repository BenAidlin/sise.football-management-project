package DataAccess;

import java.util.HashMap;
import java.util.List;

public interface IGameDao {
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey);
    public boolean save(HashMap<String, String> gameData, String leagueName, int season);
}
