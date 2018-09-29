package by.epam.project.model.pool;

import java.util.ResourceBundle;

public class MySqlDBResourceManager {

    private final static MySqlDBResourceManager instance = new MySqlDBResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle("MySqlDBProperties");

    public static MySqlDBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }


}
