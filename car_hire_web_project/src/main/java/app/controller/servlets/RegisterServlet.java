package app.controller.servlets;

import app.model.entities.User;
import app.model.DAO.FactoryDAO;
import app.model.DAO.UserDAO;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;


public class RegisterServlet extends HttpServlet {

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

    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("userName");
        String pass = request.getParameter("userPass");

        User user = new User(name, pass);

        FactoryDAO factoryDAO = new FactoryDAO();
        UserDAO userDAO = factoryDAO.getUserDAO();

        boolean inserted = userDAO.insert(user);
        log(String.valueOf(inserted));

    }



}  