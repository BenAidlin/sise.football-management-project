package UnitTests.Mocks;

import DataAccess.IUserDao;

import java.util.HashMap;
import java.util.List;

public class UserDaoMock implements IUserDao {
    @Override
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        return null;
    }

    @Override
    public List<HashMap<String, String>> getAll() {
        return null;
    }

    @Override
    public boolean save(HashMap<String, String> userData) {
        return false;
    }
}
