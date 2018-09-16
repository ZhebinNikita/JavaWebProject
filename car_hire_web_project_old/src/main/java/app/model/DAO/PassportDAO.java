package app.model.DAO;

import app.model.entities.Passport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassportDAO implements EntityDAO<Passport>{

    private static final String INSERT_PASSPORT = "INSERT INTO carhire.passport values(?, ?, ?, ?, ?)";
    private static final String DELETE_PASSPORT = "DELETE FROM carhire.passport WHERE id=?";
    private static final String UPDATE_PASSPORT = "UPDATE carhire.passport SET name=?, surname=?, birthday=?," +
            " identification_number=? WHERE id=?";

    private static final String GET_ALL_PASSPORTS = "SELECT * FROM carhire.passport";


    public PassportDAO(){
        // инициализация
    }


    @Override
    public boolean insert(Passport entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(INSERT_PASSPORT);

            statement.setNull(1, Types.INTEGER);
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSurname());
            statement.setString(4, entity.getBirthday());
            statement.setString(5, entity.getIdentification_number());

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


    @Override
    public boolean delete(Passport entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(DELETE_PASSPORT);
            statement.setInt(1, entity.getId());

            int result = statement.executeUpdate();

            if (result > 0) {
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


    @Override
    public boolean update(Passport oldEntity, Passport newEntity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.
                    prepareStatement(UPDATE_PASSPORT);
            statement.setString(1, newEntity.getName());
            statement.setString(2, newEntity.getSurname());
            statement.setString(3, newEntity.getBirthday());
            statement.setString(4, newEntity.getIdentification_number());
            statement.setInt(5, oldEntity.getId());

            int result = statement.executeUpdate();

            if (result > 0) {
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


    @Override
    public Passport find(Passport entity) {
        return null;
    }


    @Override
    public List<Passport> getAll() {

        List<Passport> passports = new ArrayList<>();

        Connection connection = DAO.createConnection();

        try {

            Statement st = connection.createStatement();

            ResultSet result = st.executeQuery(GET_ALL_PASSPORTS);

            while (result.next()) {
                String name = result.getString(2);
                String surname = result.getString(3);
                String birthday = result.getString(4);
                String identification_number = result.getString(5);

                passports.add(new Passport(name, surname, birthday, identification_number));
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

        return passports;
    }


    @Override
    public boolean contains(Passport entity) {
        return false;
    }


}
