package by.epam.project.command.ajax.order;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.lang.LangResourceManager;
import by.epam.project.service.impl.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteOrderCommand implements Command {

    private static final String PARAM_ORDER_ID = "id";
    private OrderService orderService = new OrderService();
    private final static Logger LOG = LogManager.getRootLogger();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        int id = Integer.valueOf(req.getParameter(PARAM_ORDER_ID));

        if(orderService.deleteByID(id)){
            resp.getWriter().write(langManager.getString("operation.completed.successfully"));
        }
        else{
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
        }

    }

}
