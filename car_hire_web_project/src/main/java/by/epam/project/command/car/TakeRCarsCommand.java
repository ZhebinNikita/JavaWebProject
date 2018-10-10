package by.epam.project.command.car;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.model.entity.Car;
import by.epam.project.services.impl.CarService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TakeRCarsCommand implements Command {

    private CarService carService = new CarService();

    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed = false;

        List<Car> rentedCars = carService.takeRentedCars();
        req.setAttribute("rentedCars", rentedCars);

        if (!rentedCars.isEmpty())
            executed = true;

        return executed;
    }

}
