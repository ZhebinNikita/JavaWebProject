package by.epam.project.controller.servlet;

import by.epam.project.command.user.LoginCommand;
import by.epam.project.command.user.RegisterUserCommand;
import by.epam.project.dao.impl.CarDao;
import by.epam.project.dao.impl.OrderDao;
import by.epam.project.entity.Car;
import by.epam.project.entity.Order;
import by.epam.project.entity.User;
import by.epam.project.exception.ProjectException;
import by.epam.project.language.LangResourceManager;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


@WebServlet(name = "Controller", urlPatterns = {"", "/car_list", "/orders"})
public class Controller extends HttpServlet {

    private final static Logger LOG = LogManager.getRootLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOG.info("DO GET WAS CALLED!!!! " + req.getRequestURI() + " !");

        String requestURI = req.getRequestURI();

        HttpSession session = req.getSession(true);
        if (session.getAttribute("role") == null) {
            //session.setAttribute("role", "user");
        }
        else if(session.getAttribute("role").equals("user")){
            /////////////
        }
        else if(session.getAttribute("role").equals("admin")){
            /////////////
        }

        RequestDispatcher requestDispatcher;

        // Set JSP file to URL address
        switch (requestURI){
            case "/":
                requestDispatcher = req.getRequestDispatcher("/view/start.jsp");
                requestDispatcher.forward(req, resp);
                break;
            case "/car_list":
                try {
                    CarDao carDao = new CarDao();
                    List<Car> notRentedCars = carDao.getNotRentedCars();
                    req.setAttribute("notRentedCars", notRentedCars);
                } catch (ProjectException e) {
                    LOG.error(e);
                }
                requestDispatcher = req.getRequestDispatcher("/view/car_list.jsp");
                requestDispatcher.forward(req, resp);
                break;
            case "/orders":
                try {
                    OrderDao orderDao = new OrderDao();
                    List<Order> orders = orderDao.takeAll();
                    req.setAttribute("orders", orders);
                } catch (ProjectException e){
                    LOG.error(e);
                }
                requestDispatcher = req.getRequestDispatcher("/view/orders.jsp");
                requestDispatcher.forward(req, resp);
                break;
                default:
                    requestDispatcher = req.getRequestDispatcher("/view/error_page.jsp");
                    requestDispatcher.forward(req, resp);
                    break;
        }

    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        LangResourceManager langManager = LangResourceManager.INSTANCE;

        LOG.info("DO POST WAS CALLED!!!!");

        if(isAjax) {

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
                        resp.getWriter().write(langManager.getString("user.logged.in"));
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
                    resp.getWriter().write(langManager.getString("smth.went.wrong"));
                }

            }
        }

    }


    @Override
    public void init() throws ServletException {
        super.init();

        Locale.setDefault( Locale.ENGLISH );// new Locale("ru", "RU"));

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