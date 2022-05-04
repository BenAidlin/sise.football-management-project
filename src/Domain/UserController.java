package Domain;

import DataAccess.Dao;
import DataAccess.UserDao;

import java.util.List;

public class UserController {
    Dao userDao;

    public UserController() {
        userDao = UserDao.getInstance();
    }
    public Status insertUser(String userId, String password, String dateOfBirth, String name){
        List<User> usersInSys = userDao.getAll();
        for (User user: usersInSys) {
            if(user.getUserId().equals(userId))
                return Status.UserIdTaken;
        }
        User toInsert = new User(userId, password, dateOfBirth, name);
        boolean res = userDao.save(toInsert);
        if(!res) return Status.SomethingWentWrong;
        return Status.Success;
    }
    public Status logIn(String userId, String password){
        List<User> usersInSys = userDao.getAll();
        User rightUser = null;
        // get all sys users and check if userId exists
        for (User userInSys: usersInSys) {
            if(userInSys.getUserId().equals(userId)) {
                // check passwd
                if (!userInSys.getPassword().equals(password)) return Status.BadPassword;
                // check user not already logged in
                for(User userLoggedIn : User.loggedIn) if(userLoggedIn.getUserId().equals(userId))
                    return Status.AlreadyLoggedIn;
                // password correct and user not logged in yet
                rightUser = userInSys;
            }
        }
        // haven't found userId
        if(rightUser == null) return Status.BadUserName;
        // complete log in
        User.loggedIn.add(rightUser);
        return Status.Success;
    }
}
