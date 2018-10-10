package by.epam.project.dao.impl;

import by.epam.project.model.CarClass;
import by.epam.project.dao.EntityDao;
import by.epam.project.exception.ProjectException;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.model.entity.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarDao implements EntityDao<Car> {

    private static final Logger LOG = LogManager.getRootLogger();

    private static final String INSERT_CAR = "insert into carhire.car values(?, ?, ?, ?, ?)";
    private static final String DELETE_CAR = "DELETE FROM carhire.car WHERE id=?";
    private static final String UPDATE_CAR = "UPDATE carhire.car SET name=?, daily_rental_price=?," +
            " class=?, rented=? WHERE id=?";
    private static final String GET_ALL_CARS = "SELECT * FROM carhire.car";
    private static final String GET_NOT_RENTED_CARS = "SELECT * FROM carhire.car WHERE rented=0";
    private static final String GET_RENTED_CARS = "SELECT * FROM carhire.car WHERE rented=1";

    //private static final String CHECK_IF_CONTAINS = "SELECT name FROM carhire.order WHERE user_name=?," +
      //      " car_id=?, receiving_date=?, return_date=?, rental_price=?, ad_service_price=?, order_is_paid=?," +
        //    " ad_info=?";


    public CarDao(){
        // Initialization
    }


    @Override
    public boolean insert(Car entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(INSERT_CAR);

            statement.setNull(1, Types.INTEGER);
            statement.setString(2, entity.getName());
            statement.setBigDecimal(3, entity.getDailyRentalPrice());
            statement.setString(4, entity.getCarClass().name());
            statement.setInt(5, entity.getRented());

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
    public boolean delete(Car entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(DELETE_CAR);

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
    public boolean update(Car oldEntity, Car newEntity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_CAR);

            statement.setString(1, newEntity.getName());
            statement.setBigDecimal(2, newEntity.getDailyRentalPrice());
            statement.setString(3, newEntity.getCarClass().name());
            statement.setInt(4, newEntity.getRented());

            statement.setInt(5, oldEntity.getId());

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
    public List<Car> takeAll() throws ProjectException {

        List<Car> cars = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_ALL_CARS);

            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                BigDecimal dailyRentalPrice = resultSet.getBigDecimal(3);
                CarClass carClass = CarClass.valueOf(resultSet.getString(4));
                int rented = resultSet.getInt(5);

                cars.add(new Car(id, name, dailyRentalPrice, carClass, rented));
            }

        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return cars;
    }


    @Override
    public boolean contains(Car entity) throws ProjectException {
        return false;
    }


    public List<Car> takeNotRentedCars() throws ProjectException {

        List<Car> notRentedCars = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_NOT_RENTED_CARS);

            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                BigDecimal dailyRentalPrice = resultSet.getBigDecimal(3);
                CarClass carClass = CarClass.valueOf(resultSet.getString(4));
                int rented = resultSet.getInt(5);

                notRentedCars.add(new Car(id, name, dailyRentalPrice, carClass, rented));
            }

        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return notRentedCars;
    }


    public List<Car> takeRentedCars() throws ProjectException {

        List<Car> notRentedCars = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_RENTED_CARS);

            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                BigDecimal dailyRentalPrice = resultSet.getBigDecimal(3);
                CarClass carClass = CarClass.valueOf(resultSet.getString(4));
                int rented = resultSet.getInt(5);

                notRentedCars.add(new Car(id, name, dailyRentalPrice, carClass, rented));
            }

        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return notRentedCars;
    }


}
