package by.epam.project.database.dao.impl;

import by.epam.project.database.dao.EntityDao;
import by.epam.project.entity.Passport;
import by.epam.project.exception.ProjectException;
import by.epam.project.database.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassportDao implements EntityDao<Passport> {

    private static final Logger LOG = LogManager.getRootLogger();

    private static final String INSERT_PASSPORT = "INSERT INTO carhire.passport values(?, ?, ?, ?, ?)";
    private static final String DELETE_PASSPORT = "DELETE FROM carhire.passport WHERE id=?";
    private static final String UPDATE_PASSPORT = "UPDATE carhire.passport SET name=?, surname=?, birthday=?," +
            " identification_number=? WHERE id=?";
    private static final String GET_ALL_PASSPORTS = "SELECT * FROM carhire.passport";
    private static final String GET_PASSPORT_BY_ID_NUMBER =
            "SELECT * FROM carhire.passport WHERE identification_number=?";
    private static final String GET_PASSPORT_BY_ID =
            "SELECT * FROM carhire.passport WHERE id=?";
    private static final String CHECK_IF_CONTAINS = "SELECT name FROM carhire.passport " +
            "WHERE name=? and surname=? and birthday=? and identification_number=?";


    public PassportDao(){
        // инициализация
    }


    @Override
    public boolean insert(Passport entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(INSERT_PASSPORT);

            statement.setNull(1, Types.INTEGER);
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSurname());
            statement.setString(4, entity.getBirthday());
            statement.setString(5, entity.getIdentification_number());

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
    public boolean delete(Passport entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(DELETE_PASSPORT);

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
    public boolean update(Passport oldEntity, Passport newEntity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_PASSPORT);

            statement.setString(1, newEntity.getName());
            statement.setString(2, newEntity.getSurname());
            statement.setString(3, newEntity.getBirthday());
            statement.setString(4, newEntity.getIdentification_number());
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
    public List<Passport> takeAll() throws ProjectException {

        List<Passport> passports = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_ALL_PASSPORTS);

            while (resultSet.next()) {

                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String birthday = resultSet.getString(4);
                String identification_number = resultSet.getString(5);

                passports.add(new Passport(name, surname, birthday, identification_number));
            }

        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return passports;
    }


    @Override
    public boolean contains(Passport entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(CHECK_IF_CONTAINS);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getBirthday());
            statement.setString(4, entity.getIdentification_number());

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false;
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


    public Passport takePassport(String identificationNumber) throws ProjectException {

        Passport passport;

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(GET_PASSPORT_BY_ID_NUMBER);

            statement.setString(1, identificationNumber);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String birthday = resultSet.getString(4);

                passport = new Passport(name, surname, birthday, identificationNumber);
                passport.setId(id);
            }
            else{
               throw new SQLException();
            }

        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return passport;
    }


    public Passport takePassport(int id) throws ProjectException {

        Passport passport;

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(GET_PASSPORT_BY_ID);

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String birthday = resultSet.getString(4);
                String identificationNumber = resultSet.getString(5);

                passport = new Passport(name, surname, birthday, identificationNumber);
                passport.setId(id);
            }
            else{
                throw new SQLException();
            }

        }
        catch (SQLException e) {
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return passport;
    }


}
