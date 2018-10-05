package by.epam.project.controller.servlet;

import by.epam.project.PageConstant;
import by.epam.project.command.Command;
import by.epam.project.command.CommandMap;
import by.epam.project.command.CommandType;
import by.epam.project.command.user.LoginCommand;
import by.epam.project.command.user.RegisterUserCommand;
import by.epam.project.dao.impl.CarDao;
import by.epam.project.dao.impl.OrderDao;
import by.epam.project.model.entity.Car;
import by.epam.project.model.entity.Order;
import by.epam.project.model.entity.User;
import by.epam.project.exception.ProjectException;
import by.epam.project.language.LangResourceManager;
import by.epam.project.model.CarClass;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;


@WebServlet(name = "Controller", urlPatterns = {"", "/car_list", "/orders", "/profile", "/somewrongpage"}) //!!!
public class Controller extends HttpServlet {

    private final static Logger LOG = LogManager.getRootLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        String requestURI = req.getRequestURI();
        LangResourceManager langManager = LangResourceManager.INSTANCE;

        LOG.info("req.getParameter(\"language\") = " + req.getParameter("language"));

        // Set Language
        if (req.getParameter("language") != null) {
            if (req.getParameter("language").equals("en")) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            } else if (req.getParameter("language").equals("ru_RU")) {
                Locale.setDefault(new Locale("ru", "RU"));
                langManager.changeResource(new Locale("ru", "RU"));
                session.setAttribute("language", "ru_RU");
            } else {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            }
        } else {

            if (session.getAttribute("language") == null) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
                LOG.info("SESSION attribute lang = NULLLLLLLLLLLLLLLLLL, so its lang = en");
            } else if (session.getAttribute("language").equals("en")) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
                LOG.info("session = en");
            } else if (session.getAttribute("language").equals("ru_RU")) {
                Locale.setDefault(new Locale("ru", "RU"));
                langManager.changeResource(new Locale("ru", "RU"));
                session.setAttribute("language", "ru_RU");
                LOG.info("session = ru_RU");
            } else {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
                LOG.info("session = en");
            }
        }


        if (session.getAttribute("role") == null) {
            //session.setAttribute("role", "user");
        } else if (session.getAttribute("role").equals("user")) {
            /////////////
        } else if (session.getAttribute("role").equals("admin")) {
            /////////////
        }





        RequestDispatcher requestDispatcher;

        // Set JSP file to URL address
        switch (requestURI) {
            case "/":
                requestDispatcher = req.getRequestDispatcher(PageConstant.PAGE_MAIN);
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
                requestDispatcher = req.getRequestDispatcher(PageConstant.PAGE_CAR_LIST);
                requestDispatcher.forward(req, resp);
                break;
            case "/orders":
                try {
                    OrderDao orderDao = new OrderDao();
                    List<Order> orders = orderDao.takeAll();
                    req.setAttribute("orders", orders);
                } catch (ProjectException e) {
                    LOG.error(e);
                }
                requestDispatcher = req.getRequestDispatcher(PageConstant.PAGE_ORDERS);
                requestDispatcher.forward(req, resp);
                break;
            case "/profile":
                requestDispatcher = req.getRequestDispatcher(PageConstant.PAGE_PROFILE);
                requestDispatcher.forward(req, resp);
                break;
            default:
                requestDispatcher = req.getRequestDispatcher(PageConstant.PAGE_ERROR);
                requestDispatcher.forward(req, resp);
                break;
        }


        LOG.info("doGET!!! URI: "
                + req.getRequestURI() + " Locale: "
                + Locale.getDefault() + " SessionLang: "
                + session.getAttribute("language") + " ReqLang: "
                + req.getParameter("language"));


    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        LangResourceManager langManager = LangResourceManager.INSTANCE;

        // Set content type so that jQuery knows what it can expect.
        //resp.setContentType("text/plain");
        //resp.setCharacterEncoding("UTF-8");

        if(isAjax) {

            String action = req.getParameter("action");

            try {

                if (action.equals("set_lang_js_message")) {

                    resp.getWriter().write(langManager.getString(req.getParameter("lang_key")));

                }
                else if (action.equals("add_user")) {

                    String email = req.getParameter("email");

                    LoginCommand loginCommand =
                            (LoginCommand) CommandMap.getInstance().get(CommandType.LOGIN);
                    RegisterUserCommand registerUserCommand =
                            (RegisterUserCommand) CommandMap.getInstance().get(CommandType.REGISTER_USER);

                    // User with these data exist and logged in
                    if (loginCommand.execute(req)) {

                        // Associate the session with the user
                        session.setAttribute("email", email);

                        resp.getWriter().write(langManager.getString("user.logged.in"));
                        LOG.info("User logged in.");

                    } else if (registerUserCommand.execute(req)) {

                        String message = registerUserCommand.getMessage();

                        // Associate the session with the user
                        session.setAttribute("email", email);
                        resp.getWriter().write(message);
                        LOG.info(message);

                    }

                } else if (action.equals("delete_car")) {

                    CarDao carDao = new CarDao();
                    int id = Integer.valueOf(req.getParameter("id"));
                    if (carDao.delete(new Car(id))) {
                        resp.getWriter().write("Car deleted!");
                    }

                } else if (action.equals("add_car")) {

                    CarDao carDao = new CarDao();
                    String name = req.getParameter("name");
                    BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(
                            req.getParameter("daily_rental_price")));
                    CarClass car_class = CarClass.valueOf(req.getParameter("car_class"));
                    int amount = Integer.valueOf(req.getParameter("amount_cars"));

                    Car adding_car = new Car(1, name, daily_rental_price, car_class, 0);

                    for (int i = 0; i < amount; i++) {
                        if (carDao.insert(adding_car)) {
                            if (i == amount - 1) {
                                resp.getWriter().write("Car added!");
                                LOG.info(amount + " car's objects were added.");
                            }
                        } else {
                            LOG.info("Something went wrong with adding car with number = " + (i + 1));
                            resp.getWriter().write(langManager.getString("smth.went.wrong"));
                            break;
                        }
                    }

                } else if (action.equals("update_car")) {

                    CarDao carDao = new CarDao();
                    int id = Integer.valueOf(req.getParameter("id"));
                    String name = req.getParameter("name");
                    BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(
                            req.getParameter("daily_rental_price")));
                    CarClass car_class = CarClass.valueOf(req.getParameter("car_class"));

                    Car updating_car = new Car(id, name, daily_rental_price, car_class, 0);

                    if (carDao.update(new Car(id), updating_car)) {
                        resp.getWriter().write("Car updated!");
                        LOG.info("Car ID(" + id + ") was updated.");
                    } else {
                        LOG.info("Something went wrong with updating car ID(" + id + ")");
                        resp.getWriter().write(langManager.getString("smth.went.wrong"));
                    }
                }

            }
            catch (ProjectException e){
                LOG.error(e);
                resp.getWriter().write(langManager.getString("smth.went.wrong"));
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