package by.epam.project.command.car;

import by.epam.project.command.Command;
import by.epam.project.dao.impl.CarDao;
import by.epam.project.exception.ProjectException;
import by.epam.project.model.entity.Car;
import by.epam.project.services.impl.CarService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TakeNRCarsCommand implements Command {

    private CarService carService = new CarService();

    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed = false;

        List<Car> notRentedCars = carService.takeNotRentedCars();
        req.setAttribute("notRentedCars", notRentedCars);

        if (!notRentedCars.isEmpty())
            executed = true;

        return executed;
    }

}
