package by.epam.project.command.car;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.model.CarClass;
import by.epam.project.model.entity.Car;
import by.epam.project.services.impl.CarService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class UpdateCarCommand implements Command {

    private static final String PARAM_ID = "id";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DAILY_RENTAL_PRICE = "daily_rental_price";
    private static final String PARAM_CAR_CLASS = "car_class";
    private CarService carService = new CarService();


    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed;

        int id = Integer.valueOf(req.getParameter(PARAM_ID));
        String name = req.getParameter(PARAM_NAME);
        BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_DAILY_RENTAL_PRICE)));
        CarClass car_class = CarClass.valueOf(req.getParameter(PARAM_CAR_CLASS));

        Car oldCar = new Car(id);
        Car newCar = new Car(id, name, daily_rental_price, car_class, 0);

        executed = carService.updateByID(oldCar, newCar);

        return executed;
    }

}
