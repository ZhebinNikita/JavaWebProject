package app.model.DAO;

import java.sql.*;


/**
 * Конкретная реализация FactoryDAO  для  "Entity"DAO
 * */
public class FactoryDAO {

    private static final String URL= "jdbc:oracle:thin:@localhost:1521:orcl";

    private static final String USER= "NIKITAZHEBIN";
    private static final String PASSWORD= "orcl12345";


    // метод для создания соединений к "Entity"DAO

    public static Connection createConnection() {
        // Использовать DRIVER и DBURL для создания соединения
        // Рекомендовать реализацию/использование пула соединений

        Connection connection = null;

        try {
            // метод регистрации драйвера JDBC
            //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //C:\Users\никита\.m2\repository\com\oracle\ojdbc8\12.2.0.1.0\ojdbc8-12.2.0.1.0.jar!\oracle\jdbc\driver\OracleDriver.class
            //oracle.jdbc.driver.OracleDriver
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        if(connection == null){

        }

        return connection;
    }


    public CarDAO getCarDAO() {
        return new CarDAO();
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }


}
