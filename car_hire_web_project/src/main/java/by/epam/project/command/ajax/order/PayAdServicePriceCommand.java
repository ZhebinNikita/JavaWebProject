package by.epam.project.command.ajax.order;

import by.epam.project.command.Command;
import by.epam.project.entity.Account;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import by.epam.project.lang.LangResourceManager;
import by.epam.project.service.impl.AccountService;
import by.epam.project.service.impl.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class PayAdServicePriceCommand implements Command {

    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_EMAIL = "email";
    private OrderService orderService = new OrderService();
    private AccountService accountService = new AccountService();
    private final static Logger LOG = LogManager.getRootLogger();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        int id = Integer.valueOf(req.getParameter(PARAM_ORDER_ID));
        String email = req.getParameter(PARAM_EMAIL);

        // get AdService price of this Order
        Order order = orderService.takeOrder(id);
        BigDecimal adServicePrice = order.getAdServicePrice();

        // get User's balance
        Account account = accountService.take(email);
        BigDecimal balance = account.getBalance();

        // checking
        if(balance.compareTo(adServicePrice) < 0){
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
        }
        else{
            // now we should subtract adServicePrice from the Balance and delete it from Order
            if(accountService.updateBalance(new Account(email, balance.subtract(adServicePrice)))){
                if(orderService.updateAdServicePrice(id, new BigDecimal(0))){
                    resp.getWriter().write(langManager.getString("operation.completed.successfully"));
                }
                else{
                    accountService.updateBalance(new Account(email, balance));
                    resp.getWriter().write(langManager.getString("smth.went.wrong"));
                }
            }
            else{
                resp.getWriter().write(langManager.getString("smth.went.wrong"));
            }
        }


    }

}
