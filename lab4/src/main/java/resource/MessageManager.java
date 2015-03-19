package resource;

import java.util.ResourceBundle;

/**
 * Created by Ruslan on 19.03.2015.
 */
public class MessageManager {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resource.messages");

    private MessageManager(){}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
