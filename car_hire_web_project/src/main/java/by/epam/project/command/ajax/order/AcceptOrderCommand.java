package by.epam.project.command.ajax.order;

import by.epam.project.command.Command;
import by.epam.project.entity.Account;
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

public class AcceptOrderCommand implements Command {

    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_ORDER_RENTAL_PRICE = "rentalPrice";
    private static final String PARAM_AD_SERVICE_PRICE = "adServicePrice";
    private static final String PARAM_EMAIL = "email";
    private OrderService orderService = new OrderService();
    private AccountService accountService = new AccountService();
    private final static Logger LOG = LogManager.getRootLogger();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        // take user's money for the order and change order's status to - is paid
        int orderId = Integer.valueOf(req.getParameter(PARAM_ORDER_ID));
        String userEmail = req.getParameter(PARAM_EMAIL);
        BigDecimal orderRentalPrice = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_ORDER_RENTAL_PRICE)));
        BigDecimal adServicePrice = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_AD_SERVICE_PRICE)));

        BigDecimal cost = orderRentalPrice.add(adServicePrice);

        BigDecimal userBalance = accountService.take(userEmail).getBalance();

        if(userBalance.compareTo(cost) < 0){
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
        }
        else{

            Account account = new Account(userEmail, userBalance.subtract(cost));
            if(accountService.updateBalance(account)){
                if(orderService.updateOrderIsPaid(orderId)){
                    resp.getWriter().write(langManager.getString("operation.completed.successfully"));
                }
                else{
                    resp.getWriter().write(langManager.getString("smth.went.wrong"));
                }
            }
            else{
                resp.getWriter().write(langManager.getString("smth.went.wrong"));
            }

        }

    }

}
