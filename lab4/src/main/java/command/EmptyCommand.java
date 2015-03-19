package command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ruslan on 18.03.2015.
 */
public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
