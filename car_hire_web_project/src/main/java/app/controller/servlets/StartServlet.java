package app.controller.servlets;

import app.model.DAO.UserDAO;
import app.model.entities.User;

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

    /* request – from client side
     * response – from server side
     * */

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

            // Validation
            if (userDAO.contains(user)) {
                response.getWriter().write("Пользователь с данным Email уже существует!"); // validation is failed
            } else {
                if (userDAO.insert(user)) {
                    response.getWriter().write("Регистрация прошла успешно!");
                }else {
                    response.getWriter().write("Что-то пошло не так...");
                }
            }
        }

    }


}
