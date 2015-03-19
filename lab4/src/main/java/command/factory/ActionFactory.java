package command.factory;

import command.ActionCommand;
import command.EmptyCommand;
import command.client.CommandEnum;
import resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ruslan on 18.03.2015.
 */
public class ActionFactory {

    public ActionCommand defineCommand(HttpServletRequest request){
        ActionCommand current = new EmptyCommand();

        String action = request.getParameter("command");

        if (action == null || action.isEmpty()){

            return current;
        }

        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentComand();
        } catch (IllegalArgumentException e){
            request.setAttribute("wrongAcrion", action + MessageManager.getProperty("message.wrongaction"));
        }

        return current;

    }

}
