package by.epam.project.validation;

import java.math.BigDecimal;

public final class BalanceValidator implements Validator {

    private static final double MAX_SUM = 100000;


    public static boolean check(BigDecimal money){

        if(Double.valueOf(money.toString()) <= 0.00d || Double.valueOf(money.toString()) > MAX_SUM) {
            return false;
        }
        else {
            return true;
        }
    }

}
