package DataAccess;

import java.util.List;

public interface Dao<T> {
    List<T> get(String id);
    List<T> getAll();
    boolean save(T t);
    boolean update(T t);
    boolean delete(T t);
}
