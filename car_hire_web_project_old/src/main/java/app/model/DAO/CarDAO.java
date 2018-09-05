package app.model.DAO;

import app.model.CarClass;
import app.model.entities.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarDAO implements EntityDAO<Car>{

    private static String INSERT_CAR = "insert into carhire.car values(?, ?, ?, ?, ?)";
    private static String DELETE_CAR = "DELETE FROM carhire.car WHERE name=?";
    private static String UPDATE_CAR = "UPDATE carhire.car SET name=?, daily_rental_price=?, class=?, rented=? WHERE name=?";

    private static String GET_ALL_CARS = "SELECT * FROM carhire.car";


    public CarDAO(){
        // инициализация
    }


    public boolean insert(Car entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement
                    (INSERT_CAR);

            statement.setNull(1, Types.INTEGER);
            statement.setString(2, entity.getName());
            statement.setDouble(3, entity.getDailyRentalPrice());
            statement.setString(4, entity.getCarClass().name());
            statement.setInt(5, entity.getRented());

            int i = statement.executeUpdate();

            if (i > 0) {
                return true; // successfully
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean delete(Car entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.
                    prepareStatement(DELETE_CAR);
            statement.setString(1, entity.getName());

            int result_set = statement.executeUpdate();

            if (result_set > 0) {
                return true; // successfully
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean update(Car oldEntity, Car newEntity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.
                    prepareStatement(UPDATE_CAR);
            statement.setString(1, newEntity.getName());
            statement.setDouble(2, newEntity.getDailyRentalPrice());
            statement.setString(3, newEntity.getCarClass().name());
            statement.setInt(4, newEntity.getRented());
            statement.setString(5, oldEntity.getName());

            int result_set = statement.executeUpdate();

            if (result_set > 0) {
                return true; // successfully
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public Car find(Car entity) {
        return null;
    }


    public List<Car> getAll() {

        List<Car> cars = new ArrayList<>();

        Connection connection = DAO.createConnection();

        try {

            Statement st = connection.createStatement();

            ResultSet result = st.executeQuery(GET_ALL_CARS);

            for (;;) {
                if (result.next()) {
                    String name = result.getString(2);
                    double dailyRentalPrice = result.getDouble(3);
                    CarClass carClass = CarClass.valueOf(result.getString(4));
                    int rented = result.getInt(5);

                    cars.add(new Car(name, dailyRentalPrice, carClass, rented));
                }
                else{
                    break;
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }


    public boolean contains(Car entity) {
        return false;
    }



}
