package by.epam.project.command.car;

import by.epam.project.command.Command;
import by.epam.project.dao.impl.CarDao;
import by.epam.project.exception.ProjectException;
import by.epam.project.model.CarClass;
import by.epam.project.model.entity.Car;
import by.epam.project.services.impl.CarService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class AddCarCommand implements Command {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_DAILY_RENTAL_PRICE = "daily_rental_price";
    private static final String PARAM_CAR_CLASS = "car_class";
    private static final String PARAM_AMOUNT_CARS = "amount_cars";
    private CarService carService = new CarService();


    @Override
    public boolean execute(HttpServletRequest req) throws ProjectException {

        boolean executed = false;

        String name = req.getParameter(PARAM_NAME);
        BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_DAILY_RENTAL_PRICE)));
        CarClass car_class = CarClass.valueOf(req.getParameter(PARAM_CAR_CLASS));
        int amount = Integer.valueOf(req.getParameter(PARAM_AMOUNT_CARS));

        Car car = new Car(1, name, daily_rental_price, car_class, 0);

        for (int i = 0; i < amount; i++) {
            if (carService.add(car)) {
                if (i == amount - 1) {
                    executed = true;
                }
            } else {
                executed = false;
                break;
            }
        }


        return executed;
    }

}
