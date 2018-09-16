package app.model.validation;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class UserValidator {

    private final static Logger LOG = LogManager.getRootLogger();
    private final static String CHECK_EMAIL = "@";


    public UserValidator(){
        // Initialisation
    }


    public static boolean checkUser(String name, String password){

        if(name.contains(CHECK_EMAIL) && password.length() >= 8){
            LOG.info("User's validation completed successfully.");
            return true;
        }
        else{
            LOG.info("User's validation is failed." + name.matches(CHECK_EMAIL) + " - " + (password.length() >= 8));
            return false;
        }
    }

}
