package app.model.entities;


public class Car {

    private String name;
    private double dailyRentalPrice;
    private CarClass carClass;
    private int rented; // 0 - car is not rented, 1 - car is rented.


    public Car(String name, double daily_rental_price, CarClass carClass) {
        this.name = name;
        this.dailyRentalPrice = daily_rental_price;
        this.carClass = carClass;
        this.rented = 0;
    }
    public Car(String name, double daily_rental_price, CarClass carClass, int rented) {
        this.name = name;
        this.dailyRentalPrice = daily_rental_price;
        this.carClass = carClass;
        this.rented = rented;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getDailyRentalPrice() {
        return dailyRentalPrice;
    }

    public void setDailyRentalPrice(double dailyRentalPrice) {
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
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
