package by.epam.project.command.ajax.car;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.entity.CarClass;
import by.epam.project.entity.Car;
import by.epam.project.lang.LangResourceManager;
import by.epam.project.service.impl.CarService;
import by.epam.project.validation.CarValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static by.epam.project.validation.XssValidator.xssValidate;

public class AddCarCommand implements Command {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_DAILY_RENTAL_PRICE = "daily_rental_price";
    private static final String PARAM_CAR_CLASS = "car_class";
    private static final String PARAM_AMOUNT_CARS = "amount_cars";
    private static final Logger LOG = LogManager.getRootLogger();
    private CarService carService = new CarService();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        boolean executed = false;

        String name = req.getParameter(PARAM_NAME);
        BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_DAILY_RENTAL_PRICE)));
        CarClass car_class = CarClass.valueOf(req.getParameter(PARAM_CAR_CLASS).toUpperCase());
        int amount = Integer.valueOf(req.getParameter(PARAM_AMOUNT_CARS));


        ////////////////////////// XSS validation
        name = xssValidate(name);


        Car car = new Car(1, name, daily_rental_price, car_class, 0);

        // validation
        if (!CarValidator.checkCar(car)) {
            resp.getWriter().write(langManager.getString("validation.is.failed"));
            return;
        }

        for (int i = 0; i < amount; i++) {
            if (carService.add(car)) {
                if (i == amount - 1) {
                    executed = true;
                }
            } else {
                LOG.warn("Car (" + (i+1) + ") was not added.");
                executed = false;
                break;
            }
        }

        if(executed)
            resp.getWriter().write(langManager.getString("data.added"));
        else
            resp.getWriter().write(langManager.getString("smth.went.wrong"));

    }

}
