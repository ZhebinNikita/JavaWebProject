package by.epam.project.command.dispatcher.order;

import by.epam.project.command.Command;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import by.epam.project.service.impl.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class TakeOrderByEmailCommand implements Command {

    private static final String PARAM_EMAIL = "email";
    private OrderService orderService = new OrderService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {

        HttpSession session = req.getSession();
        String email = session.getAttribute(PARAM_EMAIL).toString();

        List<Order> ordersByEmail = orderService.takeOrdersByEmail(email);
        req.setAttribute("orderByEmail", ordersByEmail);
    }

}
