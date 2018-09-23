package by.epam.project.model.dao.impl;

import by.epam.project.model.dao.EntityDao;
import by.epam.project.model.entities.User;
import by.epam.project.model.exception.ProjectException;
import by.epam.project.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class UserDao implements EntityDao<User> {

    private static final Logger LOG = LogManager.getRootLogger();

    private static final String INSERT_USER = "insert into carhire.user values(?, SHA2(?, 224), ?, ?)";
    private static final String DELETE_USER = "DELETE FROM carhire.user WHERE email=?";
    private static final String UPDATE_USER = "UPDATE carhire.user SET email=?, pass=?, id_passport=?, " +
            "status=? WHERE email=?";
    private static final String GET_ALL_USERS = "SELECT * FROM carhire.user";
    private static final String CHECK_IF_CONTAINS = "SELECT email FROM carhire.user " +
            "WHERE email=? and pass=SHA2(?, 224)";
    private static final String CHECK_STATUS = "SELECT * FROM carhire.user WHERE email=?";


    public UserDao(){
        // Initialization
    }


    @Override
    public boolean insert(User entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(INSERT_USER);

            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getPassword());
            statement.setNull(3, Types.INTEGER);
            statement.setInt(4, entity.getStatus()); // in the process of registration

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
    public boolean delete(User entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(DELETE_USER);

            statement.setString(1, entity.getEmail());

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
    public boolean update(User oldEntity, User newEntity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_USER);

            statement.setString(1, newEntity.getEmail());
            statement.setString(2, newEntity.getPassword());
            statement.setString(3, oldEntity.getEmail());

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
    public List<User> getAll() throws ProjectException {

        List<User> users = new ArrayList<User>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_ALL_USERS);

            while (resultSet.next()) {
                String name = resultSet.getString("email");
                users.add(new User(name, ""));
            }

        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return users;
    }


    @Override
    public boolean contains(User entity) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(CHECK_IF_CONTAINS);

            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getPassword());

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false;
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


    public int checkStatusByEmail(String email) throws ProjectException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(CHECK_STATUS);

            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(4);
            }
            else {
                return -1; // user is not registered
            }
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ProjectException(e);
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

    }



}
