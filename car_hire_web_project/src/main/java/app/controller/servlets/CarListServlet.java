package app.controller.servlets;

import app.model.DAO.CarDAO;
import app.model.entities.Car;
import app.model.entities.User;
import app.model.DAO.DAO;
import app.model.DAO.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;


@WebServlet(name = "CarListServlet", urlPatterns = "/car_list")
public class CarListServlet extends HttpServlet {

    /* request – from client side
     * response – from server side
     * */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CarDAO carDAO = new CarDAO();
        List<Car> cars = carDAO.getAll();
        request.setAttribute("cars", cars);

        /* Set "*.jsp"  to this URL address */
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/view/car_list.jsp");
        dispatcher.forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if (isAjax) {

            String name = request.getParameter("name");
            double daily_rental_price = Double.valueOf(request.getParameter("daily_rental_price"));
            String car_class = request.getParameter("car_class");

            CarDAO carDAO = new CarDAO();
            Car car = new Car(name, daily_rental_price, car_class);

            response.setContentType("text/plain");  // Set content type so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8");


                if (carDAO.insert(car)) {
                    response.getWriter().write("Автомобиль добавлен!");
                }else {
                    response.getWriter().write("Что-то пошло не так...");
                }

            }

    }
}