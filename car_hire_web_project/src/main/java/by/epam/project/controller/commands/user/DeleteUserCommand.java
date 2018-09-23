package by.epam.project.controller.commands.user;

import by.epam.project.controller.commands.Command;
import by.epam.project.controller.services.impl.UserService;
import by.epam.project.model.entities.User;
import by.epam.project.model.exception.ProjectException;

public class DeleteUserCommand extends Command {

    private UserService userService;
    private String email;

    public DeleteUserCommand(String email) {
        this.userService = new UserService();
        this.email = email;
    }


    @Override
    public boolean execute() throws ProjectException {
        boolean executed;
        executed = userService.deleteByEmail(new User(this.email, null));
        return executed;
    }

}
