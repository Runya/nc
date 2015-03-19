package util.exeption;

/**
 * Created by Ruslan on 14.03.2015.
 */
public class ModelException extends Exception {
    public ModelException(Exception e) {
        super(e);
    }



    public ModelException(String s) {
        super(s);
    }
}
