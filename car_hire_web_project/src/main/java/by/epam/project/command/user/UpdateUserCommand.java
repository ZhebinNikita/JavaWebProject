package by.epam.project.command.user;

import by.epam.project.command.Command;
import by.epam.project.services.impl.UserService;
import by.epam.project.model.entity.User;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;


public class UpdateUserCommand implements Command {

    private UserService userService = new UserService();


    /**
     * HttpServletRequest's params:
     * 1 - newEmail.
     * 2 - newPassword.
     * 3 - newIdPassword.
     * 4 - newStatus.
     * 5 - newRole.
     * */
    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed;

        String oldEmail = req.getParameter("oldEmail");

        String newEmail = req.getParameter("newEmail");
        String newPassword  = req.getParameter("newPassword");
        int newIdPassport = Integer.valueOf(req.getParameter("newIdPassport"));
        int newStatus = Integer.valueOf(req.getParameter("newStatus"));
        int newRole = Integer.valueOf(req.getParameter("newRole"));

        User oldUser = new User(oldEmail, null);
        User newUser = new User(newEmail, newPassword);
        newUser.setIdPassport(newIdPassport);
        newUser.setStatus(newStatus);
        newUser.setRole(newRole);

        executed = userService.updateByEmail(oldUser, newUser);

        return executed;
    }

}
