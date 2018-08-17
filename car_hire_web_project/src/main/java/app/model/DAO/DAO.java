package app.model.DAO;

import java.sql.*;


/**
 * Конкретная реализация DAO  для  "Entity"DAO
 * */
public abstract class DAO {

    private static final String URL= "jdbc:mysql://localhost:3306/carhire?serverTimezone=UTC";

    private static final String USER= "root";
    private static final String PASSWORD= "RootPassword12345";


    //   метод для создания соединения к БД MySQL
    public static Connection createConnection() {
        // Использовать DRIVER и DBURL для создания соединения
        // Рекомендовать реализацию/использование пула соединений

        Connection connection = null;

        try {
            // метод регистрации драйвера JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        if(connection == null){
            // LOG
        }

        return connection;
    }

}
/*
    private static final String URL= "jdbc:oracle:thin:@localhost:1521:orcl";

    private static final String USER= "NIKITAZHEBIN";
    private static final String PASSWORD= "orcl12345";


    //   метод для создания соединения к БД Oracle
    public static Connection createConnection() {
        // Использовать DRIVER и DBURL для создания соединения
        // Рекомендовать реализацию/использование пула соединений

        Connection connection = null;

        try {
            // метод регистрации драйвера JDBC
            //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        if(connection == null){
            // LOG
        }

        return connection;
    }
*/


