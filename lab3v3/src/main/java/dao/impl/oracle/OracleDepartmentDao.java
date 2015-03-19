package dao.impl.oracle;

import dao.AbstractJDBCDao;
import dao.DaoFactory;
import dao.GenericDao;
import entity.Department;
import entity.User_Department;
import util.exeption.PersistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OracleDepartmentDao extends AbstractJDBCDao<Department, Integer> {
    @Override
    public String getTableName() {
        return "departments";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, name FROM DEPARTMENTS";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE DEPARTMENTS SET name= ? WHERE id= ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM DEPARTMENTS WHERE id= ?";
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO DEPARTMENTS (name) \n" +
                "VALUES (?)";
    }

    @Override
    protected List<Department> parseResultSet(ResultSet resultSet) throws PersistException {
        LinkedList<Department> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Department d = new Department();
                d.setId(resultSet.getInt("id"));
                d.setName(resultSet.getString("name"));
                result.add(d);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return result;
    }


    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Department object) throws PersistException {
        try {
            statement.setInt(2, object.getId());
            statement.setString(1, object.getName());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Department object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Department object) throws PersistException {
        try {
            statement.setString(1, object.getName());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    public OracleDepartmentDao(Connection connection) {
        super(connection);
    }

    @Override
    public Department create() throws PersistException {
        Department d = new Department();
        return persist(d);
    }

    //tak robutj ne mojno
    @Override
    public Department getByPK(Integer key) throws PersistException {
        Department department = super.getByPK(key);
        setUsersId(department);
        return department;
    }

    private void setUsersId(Department department) throws PersistException{
        GenericDao<User_Department, Integer> user_dept_dao = getUserDepartmentDao();
        List<Integer> user_departmentList = user_dept_dao.getAllBySecondPK(department.getId());
        department.setUserKeys(user_departmentList);
    }

    @Override
    public List<Integer> getAllByFirstPK(Integer key) throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    @Override
    public List<Integer> getAllBySecondPK(Integer key) throws PersistException {
        throw new PersistException("method don`t realized for this object type");
    }

    private GenericDao<User_Department, Integer> getUserDepartmentDao() throws PersistException{
        DaoFactory<Connection> daoFactory  = OracleDaoFactory.getOracleDaoFactory();
        GenericDao<User_Department, Integer> dao = daoFactory.getDao(getConnection(), User_Department.class);
        return dao;
    }

    @Override
    public void delete(Department object) throws PersistException {
        super.delete(object);
        GenericDao<User_Department, Integer> user_dept_dao = getUserDepartmentDao();
        List<Integer> user_dept_list = object.getUserKeys();
        if (user_dept_list != null)
        for (Integer user_id:user_dept_list){
            user_dept_dao.delete(new User_Department(user_id, object.getId()));
        }
    }

    @Override
    public void update(Department object) throws PersistException {
        super.update(object);
        GenericDao<User_Department, Integer> user_dept_dao = getUserDepartmentDao();
        List<Integer> update_list = new LinkedList<>();
        if (object.getUserKeys() != null){
            update_list.addAll(object.getUserKeys());
        }
        List<Integer> change_list = user_dept_dao.getAllBySecondPK(object.getId());
        Iterator<Integer> iterator = update_list.iterator();
        while (iterator.hasNext()){
            Integer id = iterator.next();
            if (change_list.contains(id)){
                change_list.remove(id);
                iterator.remove();
            }
        }
        for (Integer id:update_list){
            user_dept_dao.persist(new User_Department(id, object.getId()));
        }

        for (Integer id:change_list){
            user_dept_dao.delete(new User_Department(id, object.getId()));
        }

    }

    @Override
    public List<Department> getAll() throws PersistException {
        List<Department> departments = super.getAll();
        for (Department department:departments){
            setUsersId(department);
        }
        return departments;
    }
}
