package by.epam.project.command.dispatch;

import by.epam.project.command.Command;
import by.epam.project.command.CommandMap;
import by.epam.project.constant.PagePathConstant;
import by.epam.project.controller.servlet.Route;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import by.epam.project.service.impl.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.epam.project.constant.ClientRole.ROLE;
import static by.epam.project.constant.ClientRole.ROLE_ADMIN;
import static by.epam.project.constant.ClientRole.ROLE_USER;


public class DispatchOrdersPage implements Command {

    private Route route;
    private static final String ATTRIBUTE_PAID_ORDERS = "paidOrders";
    private static final String ATTRIBUTE_NOT_PAID_ORDERS = "notPaidOrders";
    private OrderService orderService = new OrderService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {

        route = new Route();
        HttpSession session = req.getSession(true);


        if (!session.getAttribute(ROLE).equals(ROLE_ADMIN)) {
            route.setPagePath(PagePathConstant.PAGE_ERROR);
            return;
        }


        List<Order> orders;


        if(req.getParameter("orders") != null) {

            switch (req.getParameter("orders")) {
                case "notPaid":
                    orders = orderService.takeNotPaidOrders();
                    req.setAttribute(ATTRIBUTE_NOT_PAID_ORDERS, orders);
                    break;
                case "paid":
                    orders = orderService.takePaidOrders();
                    req.setAttribute(ATTRIBUTE_PAID_ORDERS, orders);
                    break;
                default:
                    orders = orderService.takeNotPaidOrders();
                    req.setAttribute(ATTRIBUTE_NOT_PAID_ORDERS, orders);
                    break;
            }
        }
        else{
            orders = orderService.takeNotPaidOrders();
            req.setAttribute(ATTRIBUTE_NOT_PAID_ORDERS, orders);
        }


        route.setPagePath(PagePathConstant.PAGE_ORDERS);
    }


    @Override
    public Route getRoute() throws ProjectException {
        return route;
    }

}
