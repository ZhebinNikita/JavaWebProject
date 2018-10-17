package by.epam.project.command.dispatch.car;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.entity.Car;
import by.epam.project.service.impl.CarService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TakeRCarsCommand implements Command {

    private static final String ATTRIBUTE_RENTED_CARS = "rentedCars";
    private CarService carService = new CarService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {

        List<Car> rentedCars = carService.takeRentedCars();
        req.setAttribute(ATTRIBUTE_RENTED_CARS, rentedCars);
    }

}
