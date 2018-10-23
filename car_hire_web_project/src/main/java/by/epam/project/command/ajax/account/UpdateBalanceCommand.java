package by.epam.project.command.ajax.account;

import by.epam.project.command.Command;
import by.epam.project.entity.Account;
import by.epam.project.exception.ProjectException;
import by.epam.project.lang.LangResourceManager;
import by.epam.project.service.impl.AccountService;
import by.epam.project.validation.BalanceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;


public class UpdateBalanceCommand implements Command {

    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_AMOUNT = "amount";
    private static final String PARAM_BALANCE = "balance";
    private static final Logger LOG = LogManager.getRootLogger();
    private AccountService accountService = new AccountService();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        HttpSession session = req.getSession();

        String email = req.getParameter(PARAM_EMAIL);
        BigDecimal moneyAmount = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_AMOUNT)));


        //// Validation ////
        if(!BalanceValidator.check(moneyAmount)){
            resp.getWriter().write(langManager.getString("validation.is.failed"));
            return;
        }
        //// Validation ////


        Account currAcc = accountService.take(email);
        BigDecimal currentBalance = currAcc.getBalance();


        Account account = new Account(email, (currentBalance.add(moneyAmount)));

        if(accountService.updateBalance(account)){
            session.setAttribute(PARAM_BALANCE, account.getBalance());
            resp.getWriter().write(langManager.getString("operation.completed.successfully"));
        }
        else{
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
        }

    }

}
