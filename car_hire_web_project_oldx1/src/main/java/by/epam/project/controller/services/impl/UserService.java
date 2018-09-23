package by.epam.project.controller.services.impl;

import by.epam.project.controller.services.EntityService;
import by.epam.project.model.dao.impl.UserDao;
import by.epam.project.model.entities.User;
import by.epam.project.model.exception.ProjectException;
import by.epam.project.model.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserService implements EntityService<User> {

    private final static Logger LOG = LogManager.getRootLogger();
    private UserDao userDao = new UserDao();

    // Return type - String !?
    public String register(User user) throws ProjectException {

        if (!UserValidator.checkUser(user.getEmail(), user.getPassword())) {
            return "Email or password is incorrect!";
        }

        if(userDao.contains(user)){ // if user's Email & Password are already exist
            return "Welcome!";
        }
        else {
            int userStatus = userDao.checkStatusByEmail(user.getEmail());
            if(userStatus == -1){ // User with that Email doesn't exist
                if (userDao.insert(user)) {
                    LOG.info("User " + user.getEmail() + " registered.");
                    return "You are registered. Welcome!";
                }
                else {
                    LOG.info("User wasn't registered!");
                    return "Something went wrong...";
                }
            }
            else{
                return "Password is wrong!";
            }
        }

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

/*
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");

        UserDao userDao = new UserDao();
        User user = new User(email, pass);

        resp.setContentType("text/plain");  // Set content type so that jQuery knows what it can expect.
        resp.setCharacterEncoding("UTF-8");

        int userStatus = userDao.checkStatusByEmail(email);

        //////////////////////  Validation  //////////////////////
        if (userStatus == 0) { // in the process of registration.
            resp.getWriter().write("Check your Email to confirm registration.");
        } else if (userStatus == 1) { // registered.
            resp.getWriter().write("User with this Email is already registered!");
        } else if (!UserValidator.checkUser(email, pass)) {
            resp.getWriter().write("Email or password is wrong.");
        }
        //////////////////////  Validation  //////////////////////

        else if (userDao.insert(user)) {
            resp.getWriter().write("User registered!");
            LOG.info("User registered!");
        } else {
            resp.getWriter().write("Something went wrong...");
        }
*/