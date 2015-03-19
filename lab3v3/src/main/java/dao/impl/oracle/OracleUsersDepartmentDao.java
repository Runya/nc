package dao.impl.oracle;

import dao.GenericDao;
import entity.User_Department;
import util.exeption.PersistException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ruslan on 13.03.2015.
 */
public class OracleUsersDepartmentDao implements GenericDao<User_Department, Integer> {

    private Connection connection;
    public OracleUsersDepartmentDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public User_Department create() throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public User_Department persist(User_Department object) throws PersistException {
        try {
            Statement statement = connection.createStatement();
            boolean rez = statement.execute("INSERT INTO users_departments (user_id, department_id) VALUES (" + object.getUserPK() + ", " + object.getDepartmentPK());
            if (!rez) throw new PersistException("persist error");
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return object;
    }

    @Override
    public User_Department getByPK(Integer id) throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public void update(User_Department object) throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public void delete(User_Department object) throws PersistException {
        try {
            Statement statement = connection.createStatement();
            int rez = statement.executeUpdate("DELETE FROM users_departments WHERE user_id=" + object.getUserPK() +"and department_id=" + object.getDepartmentPK());
            if (rez != 1) throw new PersistException("delete error");
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<User_Department> getAll() throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public List<Integer> getAllByFirstPK(Integer key) throws PersistException {
        List<Integer> list = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT department_id FROM users_departments WHERE user_id=" + key);
            while (set.next()){
                list.add(set.getInt("department_id"));
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public List<Integer> getAllBySecondPK(Integer key) throws PersistException {
        List<Integer> list = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT user_id FROM users_departments WHERE department_id=" + key);
            while (set.next()){
                list.add(set.getInt("user_id"));
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return list;
    }
}
