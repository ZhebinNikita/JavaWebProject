package app.controller.servlets;

import app.model.dao.implems.UserDAO;
import app.model.entities.User;
import app.model.pool.ConnectionPool;
import app.model.pool.ConnectionPoolException;
import app.model.validation.UserValidator;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* Set "*.jsp" to this URL address */
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view/start.jsp");
        requestDispatcher.forward(request, response);

    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if (isAjax) {

            String email = request.getParameter("email");
            String pass = request.getParameter("pass");

            UserDAO userDAO = new UserDAO();
            User user = new User(email, pass);

            response.setContentType("text/plain");  // Set content type so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8");

            //////////////////////  Validation  //////////////////////
            if (userDAO.contains(user)) {
                response.getWriter().write("User with this Email is already exist!"); // validation is failed
            }
            else if(!UserValidator.checkUser(email, pass)){
                response.getWriter().write("Email or password is wrong.");
            }
            //////////////////////  Validation  //////////////////////
            else {
                if (userDAO.insert(user)) {
                    response.getWriter().write("User registered!");
                    LOG.info("User registered!");
                }else {
                    response.getWriter().write("Something went wrong...");
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
        }
        catch (ConnectionPoolException e) {
            LOG.error(e);
        }
        finally {
            LOG.info("Connection Pool is initialized!");
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
