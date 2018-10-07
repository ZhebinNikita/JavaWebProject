package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.language.LangResourceManager;
import by.epam.project.services.impl.UserService;
import by.epam.project.model.entity.User;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "pass";
    private UserService userService = new UserService();
    private String message;


    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed;

        HttpSession session = req.getSession();

        LangResourceManager langManager = LangResourceManager.INSTANCE;
        message = langManager.getString("smth.went.wrong");
        String email = req.getParameter(PARAM_EMAIL);
        String pass = req.getParameter(PARAM_PASSWORD);

        User user = new User(email, pass);

        executed = userService.login(user);

        // Associate the session with the user
        if(executed) {
            session.setAttribute("email", email);
            message = langManager.getString("user.logged.in");

            if(userService.isAdmin(email)){
                session.setAttribute("role", "admin");
            }

            return executed;
        }
        else{
            int answer = -500;

            answer = userService.register(user);

            switch (answer) {
                case 1:
                    message = langManager.getString("registered");
                    session.setAttribute("email", email); // Associate the session with the user
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

    }


    public String getMessage() {
        return message;
    }


}
