package by.epam.project.controller.servlet;

import by.epam.project.constant.PagePathConstant;
import by.epam.project.command.Command;
import by.epam.project.command.CommandMap;
import by.epam.project.command.CommandType;
import by.epam.project.exception.ProjectException;
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

import static by.epam.project.command.CommandType.*;
import static by.epam.project.constant.ClientRole.ROLE;
import static by.epam.project.constant.ClientRole.ROLE_GUEST;
import static by.epam.project.constant.ClientRole.ROLE_USER;
import static by.epam.project.controller.servlet.RouteType.FORWARD;
import static by.epam.project.lang.LangSessionManager.setSessionLanguage;


@WebServlet(name = "Controller", urlPatterns = {"", "/car_list", "/orders", "/profile", "/error_page"}) //!!!
public class Controller extends HttpServlet {

    private static final String ERROR_AJAX_RESPONSE_TEXT = "ERROR";
    private static final Logger LOG = LogManager.getRootLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSessionLanguage(req);
        setSessionRole(req);
        processDispatchRequest(req, resp);
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



    private void processDispatchRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        String pageURI = req.getRequestURI();
        RequestDispatcher requestDispatcher;

        Command command;
        Route route;
        String page;

        // Set JSP file to URL address
        try {
            command = CommandMap.getInstance().get(CommandType.getDispatcherCommand(pageURI));
            command.execute(req, resp);
            route = command.getRoute();
            page = route.getPagePath();
            requestDispatcher = req.getRequestDispatcher(page);

            switch (route.getRouteType()) {
                case FORWARD:
                    requestDispatcher.forward(req, resp);
                    break;
                case REDIRECT:
                    resp.sendRedirect(page);
                    break;
            }

        } catch (Exception e) {
            LOG.error(e);
            resp.sendRedirect(PagePathConstant.PAGE_ERROR);
        }

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


    private void setSessionRole(HttpServletRequest req) {

        HttpSession session = req.getSession(true);

        if (session.getAttribute(ROLE) == null) {
            session.setAttribute(ROLE, ROLE_GUEST);
        }
    }

}

