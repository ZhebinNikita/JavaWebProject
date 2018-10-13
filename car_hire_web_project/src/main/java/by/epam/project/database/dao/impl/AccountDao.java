package by.epam.project.database.dao.impl;

import by.epam.project.database.dao.EntityDao;
import by.epam.project.database.pool.ConnectionPool;
import by.epam.project.entity.Account;
import by.epam.project.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class AccountDao implements EntityDao<Account> {

    private static final Logger LOG = LogManager.getRootLogger();

    private static final String INSERT_ACCOUNT = "insert into carhire.account values(?, ?)";
    private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE carhire.account SET balance=? WHERE account.email=?";
    private static final String GET_ACCOUNT_BY_EMAIL = "SELECT * FROM carhire.account WHERE account.email=?";


    public AccountDao(){
        // Initialization
    }


    @Override
    public boolean insert(Account entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(INSERT_ACCOUNT);

            statement.setString(1, entity.getUserEmail());
            statement.setBigDecimal(2, entity.getBalance());

            int res = statement.executeUpdate();

            if (res > 0) {
                return true; // successfully
            }
        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(statement, connection);
        }

        return false;
    }


    @Override
    public boolean delete(Account entity) throws ProjectException {
        return false;
    }


    @Override
    public boolean update(Account oldEntity, Account newEntity) throws ProjectException {
        return false;
    }


    @Override
    public List<Account> takeAll() throws ProjectException {
        return null;
    }


    @Override
    public boolean contains(Account entity) throws ProjectException {
        return false;
    }


    public boolean updateBalance(Account entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE);

            statement.setBigDecimal(1, entity.getBalance());
            statement.setString(2, entity.getUserEmail());

            int res = statement.executeUpdate();

            if (res > 0) {
                return true; // successfully
            }
        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(statement, connection);
        }

        return false;
    }


    public Account take(String email) throws ProjectException {

        Account account = new Account(email, new BigDecimal(-1));

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(GET_ACCOUNT_BY_EMAIL);

            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BigDecimal dailyRentalPrice = resultSet.getBigDecimal("balance");
                account = new Account(email, dailyRentalPrice);
            }

            if(account.getBalance().equals(new BigDecimal(-1))){
                LOG.error("Account ("+email+") not found.");
                throw new SQLException();
            }

        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return account;
    }

}
