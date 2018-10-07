package by.epam.project.parser;

public class CarDOM {

    private String name;
    private double daily_rental_price;
    private String car_class;
    private int busyness;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDaily_rental_price() {
        return daily_rental_price;
    }

    public void setDaily_rental_price(double daily_rental_price) {
        this.daily_rental_price = daily_rental_price;
    }

    public String getCar_class() {
        return car_class;
    }

    public void setCar_class(String car_class) {
        this.car_class = car_class;
    }

    public int getBusyness() {
        return busyness;
    }

    public void setBusyness(int busyness) {
        this.busyness = busyness;
    }


    @Override
    public String toString() {
        return "Name: " + this.name
                + ", Daily rental price: " + this.daily_rental_price
                + ", Class: " + this.car_class
                + ", Busyness: " + this.busyness;
    }
}
