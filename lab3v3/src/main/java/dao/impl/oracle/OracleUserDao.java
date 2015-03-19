package dao.impl.oracle;

import dao.AbstractJDBCDao;
import dao.DaoFactory;
import dao.GenericDao;
import entity.*;
import util.exeption.PersistException;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ruslan on 3/10/2015.
 */
public class OracleUserDao extends AbstractJDBCDao<User, Integer> {

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, name, surname, role FROM users";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET name= ?, surname= ?, role= ? WHERE id= ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE id= ?";
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO users (name, surname, role) \n" +
                "VALUES (?, ?, ?)";
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws PersistException {
        LinkedList<User> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                User u = new User();
                u.setId(resultSet.getInt("id"));
                u.setSur_name(resultSet.getString("surname"));
                u.setName(resultSet.getString("name"));
                u.setRole(resultSet.getInt("role"));
                result.add(u);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return result;
    }


    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSur_name());
            statement.setInt(3, object.getRole());
            statement.setInt(4, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSur_name());
            statement.setInt(3, object.getRole());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    public OracleUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public User create() throws PersistException {
        User u = new User();
        return persist(u);
    }

    //tak robutj ne mojno
    @Override
    public User getByPK(Integer key) throws PersistException {
        User user = super.getByPK(key);
        setProjectsId(user);
        setDepartmentId(user);
        return user;
    }

    private void setProjectsId(User user) throws PersistException{
        GenericDao<User_Project, Integer> user_proj_dao = getUserProjectDao();
        List<Integer> user_projectList = user_proj_dao.getAllByFirstPK(user.getId());
        user.setProjectKeys(user_projectList);
    }

    private void setDepartmentId(User user) throws PersistException{
        GenericDao<User_Department, Integer> user_dept_dao = getUserDepartmentDao();
        List<Integer> user_departmentList = user_dept_dao.getAllByFirstPK(user.getId());
        user.setDepartmentKeys(user_departmentList);
    }

    @Override
    public List<Integer> getAllByFirstPK(Integer key) throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public List<Integer> getAllBySecondPK(Integer key) throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    private GenericDao<User_Project, Integer> getUserProjectDao() throws PersistException{
        DaoFactory<Connection> daoFactory  = OracleDaoFactory.getOracleDaoFactory();
        GenericDao<User_Project, Integer> dao = daoFactory.getDao(getConnection(), User_Project.class);
        return dao;
    }

    private GenericDao<User_Department, Integer> getUserDepartmentDao() throws PersistException{
        DaoFactory<Connection> daoFactory  = OracleDaoFactory.getOracleDaoFactory();
        GenericDao<User_Department, Integer> dao = daoFactory.getDao(getConnection(), User_Department.class);
        return dao;
    }

    @Override
    public void delete(User object) throws PersistException {
        super.delete(object);
        GenericDao<User_Department, Integer> user_dept_dao = getUserDepartmentDao();
        List<Integer> user_dept_list = object.getDepartmentKeys();
        if (user_dept_list != null)
        for (Integer deptId:user_dept_list){
            user_dept_dao.delete(new User_Department(object.getId(), deptId));
        }

        GenericDao<User_Project, Integer> user_proj_dao = getUserProjectDao();
        List<Integer> user_proj_list = object.getProjectKeys();
        if (user_proj_list != null)
        for (Integer projectId:user_proj_list){
            user_proj_dao.delete(new User_Project(object.getId(), projectId));
        }
    }

    @Override
    public void update(User object) throws PersistException {
        super.update(object);
        GenericDao<User_Department, Integer> user_dept_dao = getUserDepartmentDao();
        List<Integer> update_list = new LinkedList<>(object.getDepartmentKeys());
        List<Integer> change_list = user_dept_dao.getAllByFirstPK(object.getId());
        Iterator<Integer> iterator = update_list.iterator();
        while (iterator.hasNext()){
            Integer id = iterator.next();
            if (change_list.contains(id)){
                change_list.remove(id);
                iterator.remove();
            }
        }
        for (Integer id:update_list){
            user_dept_dao.persist(new User_Department(object.getId(), id));
        }

        for (Integer id:change_list){
            user_dept_dao.delete(new User_Department(object.getId(), id));
        }

        GenericDao<User_Project, Integer> user_proj_dao = getUserProjectDao();
        update_list = new LinkedList<>(object.getProjectKeys());
        change_list = user_proj_dao.getAllByFirstPK(object.getId());
        iterator = update_list.iterator();
        while (iterator.hasNext()){
            Integer id = iterator.next();
            if (change_list.contains(id)){
                change_list.remove(id);
                iterator.remove();
            }
        }
        for (Integer id:update_list){
            user_proj_dao.persist(new User_Project(object.getId(), id));
        }

        for (Integer id:change_list){
            user_proj_dao.delete(new User_Project(object.getId(), id));
        }




    }

    @Override
    public List<User> getAll() throws PersistException {
        List<User> users = super.getAll();
        for (User user:users){
            setProjectsId(user);
            setDepartmentId(user);
        }
        return users;
    }
}
