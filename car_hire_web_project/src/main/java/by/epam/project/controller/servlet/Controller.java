package by.epam.project.controller.servlet;

import by.epam.project.constant.PagePathConstant;
import by.epam.project.command.Command;
import by.epam.project.command.CommandMap;
import by.epam.project.command.CommandType;
import by.epam.project.exception.ProjectException;
import by.epam.project.language.LangResourceManager;
import by.epam.project.database.pool.ConnectionPool;
import by.epam.project.database.pool.ConnectionPoolException;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
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
import java.util.Locale;

import static by.epam.project.command.CommandType.*;


@WebServlet(name = "Controller", urlPatterns = {"", "/car_list", "/orders", "/profile", "/error_page"}) //!!!
public class Controller extends HttpServlet {

    private static final String ERROR_AJAX_RESPONSE_TEXT = "ERROR";
    private final static Logger LOG = LogManager.getRootLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setSessionLanguage(req);

        setSessionRole(req);

        try {
            processRequest(req, resp);
        } catch (ProjectException e) {
            LOG.error(e);
            //;;;; redirect to error page
        }

    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        processAjaxRequest(req, resp);

    }


    @Override
    public void init() throws ServletException {
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
        AbandonedConnectionCleanupThread.checkedShutdown();
        ConnectionPool.getInstance().dispose();
        LOG.info("Connection Pool is destroyed!");
    }



    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ProjectException, IOException, ServletException {

        String pageURI = req.getRequestURI();
        RequestDispatcher requestDispatcher;

        Command command;

        // Set JSP file to URL address
        switch (pageURI) {

            case "/":
                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_MAIN);
                break;

            case "/profile":
                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_PROFILE);
                break;

            case "/error_page":
                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_ERROR);
                break;

            case "/car_list":
                command = CommandMap.getInstance().get(TAKE_NOT_RENTED_CARS);
                command.execute(req, resp);

                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_CAR_LIST);
                break;

            case "/orders":
                command = CommandMap.getInstance().get(TAKE_ALL_ORDERS);
                command.execute(req, resp);

                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_ORDERS);
                break;

            default:
                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_ERROR);
                break;
        }

        requestDispatcher.forward(req, resp);

    }


    private void processAjaxRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (isAjax) {

            String action = req.getParameter("action");

            LOG.info("Action - " + CommandType.valueOf(action) + ";");

            Command command = CommandMap.getInstance().get(CommandType.valueOf(action));

            try {
                command.execute(req, resp);
            } catch (ProjectException e) {
                LOG.error("ProcessRequest error.", e);
                resp.getWriter().write(ERROR_AJAX_RESPONSE_TEXT); // Ajax responseText
            }
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




}

