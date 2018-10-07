package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.services.impl.UserService;
import by.epam.project.model.entity.User;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements Command {

    private UserService userService = new UserService();


    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed;

        String email = req.getParameter("email");
        User user = new User(email, null);

        executed = userService.deleteByEmail(user);

        return executed;
    }

}
