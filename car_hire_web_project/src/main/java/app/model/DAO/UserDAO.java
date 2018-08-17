package app.model.DAO;

import app.model.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class UserDAO implements EntityDAO<User> {


    public UserDAO(){
        // инициализация
    }


    public boolean insert(User entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement
                    ("insert into carhire.user values(?, SHA2(?, 224))");

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPassword());

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

            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM carhire.user WHERE name=?");
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


    public boolean update(User oldEntity, User newEntity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.
                    prepareStatement("UPDATE carhire.user SET name=?, pass=? WHERE name=?");
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

            ResultSet result = st.executeQuery("SELECT * FROM carhire.user");

            for (;;) {
                if (result.next()) {
                    String name = result.getString("name");
                    users.add(new User(name, ""));
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

        return users;
    }


    public boolean contains(User entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.
                    prepareStatement("SELECT name FROM carhire.user WHERE name=?");

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
                    connection.prepareStatement("DELETE FROM carhire.user");

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
