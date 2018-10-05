package by.epam.project.dao.impl;

import by.epam.project.dao.EntityDao;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import by.epam.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements EntityDao<Order> {

    private static final Logger LOG = LogManager.getRootLogger();

    private static final String INSERT_ORDER = "insert into carhire.order values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ORDER = "DELETE FROM carhire.order WHERE id=?";
    private static final String UPDATE_ORDER = "UPDATE carhire.order SET user_name=?, car_id=?," +
            " receiving_date=?, return_date=?, rental_price=?, ad_service_price=?," +
            "order_is_paid=?, ad_info=? WHERE id=?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM carhire.order";
    private static final String CHECK_IF_CONTAINS = "SELECT name FROM carhire.order WHERE user_name=?," +
            " car_id=?, receiving_date=?, return_date=?, rental_price=?, ad_service_price=?, order_is_paid=?," +
            " ad_info=?";
    private static final String CHECK_IF_CONTAINS_BY_NAME = "SELECT name FROM carhire.order WHERE user_name=?";


    public OrderDao(){
        // инициализация
    }


    @Override
    public boolean insert(Order entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(INSERT_ORDER);

            statement.setNull(1, Types.INTEGER);
            statement.setString(2, entity.getUserName());
            statement.setInt(3, entity.getCarId());
            statement.setString(4, entity.getReceivingDate());
            statement.setString(5, entity.getReturnDate());
            statement.setBigDecimal(6, entity.getRentalPrice());
            statement.setBigDecimal(7, entity.getAdServicePrice());
            statement.setInt(8, entity.getOrderIsPaid());
            statement.setString(9, entity.getAdInfo());

            int res = statement.executeUpdate();

            if (res > 0) {
                return true; // successfully
            }
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(statement, connection);
        }

        return false;
    }


    @Override
    public boolean delete(Order entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(DELETE_ORDER);

            statement.setInt(1, entity.getId());

            int res = statement.executeUpdate();

            if (res > 0) {
                return true; // successfully
            }
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(statement, connection);
        }

        return false;
    }


    @Override
    public boolean update(Order oldEntity, Order newEntity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_ORDER);

            statement.setString(1, newEntity.getUserName());
            statement.setInt(2, newEntity.getCarId());
            statement.setString(3, newEntity.getReceivingDate());
            statement.setString(4, newEntity.getReturnDate());
            statement.setBigDecimal(5, newEntity.getRentalPrice());
            statement.setBigDecimal(6, newEntity.getAdServicePrice());
            statement.setInt(7, newEntity.getOrderIsPaid());
            statement.setString(8, newEntity.getAdInfo());
            statement.setInt(9, oldEntity.getId());

            int res = statement.executeUpdate();

            if (res > 0) {
                return true; // successfully
            }
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(statement, connection);
        }

        return false;
    }


    @Override
    public List<Order> takeAll() throws ProjectException {

        List<Order> orders = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_ALL_ORDERS);

            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                String user_name = resultSet.getString(2);
                int car_id = resultSet.getInt(3);
                String receiving_date = resultSet.getString(4);
                String return_date = resultSet.getString(5);
                BigDecimal rental_price = resultSet.getBigDecimal(6);
                BigDecimal ad_service_price = resultSet.getBigDecimal(7);
                int order_is_paid = resultSet.getInt(8);
                String ad_info = resultSet.getString(9);

                orders.add(new Order(id, user_name, car_id, receiving_date, return_date, rental_price,
                        ad_service_price, order_is_paid, ad_info));
            }


        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return orders;
    }


    @Override
    public boolean contains(Order entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(CHECK_IF_CONTAINS);

            statement.setString(1, entity.getUserName());

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false; // successfully
            }
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return true;
    }


    public boolean containsByUserName(Order entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(CHECK_IF_CONTAINS_BY_NAME);

            statement.setString(1, entity.getUserName());

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false; // successfully
            }
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return true;
    }


}
