package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.services.impl.UserService;
import by.epam.project.model.entity.User;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    private UserService userService = new UserService();


    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed;

        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        User user = new User(email, pass);

        executed = userService.login(user);

        return executed;
    }

}
