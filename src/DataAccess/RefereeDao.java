package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//TODO: handle delete and update

public class RefereeDao extends Dao{
    // single tone
    private static RefereeDao instance = new RefereeDao();
    public static RefereeDao getInstance(){return instance;}
    private RefereeDao(){}
    public List<HashMap<String, String>> get(HashMap<String, String> tableKey) {
        String id = tableKey.get("Id");
        List<HashMap<String, String>> games = new ArrayList<>();
        String query = String.format("SELECT * FROM get_all_referees_data WHERE Id = '%s'", id);
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList(
                "Id", "Name", "Password", "DateOfBirth")));
    }

    public List<HashMap<String, String>> getAll() {
        List<HashMap<String, String>> games = new ArrayList<>();
        String query = String.format("SELECT * FROM get_all_referees_data");
        ResultSet rs = this.executeAndGet(query);
        return this.extractDataFromResult(rs, new ArrayList<String>(Arrays.asList(
                                                "Id", "Name", "Password", "DateOfBirth")));
    }

    public boolean save(HashMap<String, String> refereeData) {
        String Id = refereeData.get("Id");
        String query = String.format("INSERT INTO Referee VALUES('%s')", Id);
        boolean b = this.execute(query);
        return b;
    }

    public boolean update(HashMap<String, String> refereeData) {
        String Id = refereeData.get("Id");
        String Password = refereeData.get("Password");
        String DateOfBirth = refereeData.get("DateOfBirth");
        String Name = refereeData.get("Name");
        String query = String.format("UPDATE Users SET Password = %s, DateOfBirth = %s, Name = %s" +
                                    "WHERE  UserId = %s",
                                     Password, DateOfBirth, Name , Id);
        boolean b = this.execute(query);
        return b;
    }

    public boolean delete(HashMap<String, String> refereeData) {
        String Id = refereeData.get("Id");
        String query = String.format("DELETE FROM Referee" + "WHERE  Id = %s", Id);
        boolean b = this.execute(query);
        return b;
    }

    public List<HashMap<String, String>> GetRefereesForSeason(String leagueName, int season) {
        String query = String.format(
                "select RefereeId from get_referees_data_for_league " +
                        "where leagueName = '%s' and Season = %s", leagueName, season);
        ResultSet rs = this.executeAndGet(query);
        List<HashMap<String, String>> referees = extractDataFromResult(rs, new ArrayList<>( Arrays.asList("RefereeId")));
        return referees;
    }

}