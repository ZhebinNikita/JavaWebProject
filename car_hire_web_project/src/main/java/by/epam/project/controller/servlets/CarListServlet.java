package by.epam.project.controller.servlets;

import by.epam.project.model.CarClass;
import by.epam.project.model.dao.impl.CarDao;
import by.epam.project.model.entities.Car;
import by.epam.project.model.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;


@WebServlet(name = "CarListServlet", urlPatterns = "/car_list")
public class CarListServlet extends HttpServlet {

    private final static Logger LOG = LogManager.getRootLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            CarDao carDao = new CarDao();
            List<Car> notRentedCars = carDao.getNotRentedCars();
            req.setAttribute("notRentedCars", notRentedCars);
        } catch (ProjectException e) {
            LOG.error(e);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/view/car_list.jsp");
        dispatcher.forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (isAjax) {

            CarDao carDao = new CarDao();

            resp.setContentType("text/plain");  // Set content type so that jQuery knows what it can expect.
            resp.setCharacterEncoding("UTF-8");

            String action = req.getParameter("action");

            try {
                if (action.compareTo("delete_car") == 0) {

                    int id = Integer.valueOf(req.getParameter("id"));
                    if (carDao.delete(new Car(id))) {
                        resp.getWriter().write("Car deleted!");
                    }

                } else if (action.compareTo("add_car") == 0) {

                    String name = req.getParameter("name");
                    BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(
                            req.getParameter("daily_rental_price")));
                    CarClass car_class = CarClass.valueOf(req.getParameter("car_class"));
                    int amount = Integer.valueOf(req.getParameter("amount_cars"));

                    Car adding_car = new Car(1, name, daily_rental_price, car_class, 0);

                    for (int i = 0; i < amount; i++) {
                        if (carDao.insert(adding_car)) {
                            if (i == amount - 1) {
                                resp.getWriter().write("Car added!");
                                LOG.info(amount + " car's objects were added.");
                            }
                        } else {
                            LOG.info("Something went wrong with adding car with number = " + (i + 1));
                            resp.getWriter().write("Something went wrong...");
                            break;
                        }
                    }

                } else if (action.compareTo("update_car") == 0) {

                    int id = Integer.valueOf(req.getParameter("id"));
                    String name = req.getParameter("name");
                    BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(
                            req.getParameter("daily_rental_price")));
                    CarClass car_class = CarClass.valueOf(req.getParameter("car_class"));

                    Car updating_car = new Car(id, name, daily_rental_price, car_class, 0);

                    if (carDao.update(new Car(id), updating_car)) {
                        resp.getWriter().write("Car updated!");
                        LOG.info("Car was updated.");
                    } else {
                        LOG.info("Something went wrong with updating car with id = " + id);
                        resp.getWriter().write("Something went wrong...");
                    }

                }
            }
            catch (ProjectException e){
                LOG.error(e);
            }
        }
    }
}