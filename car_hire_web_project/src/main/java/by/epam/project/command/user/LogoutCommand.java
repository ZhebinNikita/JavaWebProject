package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private static final String PARAM_EMAIL = "email";


    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {
        HttpSession session = req.getSession(true);

        try {
            if (session.getAttribute(PARAM_EMAIL).toString().contains("@")) {
                session.invalidate();
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e){
            throw new ProjectException(e);
        }
    }


}
