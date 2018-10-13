package by.epam.project.command.ajax.car;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.entity.CarClass;
import by.epam.project.entity.Car;
import by.epam.project.language.LangResourceManager;
import by.epam.project.service.impl.CarService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class AddCarCommand implements Command {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_DAILY_RENTAL_PRICE = "daily_rental_price";
    private static final String PARAM_CAR_CLASS = "car_class";
    private static final String PARAM_AMOUNT_CARS = "amount_cars";
    private CarService carService = new CarService();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        boolean executed = false;

        String name = req.getParameter(PARAM_NAME);
        BigDecimal daily_rental_price = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_DAILY_RENTAL_PRICE)));
        CarClass car_class = CarClass.valueOf(req.getParameter(PARAM_CAR_CLASS).toUpperCase());
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

        if(executed)
            resp.getWriter().write(langManager.getString("data.added"));
        else
            resp.getWriter().write(langManager.getString("smth.went.wrong"));

    }

}
