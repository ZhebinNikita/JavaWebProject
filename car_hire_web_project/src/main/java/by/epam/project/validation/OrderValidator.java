package by.epam.project.validation;

import by.epam.project.entity.Order;
import by.epam.project.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class OrderValidator implements Validator{

    private static final String REGEX_DATE = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
    private static final Logger LOG = LogManager.getRootLogger();


    public static boolean check(Order order) throws ProjectException{
        return check(order.getReceivingDate(), order.getReturnDate());
    }


    public static boolean check(String receivingDate, String returnDate) throws ProjectException{

        long receivingDateInMills = getTimeInMillis(receivingDate);
        long returnDateInMills = getTimeInMillis(returnDate);

        long currentTime = getCurrentTimeInMillis();


        LOG.info("receivingDate = (" + receivingDateInMills + ")");
        LOG.info("returnDate = (" + returnDateInMills + ")");
        LOG.info("currentTime = (" + currentTime + ")");


        if(!receivingDate.matches(REGEX_DATE) || !returnDate.matches(REGEX_DATE)
                || receivingDateInMills < currentTime || returnDateInMills < currentTime
                || receivingDateInMills > returnDateInMills) {
            return false;
        }
        else{
            return true;
        }

    }


    private static long getTimeInMillis(String date) throws ProjectException {

        Date date1 = null;
        try {
            // create format in which we'll parse the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            date1 = dateFormat.parse(date);
        } catch (ParseException e){
            throw new ProjectException(e);
        }

        return date1.getTime();
    }


    private static long getCurrentTimeInMillis() throws ProjectException {

        String date = LocalDate.now().toString();

        Date date1 = null;
        try {
            // create format in which we'll parse the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            date1 = dateFormat.parse(date);
        } catch (ParseException e){
            throw new ProjectException(e);
        }

        return date1.getTime();
    }

}
