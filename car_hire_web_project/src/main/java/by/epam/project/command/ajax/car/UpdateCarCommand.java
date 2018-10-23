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

public class UpdateCarCommand implements Command {

    private static final String PARAM_ID = "id";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DAILY_RENTAL_PRICE = "daily_rental_price";
    private static final String PARAM_CAR_CLASS = "car_class";
    private CarService carService = new CarService();
    private final static Logger LOG = LogManager.getRootLogger();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        int id = Integer.valueOf(req.getParameter(PARAM_ID));
        String name = req.getParameter(PARAM_NAME);
        BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_DAILY_RENTAL_PRICE)));
        CarClass car_class = CarClass.valueOf(req.getParameter(PARAM_CAR_CLASS).toUpperCase());


        //// XSS validation ////
        name = xssValidate(name);
        //// XSS validation ////


        Car oldCar = new Car(id);
        Car newCar = new Car(id, name, daily_rental_price, car_class, 0);


        // validation
        if (!CarValidator.check(newCar)) {
            resp.getWriter().write(langManager.getString("validation.is.failed"));
            return;
        }


        if (carService.updateByID(oldCar, newCar)) {
            resp.getWriter().write(langManager.getString("data.updated"));
        } else {
            LOG.error("Something went wrong with updating car ID(" + id + ")");
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
        }
    }

}
