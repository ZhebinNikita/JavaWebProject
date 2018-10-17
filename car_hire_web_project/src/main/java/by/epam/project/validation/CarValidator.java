package by.epam.project.validation;

import by.epam.project.entity.Car;
import by.epam.project.entity.CarClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public final class CarValidator {

    private final static Logger LOG = LogManager.getRootLogger();
    private final static int MIN_NAME_LENGTH = 2;
    private final static int MAX_NAME_LENGTH = 45;
    private final static String EMPTY_SYMBOL = "";


    public CarValidator(){
        // Initialisation
    }


    public static boolean checkCar(Car car) {

        String name = car.getName();
        BigDecimal dailyRentalPrice = car.getDailyRentalPrice();
        CarClass carClass = car.getCarClass();

        if(name == null || dailyRentalPrice == null || carClass == null
                || name.equals(EMPTY_SYMBOL)
                || dailyRentalPrice.compareTo(new BigDecimal(0)) <= 0) {
            return false;
        }

        return true;
    }


}
