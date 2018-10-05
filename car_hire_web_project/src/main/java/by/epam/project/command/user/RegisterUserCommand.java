package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.services.impl.UserService;
import by.epam.project.model.entity.User;
import by.epam.project.exception.ProjectException;
import by.epam.project.language.LangResourceManager;

import javax.servlet.http.HttpServletRequest;


public class RegisterUserCommand implements Command {

    private UserService userService = new UserService();
    private String message;


    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        int answer = -500;

        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        User user = new User(email, pass);

        answer = userService.register(user);

        LangResourceManager langManager = LangResourceManager.INSTANCE;

        switch (answer) {
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
