package app.model.entities;

public class Order {

    private int id; // auto increment
    private String user_name;
    private int car_id;
    private String receiving_date;
    private String return_date;
    private double rental_price;
    private int order_is_paid; // 0 - order is not paid,, 1 - order is paid.
    private String ad_info; // 0 - order is not paid,, 1 - order is paid.


    public Order(String user_name, int car_id, String receiving_date,
                 String return_date, double rental_price, int order_is_paid, String ad_info){
        this.user_name = user_name;
        this.car_id = car_id;
        this.receiving_date = receiving_date;
        this.return_date = return_date;
        this.rental_price = rental_price;
        this.order_is_paid = order_is_paid;
        this.ad_info = ad_info;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }


    public int getCarId() {
        return car_id;
    }

    public void setCarId(int car_id) {
        this.car_id = car_id;
    }


    public String getReceivingDate() {
        return receiving_date;
    }

    public void setReceivingDate(String receiving_date) {
        this.receiving_date = receiving_date;
    }


    public String getReturnDate() {
        return return_date;
    }

    public void setReturnDate(String return_date) {
        this.return_date = return_date;
    }


    public double getRentalPrice() {
        return rental_price;
    }

    public void setRentalPrice(double rental_price) {
        this.rental_price = rental_price;
    }


    public int getOrderIsPaid() {
        return order_is_paid;
    }

    public void setOrderIsPaid(int order_is_paid) {
        this.order_is_paid = order_is_paid;
    }


    public String getAdInfo() {
        return ad_info;
    }

    public void setAdInfo(String ad_info) {
        this.ad_info = ad_info;
    }


    @Override
    public String toString() {
        return "user_name = " + user_name
                + ",\ncar_id = " + car_id
                + ",\nreceiving_date = " + receiving_date
                + ",\nreturn_date = " + return_date
                + ",\nrental_price = " + rental_price
                + ",\norder_is_paid = " + order_is_paid
                + ",\nad_info = " + ad_info
                ;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if(user_name.equals(order.user_name) && car_id == order.car_id
                && receiving_date.equals(order.receiving_date) && return_date.equals(order.return_date)
                && rental_price == order.rental_price && order_is_paid == order.order_is_paid
                && ad_info.equals(order.ad_info)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result * car_id * (int)rental_price + order_is_paid
                + (user_name != null ? user_name.hashCode() : 0)
                + (receiving_date != null ? receiving_date.hashCode() : 0)
                + (return_date != null ? return_date.hashCode() : 0)
                + (ad_info != null ? ad_info.hashCode() : 0);
        return result;
    }


}
