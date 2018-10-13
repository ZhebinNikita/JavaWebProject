package by.epam.project.service.impl;

import by.epam.project.service.Service;
import by.epam.project.database.dao.impl.OrderDao;
import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderService implements Service {

    private final static Logger LOG = LogManager.getRootLogger();
    private OrderDao orderDao = new OrderDao();


    public boolean add(Order o) throws ProjectException {
        boolean oAdded;
        oAdded = orderDao.insert(o);
        return oAdded;
    }


    public boolean deleteByID(Order o) throws ProjectException {
        boolean oDeleted;
        oDeleted = orderDao.delete(o);
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

}
