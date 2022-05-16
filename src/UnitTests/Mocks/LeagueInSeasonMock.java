package UnitTests.Mocks;

import DataAccess.ILeagueInSeasonDao;

import java.util.HashMap;
import java.util.List;

public class LeagueInSeasonMock implements ILeagueInSeasonDao {
    @Override
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        return null;
    }

    @Override
    public List<HashMap<String, String>> getById(HashMap<String, String> tableKey) {
        return null;
    }
}
