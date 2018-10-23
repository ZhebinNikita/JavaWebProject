package by.epam.project.validation;


import by.epam.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class UserValidator implements Validator{

    private final static Logger LOG = LogManager.getRootLogger();
    private final static String CHECK_EMAIL = "@";
    private final static String SPACE = " ";
    private final static String EMPTY = "";
    private final static int MAX_EMAIL_LENGTH = 40;
    private final static int MIN_EMAIL_LENGTH = 5;
    private final static int MAX_PASSWORD_LENGTH = 40;
    private final static int MIN_PASSWORD_LENGTH = 8;


    public static boolean check(User user){

        String email = user.getEmail();
        String password = user.getPassword();

        if(email.contains(CHECK_EMAIL)
                && !email.contains(SPACE)
                && !email.equals(EMPTY)
                && email.length() < MAX_EMAIL_LENGTH
                && email.length() >= MIN_EMAIL_LENGTH

                && !password.contains(SPACE)
                && password.length() >= MIN_PASSWORD_LENGTH
                && password.length() < MAX_PASSWORD_LENGTH){
            LOG.info("User ("+email+") validation completed successfully.");
            return true;
        }
        else{
            LOG.info("User ("+email+") validation is failed.");
            return false;
        }
    }

}
