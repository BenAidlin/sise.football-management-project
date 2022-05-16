package UnitTests.Mocks;

import DataAccess.ITeamDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamDaoMock implements ITeamDao {
    boolean b = true;
    List<HashMap<String, String>> returnsLst = new ArrayList<>();

    @Override
    public boolean save(HashMap<String, String> teamData) {
        return b;
    }

    @Override
    public List<HashMap<String, String>> GetTeamsForSeason(String leagueName, int season) {
        return returnsLst;
    }

    @Override
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        return returnsLst;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public List<HashMap<String, String>> getReturnsLst() {
        return returnsLst;
    }

    public void setReturnsLst(List<HashMap<String, String>> returnsLst) {
        this.returnsLst = returnsLst;
    }
}
