package UnitTests.Mocks;

import DataAccess.ILeagueInSeasonDao;

import java.util.HashMap;
import java.util.List;

public class LeagueInSeasonMock implements ILeagueInSeasonDao {
    List<HashMap<String, String>> returnFromMock;

    @Override
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        return returnFromMock;
    }

    @Override
    public List<HashMap<String, String>> getById(HashMap<String, String> tableKey) {
        return returnFromMock;
    }

    public List<HashMap<String, String>> getReturnFromMock() {
        return returnFromMock;
    }

    public void setReturnFromMock(List<HashMap<String, String>> returnFromMock) {
        this.returnFromMock = returnFromMock;
    }
}
