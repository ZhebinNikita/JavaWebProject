package by.epam.project.command.ajax.user;

import by.epam.project.command.Command;
import by.epam.project.entity.Account;
import by.epam.project.lang.LangResourceManager;
import by.epam.project.service.impl.AccountService;
import by.epam.project.service.impl.UserService;
import by.epam.project.entity.User;
import by.epam.project.exception.ProjectException;
import by.epam.project.validation.XssValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.project.constant.ClientRole.ROLE;
import static by.epam.project.constant.ClientRole.ROLE_ADMIN;
import static by.epam.project.validation.XssValidator.xssValidate;



public class LoginCommand implements Command {

    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "pass";
    private static final String PARAM_BALANCE = "balance";
    private static final Logger LOG = LogManager.getRootLogger();
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        HttpSession session = req.getSession();

        String email = req.getParameter(PARAM_EMAIL);
        String pass = req.getParameter(PARAM_PASSWORD);


        ////////////////////////// XSS validation
        email = xssValidate(email);
        pass = xssValidate(pass);


        User user = new User(email, pass);

        String message;

        if(userService.login(user)) {
            session.setAttribute(PARAM_EMAIL, email);

            if(userService.isAdmin(email)){
                session.setAttribute(ROLE, ROLE_ADMIN);
            }

            Account account = accountService.take(email);
            session.setAttribute(PARAM_BALANCE, account.getBalance());

            message = langManager.getString("user.logged.in");
            resp.getWriter().write(message);
        }
        else{
            int answer = -500;

            answer = userService.register(user);

            switch (answer) {
                case 1:
                    message = langManager.getString("registered");
                    session.setAttribute(PARAM_EMAIL, email); // Associate the session with the user
                    Account account = accountService.take(email);
                    session.setAttribute(PARAM_BALANCE, account.getBalance());
                    resp.getWriter().write(message);
                    break;
                case 0:
                    message = langManager.getString("user.exist") + " "
                            + langManager.getString("user.password.wrong");
                    resp.getWriter().write(message);
                    break;
                case -1:
                    message = langManager.getString("email.pass.incorrect");
                    resp.getWriter().write(message);
                    break;
                case -2:
                    message = langManager.getString("email.pass.incorrect");
                    resp.getWriter().write(message);
                    break;
                default:
                    message = langManager.getString("smth.went.wrong");
                    resp.getWriter().write(message);
                    break;
            }

        }

    }




}
