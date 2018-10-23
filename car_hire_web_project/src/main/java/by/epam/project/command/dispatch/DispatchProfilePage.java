package by.epam.project.command.dispatch;

import by.epam.project.command.Command;
import by.epam.project.constant.PagePathConstant;
import by.epam.project.controller.servlet.Route;
import by.epam.project.entity.Car;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import by.epam.project.service.impl.CarService;
import by.epam.project.service.impl.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.epam.project.constant.ClientRole.ROLE;
import static by.epam.project.constant.ClientRole.ROLE_ADMIN;
import static by.epam.project.constant.ClientRole.ROLE_GUEST;


public class DispatchProfilePage implements Command {

    private Route route;
    private static final String PARAM_EMAIL = "email";
    private static final String ATTRIBUTE_ORDER_BY_EMAIL = "orderByEmail";
    private static final String ATTRIBUTE_RENTED_CARS_BY_EMAIL = "rentedCarsByEmail";
    private OrderService orderService = new OrderService();
    private CarService carService = new CarService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        HttpSession session = req.getSession();
        String email = session.getAttribute(PARAM_EMAIL).toString();


        if (session.getAttribute(ROLE).equals(ROLE_GUEST)) {
            route.setPagePath(PagePathConstant.PAGE_ERROR);
            return;
        }


        List<Order> ordersByEmail = orderService.takeOrdersByEmail(email);
        req.setAttribute(ATTRIBUTE_ORDER_BY_EMAIL, ordersByEmail);


        /*List<Car> cars = new ArrayList<>();
        for (Order order: ordersByEmail) {
            cars.add(carService.takeCar(order.getCarId()));
        }
        req.setAttribute(ATTRIBUTE_RENTED_CARS_BY_EMAIL, cars);*/


        route = new Route();
        route.setPagePath(PagePathConstant.PAGE_PROFILE);
    }


    @Override
    public Route getRoute() throws ProjectException {
        return route;
    }

}
