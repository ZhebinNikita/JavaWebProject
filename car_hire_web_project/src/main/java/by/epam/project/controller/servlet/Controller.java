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
import static by.epam.project.constant.ClientRole.ROLE_USER;
import static by.epam.project.lang.LangSessionManager.setSessionLanguage;


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

                command = CommandMap.getInstance().get(TAKE_ORDER_BY_EMAIL);
                command.execute(req, resp);

                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_PROFILE);
                break;

            case "/error_page":
                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_ERROR);
                break;

            case "/car_list":

                if(req.getParameter("cars") != null) {
                    if (req.getParameter("cars").equals("rented")) {
                        command = CommandMap.getInstance().get(TAKE_RENTED_CARS);
                    }
                    else if(req.getParameter("cars").equals("notRented")){
                        command = CommandMap.getInstance().get(TAKE_NOT_RENTED_CARS);
                    }
                    else{
                        command = CommandMap.getInstance().get(TAKE_NOT_RENTED_CARS);
                    }
                }
                else{
                    command = CommandMap.getInstance().get(TAKE_NOT_RENTED_CARS);
                }

                command.execute(req, resp);

                requestDispatcher = req.getRequestDispatcher(PagePathConstant.PAGE_CAR_LIST);
                break;

            case "/orders":

                if(req.getParameter("orders") != null) {
                    if (req.getParameter("orders").equals("notPaid")) {
                        command = CommandMap.getInstance().get(TAKE_NOT_PAID_ORDERS);
                    }
                    else if(req.getParameter("orders").equals("paid")){
                        command = CommandMap.getInstance().get(TAKE_PAID_ORDERS);
                    }
                    else{
                        command = CommandMap.getInstance().get(TAKE_NOT_PAID_ORDERS);
                    }
                }
                else{
                    command = CommandMap.getInstance().get(TAKE_NOT_PAID_ORDERS);
                }

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





    private void setSessionRole(HttpServletRequest req) {

        HttpSession session = req.getSession(true);

        if (session.getAttribute(ROLE) == null) {
            session.setAttribute(ROLE, ROLE_USER);
        }

    }




}

