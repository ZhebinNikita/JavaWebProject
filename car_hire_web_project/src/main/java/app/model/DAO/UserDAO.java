package app.model.DAO;

import app.model.entities.User;
import app.model.DAO.EntityDAO;
import app.model.DAO.FactoryDAO;

import java.util.List;
import java.sql.*;

/**
 * UserDAO использует Transfer Objects (User Objects) для передачи данных
 * */
public class UserDAO implements EntityDAO<User> {


    public UserDAO(){
        // инициализация
    }


    public boolean insert(User entity) {
        try {
            Connection connection = FactoryDAO.createConnection();

            PreparedStatement preparedStatement = connection.prepareStatement
                    ("insert into NIKITAZHEBIN.USERS values(?,?)");

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getPassword());

            int i = preparedStatement.executeUpdate();

            //connection.close();

            if (i > 0) {
                //out.print("You are successfully registered..."); // отправляем ответ с сервера клиенту
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean delete(User entity) {
        return false;
    }


    public boolean update(User entity, User newEntity) {
        return false;
    }


    public User find(User entity) {
        return null;
    }


    public List<User> getAll() {
        return null;
    }


}
