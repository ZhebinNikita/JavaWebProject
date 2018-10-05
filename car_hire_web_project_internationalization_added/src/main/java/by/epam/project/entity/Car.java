package by.epam.project.entity;


import by.epam.project.model.CarClass;

import java.math.BigDecimal;

public class Car {

    private int id;
    private String name;
    private BigDecimal dailyRentalPrice;
    private CarClass carClass;
    private int rented; // 0 - car is not rented, 1 - car is rented.


    public Car(int id) {
        this.id = id;
    }

    public Car(int id, String name, BigDecimal daily_rental_price, CarClass carClass, int rented) {
        this.id = id;
        this.name = name;
        this.dailyRentalPrice = daily_rental_price;
        this.carClass = carClass;
        this.rented = rented;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BigDecimal getDailyRentalPrice() {
        return dailyRentalPrice;
    }

    public void setDailyRentalPrice(BigDecimal dailyRentalPrice) {
        this.dailyRentalPrice = dailyRentalPrice;
    }


    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }


    public int getRented() {
        return rented;
    }

    public void setRented(int rented) {
        this.rented = rented;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if(rented == car.rented && name.equals(car.name)
                && dailyRentalPrice.equals(car.dailyRentalPrice)
                && carClass.equals(car.carClass)){
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + id + rented
                + (name != null ? name.hashCode() : 0)
                + (dailyRentalPrice != null ? dailyRentalPrice.hashCode() : 0)
                + (carClass != null ? carClass.hashCode() : 0);
        return result;
    }


}
