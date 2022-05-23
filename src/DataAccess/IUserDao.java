package DataAccess;

import java.util.HashMap;
import java.util.List;

public interface IUserDao {
    List<HashMap<String, String>> get(HashMap<String, String> tableKey);
    List<HashMap<String, String>> getAll();
    boolean save(HashMap<String, String> userData);
    boolean delete(HashMap<String, String> userData);
}
