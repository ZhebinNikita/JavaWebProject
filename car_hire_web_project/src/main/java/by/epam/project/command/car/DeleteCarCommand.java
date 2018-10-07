package by.epam.project.command.car;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.model.entity.Car;
import by.epam.project.services.impl.CarService;

import javax.servlet.http.HttpServletRequest;

public class DeleteCarCommand implements Command {

    private static final String PARAM_ID = "id";
    private CarService carService = new CarService();


    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed;

        int id = Integer.valueOf(req.getParameter(PARAM_ID));
        Car car = new Car(id);

        executed = carService.deleteByID(car);

        return executed;
    }

}
