package UnitTests.Mocks;

import DataAccess.IUserDao;

import java.util.HashMap;
import java.util.List;

public class UserDaoMock implements IUserDao {

    private List<HashMap<String, String>> returnFromGet;
    private List<HashMap<String, String>> returnFromGetAll;
    private boolean returnFromSave;

    @Override
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        return returnFromGet;
    }

    @Override
    public List<HashMap<String, String>> getAll() {
        return returnFromGetAll;
    }

    @Override
    public boolean save(HashMap<String, String> userData) {
        return returnFromSave;
    }

    public List<HashMap<String, String>> getReturnFromGet() {
        return returnFromGet;
    }

    public void setReturnFromGet(List<HashMap<String, String>> returnFromGet) {
        this.returnFromGet = returnFromGet;
    }

    public List<HashMap<String, String>> getReturnFromGetAll() {
        return returnFromGetAll;
    }

    public void setReturnFromGetAll(List<HashMap<String, String>> returnFromGetAll) {
        this.returnFromGetAll = returnFromGetAll;
    }

    public boolean isReturnFromSave() {
        return returnFromSave;
    }

    public void setReturnFromSave(boolean returnFromSave) {
        this.returnFromSave = returnFromSave;
    }
}
