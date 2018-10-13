package by.epam.project.command.dispatcher;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.entity.Order;
import by.epam.project.service.impl.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AllOrdersDispatcherCommand implements Command {

    private OrderService orderService = new OrderService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {

        List<Order> orders = orderService.takeAllOrders();
        req.setAttribute("orders", orders);
    }

}