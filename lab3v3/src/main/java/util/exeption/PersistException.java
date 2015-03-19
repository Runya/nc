package util.exeption;

/**
 * Created by Ruslan on 02.03.2015.
 */
public class PersistException extends Exception {
    
    private String s;
    
    public PersistException(String s) {
        this.s = s;
    }

    public PersistException(Exception e) {
        super(e);
    }
}
