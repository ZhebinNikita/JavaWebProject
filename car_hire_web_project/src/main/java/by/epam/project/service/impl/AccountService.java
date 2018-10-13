package by.epam.project.service.impl;

import by.epam.project.database.dao.impl.AccountDao;
import by.epam.project.entity.Account;
import by.epam.project.exception.ProjectException;
import by.epam.project.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountService implements Service {

    private final static Logger LOG = LogManager.getRootLogger();
    private AccountDao accountDao = new AccountDao();


    public boolean add(Account account) throws ProjectException {
        boolean accountAdded;
        accountAdded = accountDao.insert(account);
        return accountAdded;
    }


    public boolean updateBalance(Account account) throws ProjectException {
        boolean balanceUpdated;
        balanceUpdated = accountDao.updateBalance(account);
        return balanceUpdated;
    }


    public Account take(String email) throws ProjectException {
        return accountDao.take(email);
    }

}
