package by.epam.project.command.dispatcher.order;

import by.epam.project.command.Command;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import by.epam.project.service.impl.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakePaidOrdersCommand implements Command {

    private OrderService orderService = new OrderService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {

        List<Order> paidOrders = orderService.takePaidOrders();
        req.setAttribute("paidOrders", paidOrders);
    }

}
