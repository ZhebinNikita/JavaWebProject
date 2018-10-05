package by.epam.project.language;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LangResourceManager {

    INSTANCE;
    private ResourceBundle resourceBundle;
    private final String resourceName = "lang";


    /** Can be created only one object */
    LangResourceManager() {
        resourceBundle = ResourceBundle.getBundle(resourceName, Locale.getDefault());
    }

    public void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(resourceName, locale);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }

}
