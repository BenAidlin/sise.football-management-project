package UnitTests.Mocks;

import DataAccess.IGameDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameDaoMock implements IGameDao {
    boolean b = true;
    List<HashMap<String, String>> returnsLst = new ArrayList<>();
    int num = 0;

    @Override
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        return returnsLst;
    }

    @Override
    public boolean save(HashMap<String, String> gameData, String leagueName, int season) {
        this.setNum(this.getNum() + 1);
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
