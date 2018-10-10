package by.epam.project.command.order;

import by.epam.project.command.Command;
import by.epam.project.dao.impl.OrderDao;
import by.epam.project.exception.ProjectException;
import by.epam.project.model.entity.Order;
import by.epam.project.services.impl.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TakeAllOrdersCommand implements Command {

    private OrderService orderService = new OrderService();

    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed = false;

        List<Order> orders = orderService.takeAllOrders();
        req.setAttribute("orders", orders);

        if (!orders.isEmpty())
            executed = true;

        return executed;
    }

}
