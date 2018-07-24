package app.model.DAO;

import app.model.entities.Car;
import java.util.List;


/**
 * CarDAO использует Transfer Objects (Car Objects) для передачи данных
 * */
public class CarDAO implements EntityDAO<Car>{


    public CarDAO(){
        // инициализация
    }


    public boolean insert(Car entity) {
        return false;
    }


    public boolean delete(Car entity) {
        return false;
    }


    public boolean update(Car entity, Car newEntity) {
        return false;
    }


    public Car find(Car entity) {
        return null;
    }


    public List<Car> getAll() {
        return null;
    }


}
