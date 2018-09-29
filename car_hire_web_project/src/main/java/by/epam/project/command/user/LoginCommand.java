package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.services.impl.UserService;
import by.epam.project.entity.User;
import by.epam.project.exception.ProjectException;

public class LoginCommand extends Command {

    private UserService userService;
    private User user;

    public LoginCommand(User user) {
        this.userService = new UserService();
        this.user = user;
    }


    @Override
    public boolean execute() throws ProjectException {
        boolean executed;
        executed = userService.login(this.user);
        return executed;
    }

}
