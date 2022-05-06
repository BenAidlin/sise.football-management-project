package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamDao extends Dao{
    //single tone
    private static TeamDao instance = new TeamDao();
    public static TeamDao getInstance(){return instance;}

    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        String id = tableKey.get("Id");
        String query = String.format("SELECT * FROM Teams WHERE Id = '%s'" , id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs);
    }

    public List<HashMap<String, String>> getAll() {
        String query = String.format("SELECT * FROM Teams");
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs);
    }

    public boolean save(HashMap<String, String> teamData) {
        String Id = teamData.get("Id");
        String Name = teamData.get("Name");
        String query = String.format("INSERT INTO Teams VALUES('%s', '%s'')", Id, Name);
        boolean b = this.execute(query);
        return b;
    }

    public boolean update(HashMap<String, String> teamData) {
        String Id = teamData.get("Id");
        String Name = teamData.get("Name");
        String query = String.format("UPDATE Teams SET Name = %s" + "WHERE Id = %s", Id, Name);
        boolean b = this.execute(query);
        return b;
    }

    public boolean delete(HashMap<String, String> teamData) {
        String Id = teamData.get("Id");
        String query = String.format("DELETE FROM Teams WHERE Id = %s", Id);
        boolean b = this.execute(query);
        return b;
    }

    @Override
    List<HashMap<String, String>> extractDataFromResult(ResultSet rs) {
        if (rs == null){return null;}
        List<HashMap<String, String>> teams = new ArrayList<>();
        try {
            while(rs.next()){
                HashMap<String, String> teamData = new HashMap<>();
                teamData.put("Id", rs.getString("Id"));
                teamData.put("Name", rs.getString("Name"));
                teams.add(teamData);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teams;
    }
}
