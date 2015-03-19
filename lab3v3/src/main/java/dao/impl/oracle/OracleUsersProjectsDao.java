package dao.impl.oracle;

import dao.GenericDao;
import entity.User_Project;
import util.exeption.PersistException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ruslan on 13.03.2015.
 */
public class OracleUsersProjectsDao implements GenericDao<User_Project, Integer> {

    private Connection connection;
    public OracleUsersProjectsDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public User_Project create() throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public User_Project persist(User_Project object) throws PersistException {
        try {
            Statement statement = connection.createStatement();
            boolean rez = statement.execute("INSERT INTO users_projects (user_id, project_id) VALUES (" + object.getUserPK() + ", " + object.getProjectPK());
            if (!rez) throw new PersistException("persist error");
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return object;
    }

    @Override
    public User_Project getByPK(Integer id) throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public void update(User_Project object) throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public void delete(User_Project object) throws PersistException {
        try {
            Statement statement = connection.createStatement();
            int rez = statement.executeUpdate("DELETE FROM users_projects WHERE user_id=" + object.getUserPK() +"department_id=" + object.getProjectPK());
            if (rez != 1) throw new PersistException("delete error");
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<User_Project> getAll() throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public List<Integer> getAllByFirstPK(Integer key) throws PersistException {
        List<Integer> list = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT project_id FROM users_projects WHERE user_id=" + key);
            while (set.next()){
                list.add(set.getInt("project_id"));
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
            ResultSet set = statement.executeQuery("SELECT user_id FROM users_projects WHERE project_id=" + key);
            while (set.next()){
                list.add(set.getInt("user_id"));
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return list;
    }

}
