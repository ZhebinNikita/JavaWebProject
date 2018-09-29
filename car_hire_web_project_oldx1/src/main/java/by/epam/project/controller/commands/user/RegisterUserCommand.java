package by.epam.project.controller.commands.user;

import by.epam.project.controller.commands.Command;
import by.epam.project.controller.services.impl.UserService;
import by.epam.project.model.entities.User;
import by.epam.project.model.exception.ProjectException;

public class RegisterUserCommand extends Command {

    private UserService userService;
    private User user;
    private int answer;
    private String message;

    public RegisterUserCommand(User user) {
        this.userService = new UserService();
        this.user = user;
        this.answer = -5;
        this.message = "Empty message.";
    }


    @Override
    public boolean execute() throws ProjectException {
        answer = userService.register(this.user);

        switch (answer){
            case 1:
                message = "You are registered.";
                return true;
            case 0:
                message = "User with this Email is already exist!";
                return false;
            case -1:
                message = "Email or password is incorrect!";
                return false;
            case -2:
                message = "Something went wrong...";
                return false;

                default:
                    return false;
        }

    }


    public String getMessage() {
        return message;
    }

}
