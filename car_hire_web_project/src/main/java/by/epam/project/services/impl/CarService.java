package by.epam.project.services.impl;

import by.epam.project.services.Service;
import by.epam.project.dao.impl.CarDao;
import by.epam.project.model.entity.Car;
import by.epam.project.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class CarService implements Service {

    private final static Logger LOG = LogManager.getRootLogger();
    private CarDao carDao = new CarDao();


    public boolean add(Car car) throws ProjectException {
        boolean carAdded;
        carAdded = carDao.insert(car);
        return carAdded;
    }


    public boolean deleteByID(Car car) throws ProjectException {
        boolean carDeleted;
        carDeleted = carDao.delete(car);
        return carDeleted;
    }


    public boolean updateByID(Car oldCar, Car newCar) throws ProjectException {
        boolean carUpdated;
        carUpdated = carDao.update(oldCar, newCar);
        return carUpdated;
    }


    public List<Car> getAllCars() throws ProjectException {
        List<Car> cars = carDao.takeAll();
        return cars;
    }


    public List<Car> takeNotRentedCars() throws ProjectException {
        List<Car> notRentedCars = carDao.takeNotRentedCars();
        return notRentedCars;
    }


    public List<Car> takeRentedCars() throws ProjectException {
        List<Car> rentedCars = carDao.takeRentedCars();
        return rentedCars;
    }

}
