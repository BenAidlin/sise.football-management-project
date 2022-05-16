package Service;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
/*        UserApplication ua = new UserApplication();
        System.out.println(ua.signUp("Ben", "ben123", new Date(), "Ben Aidlin"));*/

        RefereeRegisterApplication refereeRegisterApplication = new RefereeRegisterApplication();
        System.out.println( refereeRegisterApplication.addRefereeToLeague("Sara", "12"));
    }
}