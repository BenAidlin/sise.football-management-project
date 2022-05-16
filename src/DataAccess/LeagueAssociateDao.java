package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//TODO: handle delete and update

public class LeagueAssociateDao extends Dao{
    // single tone
    private static LeagueAssociateDao instance = new LeagueAssociateDao();
    public static LeagueAssociateDao getInstance(){return instance;}
    private LeagueAssociateDao(){}
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        String id = tableKey.get("Id");
        String query = String.format("SELECT * FROM get_all_league_associate_data WHERE Id = '%s'", id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList(
                "Id", "Name", "Password", "DateOfBirth")));
    }

    public List<HashMap<String, String>> getAll() {
        String query = String.format("SELECT * FROM get_all_league_associate_data");
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList(
                "Id", "Name", "Password", "DateOfBirth")));
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

}
