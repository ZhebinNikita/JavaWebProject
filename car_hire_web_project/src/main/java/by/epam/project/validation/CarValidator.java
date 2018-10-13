package by.epam.project.validation;

import by.epam.project.entity.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CarValidator {

    private final static Logger LOG = LogManager.getRootLogger();
    private final static int MIN_NAME_LENGTH = 2;
    private final static int MAX_NAME_LENGTH = 45;


    public CarValidator(){
        // Initialisation
    }


    public static boolean checkCar(Car car) {
        return false;
    }


}
