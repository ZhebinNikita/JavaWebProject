package by.epam.project.controller.servlets;

import by.epam.project.controller.commands.user.LoginCommand;
import by.epam.project.controller.commands.user.RegisterUserCommand;
import by.epam.project.model.entities.User;
import by.epam.project.model.exception.ProjectException;
import by.epam.project.model.pool.ConnectionPool;
import by.epam.project.model.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet that handle requests of the welcome page.
 * */
@WebServlet(name = "StartServlet", urlPatterns = "")
public class StartServlet extends HttpServlet {

    private final static Logger LOG = LogManager.getRootLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /* Set "*.jsp" to this URL address */
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/view/start.jsp");
        requestDispatcher.forward(req, resp);

    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (isAjax) {

            String action = req.getParameter("action");

            if(action.compareTo("add_user") == 0){

                String email = req.getParameter("email");
                String pass = req.getParameter("pass");
                User user = new User(email, pass);

                LoginCommand loginCommand = new LoginCommand(user);

                try {
                    boolean login = loginCommand.execute();
                    if (login) { // User with these data exist and logged in
                        // session acts
                        LOG.info("User logged in.");
                        resp.getWriter().write("User logged in.");
                        // session acts
                    } else {
                        String message;
                        RegisterUserCommand registerUserCommand = new RegisterUserCommand(user);

                        boolean registered = registerUserCommand.execute();
                        message = registerUserCommand.getMessage();
                        if (registered) {
                            // session acts
                            LOG.info(message);
                            resp.getWriter().write(message);
                            // session acts
                        } else {
                            LOG.info(message);
                            resp.getWriter().write(message);
                        }
                    }
                }
                catch (ProjectException e){
                    LOG.error(e);
                    resp.getWriter().write("Something went wrong. Try again later.");
                }

            }
        }

    }


    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
            LOG.info("Connection Pool is initialized!");
        }
        catch (ConnectionPoolException e) {
            LOG.error("Connection Pool is not initialized!", e);
            throw new ServletException(e);
        }
    }


    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.dispose();
        LOG.info("Connection Pool is destroyed!");
    }


}

/*
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");

        UserDao userDao = new UserDao();
        User user = new User(email, pass);

        resp.setContentType("text/plain");  // Set content type so that jQuery knows what it can expect.
        resp.setCharacterEncoding("UTF-8");

        int userStatus = userDao.checkStatusByEmail(email);

        //////////////////////  Validation  //////////////////////
        if (userStatus == 0) { // in the process of registration.
            resp.getWriter().write("Check your Email to confirm registration.");
        } else if (userStatus == 1) { // registered.
            resp.getWriter().write("User with this Email is already registered!");
        } else if (!UserValidator.checkUser(email, pass)) {
            resp.getWriter().write("Email or password is wrong.");
        }
        //////////////////////  Validation  //////////////////////

        else if (userDao.insert(user)) {
            resp.getWriter().write("User registered!");
            LOG.info("User registered!");
        } else {
            resp.getWriter().write("Something went wrong...");
        }
*/