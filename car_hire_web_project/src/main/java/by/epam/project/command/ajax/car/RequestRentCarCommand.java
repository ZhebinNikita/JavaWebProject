package by.epam.project.command.ajax.car;

import by.epam.project.command.Command;
import by.epam.project.entity.Order;
import by.epam.project.entity.Passport;
import by.epam.project.exception.ProjectException;
import by.epam.project.lang.LangResourceManager;
import by.epam.project.service.impl.*;
import by.epam.project.validation.OrderValidator;
import by.epam.project.validation.PassportValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static by.epam.project.validation.XssValidator.xssValidate;

public class RequestRentCarCommand implements Command {

    // Order data
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_CAR_ID = "carId";
    private static final String PARAM_RECEIVING_DATE = "receiving_date";
    private static final String PARAM_RETURN_DATE = "return_date";
    private static final String PARAM_RENTAL_PRICE = "rentalPrice";
    private static final String PARAM_AD_SERVICE_PRICE = "adServicePrice";
    private static final String PARAM_ORDER_IS_PAID = "orderIsPaid";
    private static final String PARAM_AD_INFO = "adInfo";
    // Passport data
    private static final String PARAM_RENTER_NAME = "renter_name";
    private static final String PARAM_RENTER_SURNAME = "renter_surname";
    private static final String PARAM_RENTER_BIRTHDAY = "renter_birthday";
    private static final String PARAM_RENTER_ID_NUMBER = "renter_id_number";

    private OrderService orderService = new OrderService();
    private PassportService passportService = new PassportService();
    private UserService userService = new UserService();
    private CarService carService = new CarService();
    private AccountService accountService = new AccountService();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        String userName = req.getParameter(PARAM_EMAIL);

        // take Passport data
        String name = req.getParameter(PARAM_RENTER_NAME);
        String surname = req.getParameter(PARAM_RENTER_SURNAME);
        String birthdayDate = req.getParameter(PARAM_RENTER_BIRTHDAY);
        String identificationNumber = req.getParameter(PARAM_RENTER_ID_NUMBER);
        String adInfo = req.getParameter(PARAM_AD_INFO);
        String receivingDate = req.getParameter(PARAM_RECEIVING_DATE);
        String returnDate = req.getParameter(PARAM_RETURN_DATE);
        BigDecimal adServicePrice = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_AD_SERVICE_PRICE)));
        int carId = Integer.valueOf(req.getParameter(PARAM_CAR_ID));
        int orderIsPaid = 0; // request's default value 0 - is not paid
        BigDecimal carDailyRentalPrice = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_RENTAL_PRICE)));
        BigDecimal userBalance = accountService.take(userName).getBalance();


        //// Validation ////
        if (!OrderValidator.check(receivingDate, returnDate)) {
            resp.getWriter().write(langManager.getString("validation.is.failed"));
            return;
        }
        //// Validation ////


        int daysDifference = daysBtwTwoDates(receivingDate, returnDate);

        BigDecimal rentalPrice = carDailyRentalPrice.multiply(BigDecimal.valueOf(daysDifference));


        if(rentalPrice.compareTo(userBalance) > 0) {
            resp.getWriter().write(langManager.getString("validation.is.failed"));
            return;
        }


        //// XSS validation ////
        receivingDate = xssValidate(receivingDate);
        returnDate = xssValidate(returnDate);
        adInfo = xssValidate(adInfo);
        userName = xssValidate(userName);
        name = xssValidate(name);
        surname = xssValidate(surname);
        birthdayDate = xssValidate(birthdayDate);
        identificationNumber = xssValidate(identificationNumber);
        //// XSS validation ////



        Passport passport = new Passport(name, surname, birthdayDate, identificationNumber);

        //// Validation ////
        if (!PassportValidator.check(passport)) {
            resp.getWriter().write(langManager.getString("validation.is.failed"));
            return;
        }
        //// Validation ////


        // if the user has a passport, then update its data
        int pId = userService.takePassportId(userName);
        if (pId > 0) {
            passportService.updateByID(new Passport(pId), passport);
        } else {
            // add the passport if it's a new one
            if (!passportService.contains(passport)) {
                passportService.add(passport);
            }
        }

        // take passport ID
        int passportId = passportService.takePassport(identificationNumber).getId();

        // associate this ID with the User
        if (!userService.updatePassportId(passportId, userName)) {
            Passport p = new Passport(passportId);
            passportService.deleteByID(p);
        }

        // set this car Rented
        if (!carService.setRented(carId)) {
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
            return;
        }

        Order order = new Order(0, userName, carId, receivingDate, returnDate,
                rentalPrice, adServicePrice, orderIsPaid, adInfo);

        //// Validation ////
        if (!OrderValidator.check(order)) {
            resp.getWriter().write(langManager.getString("validation.is.failed"));
            return;
        }
        //// Validation ////


        if (orderService.add(order)) {
            resp.getWriter().write(langManager.getString("request.sent"));
        } else {
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
        }
    }


    private int daysBtwTwoDates(String dateSts1, String dateStr2) throws ProjectException {

        try {
            // create format in which we'll parse the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date1 = dateFormat.parse(dateSts1);
            Date date2 = dateFormat.parse(dateStr2);

            long milliseconds = date2.getTime() - date1.getTime();

            // 24 часа = 1 440 минут = 1 день
            return ((int) (milliseconds / (24 * 60 * 60 * 1000)));

        } catch (Exception e) {
            throw new ProjectException(e);
        }
    }


}
