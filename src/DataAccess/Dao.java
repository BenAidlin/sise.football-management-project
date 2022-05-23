package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
//
//public interface Dao<T> {
//    List<T> get(HashMap<String, String> tableKey);
//    List<T> getAll();
//    boolean save(T t);
//    boolean update(T t);
//    boolean delete(T t);
//}

abstract class Dao {
    DbConnector connector = DbConnector.getInstance();

    public ResultSet executeAndGet(String query) {
        try {
            ResultSet rs = connector.getConnection().createStatement().executeQuery(query);
            return rs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean execute(String query) {
        try {
            connector.getConnection().createStatement().executeUpdate(query);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    List<HashMap<String, String>> extractDataFromResult(ResultSet rs, List<String> fields) {
        if (rs == null) {
            return null;
        }
        List<HashMap<String, String>> objs = new ArrayList<>();
        try {
            while (rs.next()) {
                HashMap<String, String> objsData = new HashMap<>();
                for (String field : fields) {
                    objsData.put(field, rs.getString(field));
                }
                objs.add(objsData);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return objs;
    }
}