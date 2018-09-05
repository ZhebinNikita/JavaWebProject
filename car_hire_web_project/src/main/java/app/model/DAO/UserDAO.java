package app.model.DAO;

import app.model.entities.User;

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
        // инициализация
    }


    public boolean insert(User entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement
                    (INSERT_USER);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPassword());
            statement.setNull(3, Types.INTEGER);

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


    public boolean delete(User entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(DELETE_USER);
            statement.setString(1, entity.getName());

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


    public boolean update(User oldEntity, User newEntity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.
                    prepareStatement(UPDATE_USER);
            statement.setString(1, newEntity.getName());
            statement.setString(2, newEntity.getPassword());
            statement.setString(3, oldEntity.getName());

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


    public User find(User entity) {
        return null;
    }


    public List<User> getAll() {

        List<User> users = new ArrayList<User>();

        Connection connection = DAO.createConnection();

        try {

            Statement st = connection.createStatement();

            ResultSet result = st.executeQuery(GET_ALL_USERS);

            while (result.next()) {
                String name = result.getString("name");
                users.add(new User(name, ""));
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

        return users;
    }


    public boolean contains(User entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.
                    prepareStatement(CHECK_IF_CONTAINS);

            statement.setString(1, entity.getName());

            ResultSet res = statement.executeQuery();

            if (!res.next()) {
                return false; // successfully
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

        return true;
    }


    public boolean deleteAll(){

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement =
                    connection.prepareStatement(DELETE_ALL_USERS);

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


}
