package by.epam.project.validation;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class UserValidator {

    private final static Logger LOG = LogManager.getRootLogger();
    private final static String CHECK_EMAIL = "@";
    private final static int MAX_EMAIL_LENGTH = 40;
    private final static int MAX_PASSWORD_LENGTH = 40;


    public UserValidator(){
        // Initialisation
    }


    public static boolean checkUser(String email, String password){

        if(email.contains(CHECK_EMAIL)
                && !email.contains(" ")
                && !password.contains(" ")
                && password.length() >= 8
                && email.length() < MAX_EMAIL_LENGTH
                && password.length() < MAX_PASSWORD_LENGTH){
            LOG.info("User's ("+email+") validation completed successfully.");
            return true;
        }
        else{
            LOG.info("User's ("+email+") validation is failed.");
            return false;
        }
    }

}
