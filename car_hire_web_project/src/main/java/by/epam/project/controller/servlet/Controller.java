package by.epam.project.controller.servlet;

import by.epam.project.PageConstant;
import by.epam.project.command.Command;
import by.epam.project.command.CommandMap;
import by.epam.project.command.CommandType;
import by.epam.project.command.user.LoginCommand;
import by.epam.project.dao.impl.CarDao;
import by.epam.project.dao.impl.OrderDao;
import by.epam.project.model.entity.Car;
import by.epam.project.model.entity.Order;
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

import static by.epam.project.command.CommandType.*;


@WebServlet(name = "Controller", urlPatterns = {"", "/car_list", "/orders", "/profile", "/somewrongpage"}) //!!!
public class Controller extends HttpServlet {

    private final static Logger LOG = LogManager.getRootLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        setSessionLanguage(req);

        setSessionRole(req);

        try {
            forwardPage(req, resp);
        } catch (ProjectException e) {
            LOG.error(e);
        }

    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        LangResourceManager langManager = LangResourceManager.INSTANCE;

        if(isAjax) {
            String action = req.getParameter("action");

            try {

                if (action.equals("set_lang_js_message")) {
                    resp.getWriter().write(langManager.getString(req.getParameter("lang_key")));
                }
                else{
                    processAction(action, req, resp);
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


    private void forwardPage(HttpServletRequest req, HttpServletResponse resp)
            throws ProjectException, IOException, ServletException {

        String requestURI = req.getRequestURI();
        RequestDispatcher requestDispatcher;

        Command command;

        // Set JSP file to URL address
        switch (requestURI) {
            case "/":
                requestDispatcher = req.getRequestDispatcher(PageConstant.PAGE_MAIN);
                requestDispatcher.forward(req, resp);
                break;
            case "/car_list":
                command = CommandMap.getInstance().get(TAKE_NOT_RENTED_CARS);
                command.execute(req);

                requestDispatcher = req.getRequestDispatcher(PageConstant.PAGE_CAR_LIST);
                requestDispatcher.forward(req, resp);
                break;
            case "/orders":
                command = CommandMap.getInstance().get(TAKE_ALL_ORDERS);
                command.execute(req);

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

    }


    private void setSessionLanguage(HttpServletRequest req) {

        HttpSession session = req.getSession(true);

        LangResourceManager langManager = LangResourceManager.INSTANCE;

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
            } else if (session.getAttribute("language").equals("en")) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            } else if (session.getAttribute("language").equals("ru_RU")) {
                Locale.setDefault(new Locale("ru", "RU"));
                langManager.changeResource(new Locale("ru", "RU"));
                session.setAttribute("language", "ru_RU");
            } else {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            }
        }

    }


    private void setSessionRole(HttpServletRequest req) {

        HttpSession session = req.getSession(true);

        if (session.getAttribute("role") == null) {
            session.setAttribute("role", "user");
        }

    }


    private void processAction(String action, HttpServletRequest req, HttpServletResponse resp)
            throws ProjectException, IOException {

        LangResourceManager langManager = LangResourceManager.INSTANCE;

        Command command;

        LOG.info("Action - " + CommandType.valueOf(action) + ";");

        switch (CommandType.valueOf(action)){

            case LOGIN:
                command = CommandMap.getInstance().get(LOGIN);
                command.execute(req);
                String message = ((LoginCommand) command).getMessage();
                resp.getWriter().write(message);
                break;

            case LOGOUT:
                command = CommandMap.getInstance().get(LOGOUT);
                if(!command.execute(req)) {
                    resp.getWriter().write(langManager.getString("smth.went.wrong"));
                }
                break;

            case UPDATE_CAR:
                command = CommandMap.getInstance().get(UPDATE_CAR);
                if (command.execute(req)) {
                    resp.getWriter().write(langManager.getString("data.updated"));
                } else {
                    LOG.info("Something went wrong with updating car");
                    resp.getWriter().write(langManager.getString("smth.went.wrong"));
                }
                break;

            case ADD_CAR:
                command = CommandMap.getInstance().get(ADD_CAR);
                if (command.execute(req)) {
                    resp.getWriter().write("Data added!");
                }
                else{
                    resp.getWriter().write(langManager.getString("smth.went.wrong"));
                }
                break;

            case DELETE_CAR:
                command = CommandMap.getInstance().get(DELETE_CAR);
                if (command.execute(req)) {
                    resp.getWriter().write(langManager.getString("data.deleted"));
                } else {
                    resp.getWriter().write(langManager.getString("smth.went.wrong"));
                }
                break;

        }

    }


}

