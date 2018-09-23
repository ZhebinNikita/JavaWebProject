package by.epam.project.controller.servlets;

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
