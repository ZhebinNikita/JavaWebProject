package app.model.dao.implems;

import app.model.dao.EntityDAO;
import app.model.entities.User;
import app.model.pool.ConnectionPool;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class UserDAO implements EntityDAO<User> {

    private static final String INSERT_USER = "insert into carhire.user values(?, SHA2(?, 224), ?)";
    private static final String DELETE_USER = "DELETE FROM carhire.user WHERE name=?";
    private static final String UPDATE_USER = "UPDATE carhire.user SET name=?, pass=? WHERE name=?";

    private static final String GET_ALL_USERS = "SELECT * FROM carhire.user";
    private static final String DELETE_ALL_USERS = "DELETE FROM carhire.user";

    private static final String CHECK_IF_CONTAINS = "SELECT name FROM carhire.user WHERE name=?";


    public UserDAO(){
        // Initialization
    }


    public boolean insert(User entity) {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(INSERT_USER);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPassword());
            statement.setNull(3, Types.INTEGER);

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


    public boolean delete(User entity) {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(DELETE_USER);

            statement.setString(1, entity.getName());

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


    public boolean update(User oldEntity, User newEntity) {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_USER);

            statement.setString(1, newEntity.getName());
            statement.setString(2, newEntity.getPassword());
            statement.setString(3, oldEntity.getName());

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


    public User find(User entity) {
        return null;
    }


    public List<User> getAll() {

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
                String name = resultSet.getString("name");
                users.add(new User(name, ""));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return users;
    }


    public boolean contains(User entity) {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(CHECK_IF_CONTAINS);

            statement.setString(1, entity.getName());

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false; // successfully
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }

        return true;
    }


    public boolean deleteAll(){

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(DELETE_ALL_USERS);

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


}
