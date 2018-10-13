package by.epam.project.command.ajax.car;

import by.epam.project.command.Command;
import by.epam.project.entity.Car;
import by.epam.project.entity.Order;
import by.epam.project.entity.Passport;
import by.epam.project.exception.ProjectException;
import by.epam.project.language.LangResourceManager;
import by.epam.project.service.impl.CarService;
import by.epam.project.service.impl.OrderService;
import by.epam.project.service.impl.PassportService;
import by.epam.project.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private final static Logger LOG = LogManager.getRootLogger();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        // add Passport data
        String name = req.getParameter(PARAM_RENTER_NAME);
        String surname = req.getParameter(PARAM_RENTER_SURNAME);
        String birthdayDate = req.getParameter(PARAM_RENTER_BIRTHDAY);
        String identificationNumber = req.getParameter(PARAM_RENTER_ID_NUMBER);

        Passport passport = new Passport(name, surname, birthdayDate, identificationNumber);
        passportService.add(passport);


        // take passport ID
        int passportId = passportService.takePassport(identificationNumber).getId();
        String userName = req.getParameter(PARAM_EMAIL);

        // associate this ID with the User
        userService.updatePassportId(passportId, userName);

        // add Order data
        int carId = Integer.valueOf(req.getParameter(PARAM_CAR_ID));
        String receivingDate = req.getParameter(PARAM_RECEIVING_DATE);
        String returnDate = req.getParameter(PARAM_RETURN_DATE);

        int daysDifference = daysBtwTwoDates(receivingDate, returnDate);

        BigDecimal rentalPrice = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_RENTAL_PRICE)))
                .multiply(BigDecimal.valueOf(daysDifference));
        BigDecimal adServicePrice = BigDecimal.valueOf(Double.valueOf(req.getParameter(PARAM_AD_SERVICE_PRICE)));
        int orderIsPaid = 0; // request's default value 0 - is not paid
        String adInfo = req.getParameter(PARAM_AD_INFO);

        Order order = new Order(0, userName, carId, receivingDate, returnDate,
                rentalPrice, adServicePrice, orderIsPaid, adInfo);


        if(orderService.add(order)){
            resp.getWriter().write(langManager.getString("request.sent"));
        }
        else{
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
