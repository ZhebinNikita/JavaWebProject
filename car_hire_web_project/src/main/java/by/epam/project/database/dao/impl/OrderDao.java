package by.epam.project.database.dao.impl;

import by.epam.project.database.dao.EntityDao;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import by.epam.project.database.pool.ConnectionPool;
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
    private static final String UPDATE_AD_INFO = "UPDATE carhire.order SET ad_info=? WHERE id=?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM carhire.order";
    private static final String GET_ORDERS_BY_EMAIL = "SELECT * FROM carhire.order WHERE user_name=?";
    private static final String GET_ORDER_BY_ID = "SELECT * FROM carhire.order WHERE id=?";
    private static final String GET_PAID_ORDERS = "SELECT * FROM carhire.order WHERE order_is_paid=1";
    private static final String GET_NOT_PAID_ORDERS = "SELECT * FROM carhire.order WHERE order_is_paid=0";
    private static final String CHECK_IF_CONTAINS = "SELECT name FROM carhire.order WHERE user_name=?," +
            " car_id=?, receiving_date=?, return_date=?, rental_price=?, ad_service_price=?, order_is_paid=?," +
            " ad_info=?";
    private static final String CHECK_IF_CONTAINS_BY_NAME = "SELECT user_name FROM carhire.order WHERE user_name=?";
    private static final String UPDATE_ORDER_IS_PAID = "UPDATE carhire.order SET order_is_paid=1 WHERE id=?";
    private static final String UPDATE_ORDER_IS_NOT_PAID = "UPDATE carhire.order SET order_is_paid=0 WHERE id=?";
    private static final String UPDATE_AD_SERVICE_PRICE = "UPDATE carhire.order SET ad_service_price=? WHERE id=?";


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


    public boolean contains(String email) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(CHECK_IF_CONTAINS_BY_NAME);

            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false; // successfully
            }
        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return true;
    }



    public boolean updateOrderIsPaid(int id) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_ORDER_IS_PAID);

            statement.setInt(1, id);

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


    public boolean updateOrderIsNotPaid(int id) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_ORDER_IS_NOT_PAID);

            statement.setInt(1, id);

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


    public List<Order> takePaidOrders() throws ProjectException {

        List<Order> paidOrders = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_PAID_ORDERS);

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

                paidOrders.add(new Order(id, user_name, car_id, receiving_date, return_date, rental_price,
                        ad_service_price, order_is_paid, ad_info));
            }


        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return paidOrders;
    }


    public List<Order> takeNotPaidOrders() throws ProjectException {

        List<Order> notPaidOrders = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_NOT_PAID_ORDERS);

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

                notPaidOrders.add(new Order(id, user_name, car_id, receiving_date, return_date, rental_price,
                        ad_service_price, order_is_paid, ad_info));
            }


        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return notPaidOrders;
    }


    public List<Order> takeOrdersByEmail(String email) throws ProjectException {

        List<Order> orders = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(GET_ORDERS_BY_EMAIL);

            statement.setString(1, email);

            resultSet = statement.executeQuery();

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
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return orders;
    }


    public Order takeOrderById(int id) throws ProjectException {

        Order order;

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(GET_ORDER_BY_ID);

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String user_name = resultSet.getString(2);
                int car_id = resultSet.getInt(3);
                String receiving_date = resultSet.getString(4);
                String return_date = resultSet.getString(5);
                BigDecimal rental_price = resultSet.getBigDecimal(6);
                BigDecimal ad_service_price = resultSet.getBigDecimal(7);
                int order_is_paid = resultSet.getInt(8);
                String ad_info = resultSet.getString(9);

                order = new Order(id, user_name, car_id, receiving_date, return_date, rental_price,
                        ad_service_price, order_is_paid, ad_info);
            }
            else{
                return null;
            }


        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return order;
    }


    public boolean updateAdInfo(int id, String adInfo) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_AD_INFO);

            statement.setString(1, adInfo);
            statement.setInt(2, id);

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


    public boolean updateAdServicePrice(int id, BigDecimal adServicePrice) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_AD_SERVICE_PRICE);

            statement.setBigDecimal(1, adServicePrice);
            statement.setInt(2, id);

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

}
