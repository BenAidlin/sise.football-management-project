package Service;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
/*        UserApplication ua = new UserApplication();
        System.out.println(ua.signUp("Ben", "ben123", new Date(), "Ben Aidlin"));*/

        GameConfigApplication gca = new GameConfigApplication();
        System.out.println(gca.gamesSchedule("chempion", 2020, "sadfadadsa"));
    }
}