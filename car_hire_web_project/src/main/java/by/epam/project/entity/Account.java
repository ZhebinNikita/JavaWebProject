package by.epam.project.entity;

import java.math.BigDecimal;

public class Account {

    private String userEmail;
    private BigDecimal balance;


    public Account(String userEmail, BigDecimal balance){
        this.userEmail = userEmail;
        this.balance = balance;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
