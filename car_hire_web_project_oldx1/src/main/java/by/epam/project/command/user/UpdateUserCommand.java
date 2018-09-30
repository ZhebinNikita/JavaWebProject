package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.services.impl.UserService;
import by.epam.project.entity.User;
import by.epam.project.exception.ProjectException;


public class UpdateUserCommand extends Command {

    private UserService userService;
    private String oldEmail;
    private String newEmail;

    public UpdateUserCommand(String oldEmail, String newEmail) {
        this.userService = new UserService();
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
    }


    @Override
    public boolean execute() throws ProjectException {
        boolean executed;
        executed = userService.updateByEmail(
                new User(this.oldEmail, null),
                new User(this.newEmail, null));
        return executed;
    }

}
