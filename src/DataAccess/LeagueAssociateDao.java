package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO: handle delete and update

public class LeagueAssociateDao extends Dao{
    // single tone
    private static LeagueAssociateDao instance = new LeagueAssociateDao();
    public static LeagueAssociateDao getInstance(){return instance;}

    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        String id = tableKey.get("Id");
        List<HashMap<String, String>> games = new ArrayList<>();
        String query = String.format("SELECT * FROM get_all_league_associate_data WHERE Id = '%s'", id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs);
    }

    public List<HashMap<String, String>> getAll() {
        List<HashMap<String, String>> games = new ArrayList<>();
        String query = String.format("SELECT * FROM get_all_league_associate_data");
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs);
    }

    public boolean save(HashMap<String, String> laData) {
        String Id = laData.get("Id");
        String query = String.format("INSERT INTO LeagueAssociate VALUES('%s')", Id);
        boolean b = this.execute(query);
        return b;
    }

    public boolean update(HashMap<String, String> laData) {
        String Id = laData.get("Id");
        String Password = laData.get("Password");
        String DateOfBirth = laData.get("DateOfBirth");
        String Name = laData.get("Name");
        String query = String.format("UPDATE Users SET Password = %s, DateOfBirth = %s, Name = %s" +
                        "WHERE  UserId = %s",
                Password, DateOfBirth, Name , Id);
        boolean b = this.execute(query);
        return b;
    }

    public boolean delete(HashMap<String, String> laData) {
        String Id = laData.get("Id");
        String query = String.format("DELETE FROM LeagueAssociate" + "WHERE  Id = %s", Id);
        boolean b = this.execute(query);
        return b;
    }

    @Override
    List<HashMap<String, String>> extractDataFromResult(ResultSet rs) {
        if (rs == null){return null;}
        List<HashMap<String, String>> leaguesAssociates = new ArrayList<>();
        try {
            while(rs.next()){
                HashMap<String, String> userData = new HashMap<>();
                userData.put("Id", rs.getString("Id"));
                userData.put("Password", rs.getString("Password"));
                userData.put("DateOfBirth",rs.getString("DateOfBirth"));
                userData.put("Name", rs.getString("Name"));
                leaguesAssociates.add(userData);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return leaguesAssociates;
    }
}
