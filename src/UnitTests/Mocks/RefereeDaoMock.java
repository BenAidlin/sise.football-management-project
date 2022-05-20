package UnitTests.Mocks;

import DataAccess.IRefereeDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RefereeDaoMock implements IRefereeDao {
    boolean b = true;
    List<HashMap<String, String>> returnsLst = new ArrayList<>();

    public List<HashMap<String, String>> getReturnsLstFromRefInLeague() {
        return returnsLstFromRefInLeague;
    }

    public void setReturnsLstFromRefInLeague(List<HashMap<String, String>> returnsLstFromRefInLeague) {
        this.returnsLstFromRefInLeague = returnsLstFromRefInLeague;
    }

    List<HashMap<String, String>> returnsLstFromRefInLeague = new ArrayList<>();
    @Override
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        return returnsLst;
    }

    @Override
    public boolean save(HashMap<String, String> refereeData) {
        return b;
    }

    @Override
    public List<HashMap<String, String>> GetRefereesForSeason(String leagueName, int season) {
        return returnsLst;
    }

    @Override
    public List<HashMap<String, String>> GetRefereeInLeague(String refereeId, String leagueId) {
        return returnsLstFromRefInLeague;
    }

    @Override
    public boolean AddRefereeToLeague(String refereeId, String leagueId) {
        return b;
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
