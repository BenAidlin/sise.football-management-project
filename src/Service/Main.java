package Service;
import DataAccess.UserDao;
import Domain.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserApplication ua = new UserApplication();
        System.out.println(ua.signUp("Ben", "ben123", new Date(), "Ben Aidlin"));
    }
}