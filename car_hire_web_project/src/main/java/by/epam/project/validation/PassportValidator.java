package by.epam.project.validation;

import by.epam.project.entity.Passport;

public class PassportValidator implements Validator {

    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final int MAX_NAME_LENGTH = 45;
    private static final int MIN_NAME_LENGTH = 2;
    private static final String REGEX_DATE = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";


    public static boolean check(Passport passport){

        String name = passport.getName();
        String surname = passport.getSurname();
        String birthdayDate = passport.getBirthday();
        String identificationNumber = passport.getIdentification_number();


        if(name.contains(SPACE) || surname.contains(SPACE)
                || name.equals(EMPTY) || surname.equals(EMPTY)
                || name.length() > MAX_NAME_LENGTH || surname.length() > MAX_NAME_LENGTH
                || name.length() < MIN_NAME_LENGTH || surname.length() < MIN_NAME_LENGTH
                || !birthdayDate.matches(REGEX_DATE)
                || identificationNumber.equals(EMPTY) || identificationNumber.contains(SPACE)) {
            return false;
        }
        else{
            return true;
        }

    }

}
