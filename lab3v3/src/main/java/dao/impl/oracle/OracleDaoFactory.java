package dao.impl.oracle;

import dao.DaoFactory;
import dao.GenericDao;
import entity.*;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import oracle.jdbc.driver.OracleDriver;
import util.exeption.PersistException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Ruslan on 3/10/2015.
 */
public class OracleDaoFactory implements DaoFactory<Connection> {

    private String user = "lab3";
    private String password = "pass";
    private String url = "jdbc:oracle:thin:lab3@//192.168.56.101:1521/orcl";
    private String driver = "org.postgresql.Driver";
    private Map<Class, DaoCreator> creators;

    private static OracleDaoFactory oracleDaoFactory;






    @Override
    public Connection getContext() throws PersistException{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return connection;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null){
            throw new PersistException("Dao object for " + dtoClass + "not found");
        }
        return creator.create(connection);
    }

    private OracleDaoFactory() {

       /* try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.err.println("не вдалось загрузити драйвер");
        }

        creators = new HashMap<>();
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new OracleUserDao(connection);
            }
        });

        creators.put(Department.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new OracleDepartmentDao(connection);
            }
        });

        creators.put(Project.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new OracleProjectDao(connection);
            }
        });

        creators.put(User_Department.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new OracleUsersDepartmentDao(connection);
            }
        });

        creators.put(User_Project.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new OracleUsersProjectsDao(connection);
            }
        });



        creators.put(User_Project.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new OracleUsersProjectsDao(connection);
            }
        });


    }

    public static OracleDaoFactory getOracleDaoFactory(){
        if (oracleDaoFactory == null){
            oracleDaoFactory = new OracleDaoFactory();
        }
        return oracleDaoFactory;
    }


}
