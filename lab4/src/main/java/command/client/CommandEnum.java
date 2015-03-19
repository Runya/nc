package command.client;


import command.ActionCommand;

/**
 * Created by Ruslan on 18.03.2015.
 */
public enum CommandEnum {

    LOGIN{
        {
            //todo add login command
        }
    },
    LOGOUT{
        {
            //todo add logout command
        }
    };


    ActionCommand command;

    public ActionCommand getCurrentComand(){
        return command;
    }


}
