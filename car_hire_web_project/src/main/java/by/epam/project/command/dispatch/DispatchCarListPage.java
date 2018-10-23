package by.epam.project.command.dispatch;

import by.epam.project.command.Command;
import by.epam.project.constant.PagePathConstant;
import by.epam.project.controller.servlet.Route;
import by.epam.project.entity.Car;
import by.epam.project.exception.ProjectException;
import by.epam.project.service.impl.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.epam.project.constant.ClientRole.ROLE;
import static by.epam.project.constant.ClientRole.ROLE_ADMIN;
import static by.epam.project.constant.ClientRole.ROLE_USER;

public class DispatchCarListPage implements Command {

    private Route route;
    private static final String ATTRIBUTE_RENTED_CARS = "rentedCars";
    private static final String ATTRIBUTE_NOT_RENTED_CARS = "notRentedCars";
    private CarService carService = new CarService();
    private static final Logger LOG = LogManager.getRootLogger();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        route = new Route();
        HttpSession session = req.getSession(true);
        boolean isAdmin;


        if (session.getAttribute(ROLE) != null) {
            if(session.getAttribute(ROLE).equals(ROLE_ADMIN)){
                isAdmin = true;
            }
            else if(session.getAttribute(ROLE).equals(ROLE_USER)) {
                isAdmin = false;
            }
            else{
                route.setPagePath(PagePathConstant.PAGE_MAIN);
                return;
            }
        }
        else{
            route.setPagePath(PagePathConstant.PAGE_MAIN);
            return;
        }
        LOG.info("IS ADMIN ??? (" + isAdmin + ")");



        List<Car> cars;

        if(req.getParameter("cars") != null) {

            switch (req.getParameter("cars")) {
                case "rented":
                    if(isAdmin){
                        cars = carService.takeRentedCars();
                    }
                    else{
                        cars = carService.takeNotRentedNotRepeatedCars();
                    }
                    req.setAttribute(ATTRIBUTE_RENTED_CARS, cars);
                    break;
                case "notRented":
                    if(isAdmin){
                        cars = carService.takeNotRentedCars();
                    }
                    else{
                        cars = carService.takeNotRentedNotRepeatedCars();
                    }
                    req.setAttribute(ATTRIBUTE_NOT_RENTED_CARS, cars);
                    break;
                default:
                    cars = carService.takeNotRentedCars();
                    req.setAttribute(ATTRIBUTE_NOT_RENTED_CARS, cars);
                    break;
            }
        }
        else{

            if(isAdmin){
                cars = carService.takeNotRentedCars();
                LOG.info("takeNotRentedCars");
            }
            else{
                LOG.info("takeNotRentedNotRepeatedCars");
                cars = carService.takeNotRentedNotRepeatedCars();
            }

            req.setAttribute(ATTRIBUTE_NOT_RENTED_CARS, cars);
        }


        route.setPagePath(PagePathConstant.PAGE_CAR_LIST);
    }


    @Override
    public Route getRoute() throws ProjectException {
        return route;
    }

}
