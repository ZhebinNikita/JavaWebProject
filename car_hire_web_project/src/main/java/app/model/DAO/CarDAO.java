package app.model.DAO;

import app.model.CarClass;
import app.model.entities.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarDAO implements EntityDAO<Car>{


    public CarDAO(){
        // инициализация
    }


    public boolean insert(Car entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement
                    ("insert into carhire.car values(?, ?, ?)");

            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getDailyRentalPrice());
            statement.setString(3, entity.getCarClass());

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

            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM carhire.car WHERE name=?");
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
                    prepareStatement("UPDATE carhire.car SET name=?, daily_rental_price=?, class=? WHERE name=?");
            statement.setString(1, newEntity.getName());
            statement.setDouble(2, newEntity.getDailyRentalPrice());
            statement.setString(3, newEntity.getCarClass());
            statement.setString(4, oldEntity.getName());

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

        List<Car> cars = new ArrayList<Car>();

        Connection connection = DAO.createConnection();

        try {

            Statement st = connection.createStatement();

            ResultSet result = st.executeQuery("SELECT * FROM carhire.car");

            for (;;) {
                if (result.next()) {
                    String name = result.getString(1);
                    double dailyRentalPrice = result.getDouble(2);
                    String carClass = result.getString(3);

                    cars.add(new Car(name, dailyRentalPrice, carClass));
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
