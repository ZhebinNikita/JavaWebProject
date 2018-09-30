package by.epam.project.controller.commands.user;

import by.epam.project.controller.commands.Command;
import by.epam.project.controller.services.impl.UserService;
import by.epam.project.model.entities.User;
import by.epam.project.model.exception.ProjectException;

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
