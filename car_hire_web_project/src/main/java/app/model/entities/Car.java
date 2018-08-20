package app.model.entities;



public class Car {

    private String name;
    private double dailyRentalPrice;
    private String carClass;
    private int rented; // 0 - car is not rented, 1 - car is rented.


    public Car(String name, double daily_rental_price, String carClass) {
        this.name = name;
        this.dailyRentalPrice = daily_rental_price;
        this.carClass = carClass;
        this.rented = 0;
    }
    public Car(String name, double daily_rental_price, String carClass, int rented) {
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


    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
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

        if (name != null ? !name.equals(car.name) : car.name != null) return false;
        return dailyRentalPrice > 0 && dailyRentalPrice == car.dailyRentalPrice;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int)dailyRentalPrice;
        return result;
    }


}
