package dao;

import util.exeption.PersistException;

import java.sql.Connection;

/**
 * Created by Ruslan on 02.03.2015.
 */
public interface DaoFactory<Context>{


    public interface DaoCreator<Context> {
        public GenericDao create(Context context);
    }

    public GenericDao getDao(Context context, Class daoClass) throws PersistException;

    public Context getContext() throws PersistException;



}