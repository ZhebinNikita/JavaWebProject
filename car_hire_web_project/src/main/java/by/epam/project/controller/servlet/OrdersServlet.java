package by.epam.project.controller.servlet;

import by.epam.project.dao.impl.OrderDao;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


//@WebServlet(name = "OrdersServlet", urlPatterns = "/orders")
public class OrdersServlet { //extends HttpServlet {
/*
    private final static Logger LOG = LogManager.getRootLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            OrderDao orderDao = new OrderDao();
            List<Order> orders = orderDao.takeAll();
            req.setAttribute("orders", orders);
        } catch (ProjectException e){
            LOG.error(e);
        }

        // Set JSP file to this URL address
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/view/orders.jsp");
        dispatcher.forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (isAjax) {
            ///////////////////////////////////////////////////////////////////////////
        }

    }
*/

}