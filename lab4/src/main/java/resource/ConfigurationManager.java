package resource;

import java.util.ResourceBundle;

/**
 * Created by Ruslan on 19.03.2015.
 */
public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resource.config");

    /**
     * класс витягуючий інформацію з файлу config.properties
     */
    private ConfigurationManager(){}

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }

}
