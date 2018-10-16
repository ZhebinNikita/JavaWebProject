package by.epam.project.command.ajax.account;

import by.epam.project.command.Command;
import by.epam.project.entity.Account;
import by.epam.project.exception.ProjectException;
import by.epam.project.service.impl.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RefreshBalanceCommand implements Command {

    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_BALANCE = "balance";
    private static final Logger LOG = LogManager.getRootLogger();
    private AccountService accountService = new AccountService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        HttpSession session = req.getSession();
        String email = req.getParameter(PARAM_EMAIL);

        Account account = accountService.take(email);

        session.setAttribute(PARAM_BALANCE, account.getBalance());

    }

}
