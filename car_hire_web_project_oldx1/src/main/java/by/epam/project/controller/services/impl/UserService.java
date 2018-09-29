package by.epam.project.controller.services.impl;

import by.epam.project.controller.services.Service;
import by.epam.project.model.dao.impl.UserDao;
import by.epam.project.model.entities.User;
import by.epam.project.model.exception.ProjectException;
import by.epam.project.model.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserService implements Service {

    private final static Logger LOG = LogManager.getRootLogger();
    private UserDao userDao = new UserDao();

    // Return type - String !?
    public int register(User user) throws ProjectException {

        if (!UserValidator.checkUser(user.getEmail(), user.getPassword())) {
            return -1; // "Email or password is incorrect!";
        }
        else {
            int userStatus = userDao.checkStatusByEmail(user.getEmail());

            if(userStatus == -1){ // User with that Email doesn't exist
                if (userDao.insert(user)) {
                    LOG.info("User " + user.getEmail() + " registered.");
                    return 1; // "Welcome!";
                }
                else {
                    LOG.info("User wasn't registered!");
                    return -2; // "Something went wrong...";
                }
            }
            else{
                return 0; // "User with this Email is already exist!";
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


    public List<User> getAllUsers() throws ProjectException {
        List<User> users = userDao.getAll();
        return users;
    }

}
