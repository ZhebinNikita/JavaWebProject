package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.services.impl.UserService;
import by.epam.project.entity.User;
import by.epam.project.exception.ProjectException;

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
