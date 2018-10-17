package by.epam.project.command.dispatch.car;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.entity.Car;
import by.epam.project.service.impl.CarService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TakeNRCarsCommand implements Command {

    private static final String ATTRIBUTE_NOT_RENTED_CARS = "notRentedCars";
    private CarService carService = new CarService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {

        List<Car> notRentedCars = carService.takeNotRentedCars();
        req.setAttribute(ATTRIBUTE_NOT_RENTED_CARS, notRentedCars);
    }

}
