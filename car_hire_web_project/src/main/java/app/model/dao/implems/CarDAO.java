package app.model.dao.implems;

import app.model.CarClass;
import app.model.dao.EntityDAO;
import app.model.pool.ConnectionPool;
import app.model.entities.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarDAO implements EntityDAO<Car> {

    private final static Logger LOG = LogManager.getRootLogger();

    private static final String INSERT_CAR = "insert into carhire.car values(?, ?, ?, ?, ?)";
    private static final String DELETE_CAR = "DELETE FROM carhire.car WHERE id=?";
    private static final String UPDATE_CAR = "UPDATE carhire.car SET name=?, daily_rental_price=?," +
            " class=?, rented=? WHERE id=?";

    private static final String GET_ALL_CARS = "SELECT * FROM carhire.car";

    //private static final String CHECK_IF_CONTAINS = "SELECT name FROM carhire.order WHERE user_name=?," +
      //      " car_id=?, receiving_date=?, return_date=?, rental_price=?, ad_service_price=?, order_is_paid=?," +
        //    " ad_info=?";


    public CarDAO(){
        // Initialization
    }


    public boolean insert(Car entity) {

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
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            connectionPool.closeConnection(statement, connection);
        }

        return false;
    }


    public boolean delete(Car entity) {

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
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connectionPool.closeConnection(statement, connection);
        }

        return false;
    }


    public boolean update(Car oldEntity, Car newEntity) {

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
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connectionPool.closeConnection(statement, connection);
        }

        return false;
    }


    public Car find(Car entity) {
        return null;
    }


    public List<Car> getAll() {

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
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return cars;
    }


    public boolean contains(Car entity) {
        return false;
    }



}
