package by.epam.project.controller.services.impl;

import by.epam.project.controller.services.EntityService;
import by.epam.project.model.dao.impl.OrderDao;
import by.epam.project.model.entities.Order;
import by.epam.project.model.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderService implements EntityService<Order> {

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


    public List<Order> getAllOrder() throws ProjectException {
        List<Order> orders = orderDao.getAll();
        return orders;
    }

}
