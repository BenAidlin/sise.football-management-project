package Domain.Elements;
import java.util.Date;

public class Game {
    Team homeTeam;
    Team awayTeam;
    Referee ref;
    Date date;

    public Game(Team homeTeam, Team awayTeam, Referee ref, Date date) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ref = ref;
        this.date = date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Referee getRef() {
        return ref;
    }

    public void setRef(Referee ref) {
        this.ref = ref;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHomeTeamId(){
        return homeTeam.getId();
    }

    public String getAwayTeamId(){
        return awayTeam.getId();
    }
    public String getHomeTeamName(){
        return homeTeam.getName();
    }
    public String getAwayTeamName(){
        return awayTeam.getName();
    }
}
