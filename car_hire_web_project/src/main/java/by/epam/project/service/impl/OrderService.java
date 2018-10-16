package by.epam.project.service.impl;

import by.epam.project.database.dao.impl.CarDao;
import by.epam.project.service.Service;
import by.epam.project.database.dao.impl.OrderDao;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class OrderService implements Service {

    private final static Logger LOG = LogManager.getRootLogger();
    private OrderDao orderDao = new OrderDao();
    private CarDao carDao = new CarDao();


    public boolean add(Order o) throws ProjectException {
        boolean oAdded;
        oAdded = orderDao.insert(o);
        return oAdded;
    }


    public boolean deleteByID(int id) throws ProjectException {
        boolean oDeleted = false;

        // take carId to make it not rented
        Order order = orderDao.takeOrderById(id);
        if (carDao.setNotRented(order.getCarId())) {
            oDeleted = orderDao.delete(new Order(id));
        }
        return oDeleted;
    }


    public boolean updateByID(Order oldOrder, Order newOrder) throws ProjectException {
        boolean oUpdated;
        oUpdated = orderDao.update(oldOrder, newOrder);
        return oUpdated;
    }


    public List<Order> takeAllOrders() throws ProjectException {
        List<Order> orders = orderDao.takeAll();
        return orders;
    }


    public List<Order> takePaidOrders() throws ProjectException {
        List<Order> paidOrders = orderDao.takePaidOrders();
        return paidOrders;
    }


    public List<Order> takeNotPaidOrders() throws ProjectException {
        List<Order> notPaidOrders = orderDao.takeNotPaidOrders();
        return notPaidOrders;
    }


    public boolean updateOrderIsPaid(int id) throws ProjectException {
        boolean oUpdated;
        oUpdated = orderDao.updateOrderIsPaid(id);
        return oUpdated;
    }


    public boolean updateOrderIsNotPaid(int id) throws ProjectException {
        boolean oUpdated;
        oUpdated = orderDao.updateOrderIsNotPaid(id);
        return oUpdated;
    }


    public boolean contains(String email) throws ProjectException {
        boolean contains;
        contains = orderDao.contains(email);
        return contains;
    }


    public List<Order> takeOrdersByEmail(String email) throws ProjectException {
        return orderDao.takeOrdersByEmail(email);
    }


    public boolean updateAdInfo(int id, String adInfo) throws ProjectException {
        boolean updated;
        updated = orderDao.updateAdInfo(id, adInfo);
        return updated;
    }


    public boolean updateAdServicePrice(int id, BigDecimal adServicePrice) throws ProjectException {
        boolean updated;
        updated = orderDao.updateAdServicePrice(id, adServicePrice);
        return updated;
    }


    public Order takeOrder(int id) throws ProjectException {
        return orderDao.takeOrderById(id);
    }

}
