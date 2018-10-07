package by.epam.project.services.impl;

import by.epam.project.services.Service;
import by.epam.project.dao.impl.UserDao;
import by.epam.project.model.entity.User;
import by.epam.project.exception.ProjectException;
import by.epam.project.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserService implements Service {

    private final static Logger LOG = LogManager.getRootLogger();
    private UserDao userDao = new UserDao();

    /**
     * Returned values:
     * -100 - Something went wrong...
     * -1 - Email or password is incorrect!
     *  0 - User with this Email is already exist!
     *  1 - User registered. Welcome!
     *
     *  User's status:
     *  -1 - User with that Email doesn't exist.
     *   0 - User in the process of registration.
     *   1 - User registered.
     * */
    public int register(User user) throws ProjectException {

        if (!UserValidator.checkUser(user.getEmail(), user.getPassword())) {
            return -1;
        }
        else {
            int userStatus = userDao.checkStatusByEmail(user.getEmail());

            if(userStatus == -1){ // User with that Email doesn't exist
                if (userDao.insert(user)) {
                    LOG.info("User " + user.getEmail() + " registered.");
                    return 1;
                }
                else {
                    LOG.info("User wasn't registered!");
                    return -100;
                }
            }
            else if (userStatus == 0){ // User in the process of registration
                return 0;
            }
            else if (userStatus == 1){ // user registered
                return 0;
            }
            else{
                return -100; // smth went wrong
            }
        }

    }


    public boolean login(User user) throws ProjectException {
        boolean userLogin;
        userLogin = userDao.contains(user); // user's Email & Password is already exist
        return userLogin;
    }


    public boolean deleteByEmail(User user) throws ProjectException {
        boolean userDeleted;
        userDeleted = userDao.delete(user);
        return userDeleted;
    }


    public boolean updateByEmail(User oldUser, User newUser) throws ProjectException {
        boolean userUpdated;
        userUpdated = userDao.update(oldUser, newUser);
        return userUpdated;
    }


    public List<User> takeAllUsers() throws ProjectException {
        List<User> users = userDao.takeAll();
        return users;
    }

}
