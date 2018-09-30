package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.services.impl.UserService;
import by.epam.project.entity.User;
import by.epam.project.exception.ProjectException;
import by.epam.project.language.LangResourceManager;


public class RegisterUserCommand implements Command {

    private UserService userService;
    private User user;
    private int answer;
    private String message;

    public RegisterUserCommand(User user) {
        this.userService = new UserService();
        this.user = user;
        this.answer = -5;
    }


    @Override
    public boolean execute() throws ProjectException {
        answer = userService.register(this.user);

        LangResourceManager langManager = LangResourceManager.INSTANCE;

        switch (answer){
            case 1:
                message = langManager.getString("registered");
                return true;
            case 0:
                message = langManager.getString("user.exist") + " "
                        + langManager.getString("user.password.wrong");
                return false;
            case -1:
                message = langManager.getString("email.pass.incorrect");
                return false;
            case -2:
                message = langManager.getString("email.pass.incorrect");
                return false;

                default:
                    message = langManager.getString("smth.went.wrong");
                    return false;
        }

    }


    public String getMessage() {
        return message;
    }

}
