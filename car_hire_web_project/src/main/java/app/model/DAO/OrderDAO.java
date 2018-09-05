package app.model.DAO;

import app.model.entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements EntityDAO<Order>{

    private static final String INSERT_ORDER = "insert into carhire.order values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ORDER = "DELETE FROM carhire.order WHERE id=?";
    private static final String UPDATE_ORDER = "UPDATE carhire.order SET user_name=?, car_id=?," +
            " receiving_date=?, return_date=?, rental_price=?, ad_service_price=?," +
            "order_is_paid=?, ad_info=? WHERE id=?";

    private static final String GET_ALL_ORDERS = "SELECT * FROM carhire.order";

    private static final String CHECK_IF_CONTAINS_BY_NAME = "SELECT name FROM carhire.order WHERE user_name=?";


    public OrderDAO(){
        // инициализация
    }


    @Override
    public boolean insert(Order entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER);

            statement.setNull(1, Types.INTEGER);
            statement.setString(2, entity.getUserName());
            statement.setInt(3, entity.getCarId());
            statement.setString(4, entity.getReceivingDate());
            statement.setString(5, entity.getReturnDate());
            statement.setDouble(6, entity.getRentalPrice());
            statement.setDouble(7, entity.getAdServicePrice());
            statement.setInt(8, entity.getOrderIsPaid());
            statement.setString(9, entity.getAdInfo());

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
    public boolean delete(Order entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(DELETE_ORDER);
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
    public boolean update(Order oldEntity, Order newEntity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.
                    prepareStatement(UPDATE_ORDER);
            statement.setString(1, newEntity.getUserName());
            statement.setInt(2, newEntity.getCarId());
            statement.setString(3, newEntity.getReceivingDate());
            statement.setString(4, newEntity.getReturnDate());
            statement.setDouble(5, newEntity.getRentalPrice());
            statement.setDouble(6, newEntity.getAdServicePrice());
            statement.setInt(7, newEntity.getOrderIsPaid());
            statement.setString(8, newEntity.getAdInfo());
            statement.setInt(9, oldEntity.getId());

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


    @Override
    public Order find(Order entity) {
        return null;
    }


    @Override
    public List<Order> getAll() {

        List<Order> orders = new ArrayList<>();

        Connection connection = DAO.createConnection();

        try {

            Statement st = connection.createStatement();

            ResultSet result = st.executeQuery(GET_ALL_ORDERS);

            while (result.next()) {
                int id = result.getInt(1);
                String user_name = result.getString(2);
                int car_id = result.getInt(3);
                String receiving_date = result.getString(3);
                String return_date = result.getString(4);
                double ad_service_price = result.getDouble(5);
                double rental_price = result.getDouble(6);
                int order_is_paid = result.getInt(7);
                String ad_info = result.getString(8);

                orders.add(new Order(id, user_name, car_id, receiving_date,
                        return_date, ad_service_price,
                rental_price, order_is_paid, ad_info));
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

        return orders;
    }


    @Override
    public boolean contains(Order entity) {

        Connection connection = DAO.createConnection();

        try {

            PreparedStatement statement = connection.prepareStatement(CHECK_IF_CONTAINS_BY_NAME);

            statement.setString(1, entity.getUserName());

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


}
