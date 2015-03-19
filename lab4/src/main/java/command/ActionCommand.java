package command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ruslan on 18.03.2015.
 */
public interface ActionCommand {
    String execute(HttpServletRequest request);
}
