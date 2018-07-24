package app.controller.servlets;

import app.model.entities.User;
import app.model.DAO.FactoryDAO;
import app.model.DAO.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;


public class CarListServlet extends HttpServlet {

    /*
     *
     * два метода doPost и doGet,
     * сервлеты взаимодействуют с клиентом посредством запрос-ответ,
     * так вот в основном это GET и POST.
     *
     *
     * request – это запрос со стороны клиента;
     * response – это ответ со стороны сервера.
     *
     * */


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view/car_list.jsp");
        requestDispatcher.forward(request, response);


        User user = new User("nikki1", "password");

        FactoryDAO factoryDAO = new FactoryDAO();
        UserDAO userDAO = factoryDAO.getUserDAO();

        boolean inserted = userDAO.insert(user);
        log(String.valueOf(inserted) + "!!!!!!!!!!!!@@!@!@!@!");
    }


}