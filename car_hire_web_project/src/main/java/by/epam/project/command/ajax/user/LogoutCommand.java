package by.epam.project.command.ajax.user;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.language.LangResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {

    private static final String PARAM_EMAIL = "email";
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        HttpSession session = req.getSession(true);

        if (session.getAttribute(PARAM_EMAIL).toString().contains("@")) {
            session.invalidate();
        } else {
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
        }

    }


}
