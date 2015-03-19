package dao.impl.oracle;

import dao.AbstractJDBCDao;
import dao.DaoFactory;
import dao.GenericDao;
import entity.*;
import util.exeption.PersistException;

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
public class OracleProjectDao extends AbstractJDBCDao<Project, Integer> {

    public OracleProjectDao(Connection connection){
        super(connection);
    }

    @Override
    public String getTableName() {
        return "projects";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, name FROM projects";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE projects SET name= ? WHERE id= ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM projects WHERE id= ?";
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO projects (name) \n" +
                "VALUES (?)";
    }

    @Override
    protected List<Project> parseResultSet(ResultSet resultSet) throws PersistException {
        LinkedList<Project> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Project d = new Project();
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
    protected void prepareStatementForUpdate(PreparedStatement statement, Project object) throws PersistException {
        try {
            statement.setInt(2, object.getId());
            statement.setString(1, object.getName());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Project object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Project object) throws PersistException {
        try {
            statement.setString(1, object.getName());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Project create() throws PersistException {
        Project d = new Project();
        return persist(d);
    }

    //tak robutj ne mojno
    @Override
    public Project getByPK(Integer key) throws PersistException {
        Project projects = super.getByPK(key);
        setUsersId(projects);
        return projects;
    }

    private void setUsersId(Project project) throws PersistException{
        GenericDao<User_Project, Integer> user_proj_dao = getUserProjectDao();
        List<Integer> user_projectList = user_proj_dao.getAllBySecondPK(project.getId());
        project.setUserKeys(user_projectList);
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

    @Override
    public void delete(Project object) throws PersistException {
        super.delete(object);
        GenericDao<User_Project, Integer> user_proj_dao = getUserProjectDao();
        List<Integer> user_proj_list = object.getUserKeys();
        if (user_proj_list != null)
        for (Integer user_id:user_proj_list){
            user_proj_dao.delete(new User_Project(user_id, object.getId()));
        }
    }

    @Override
    public void update(Project object) throws PersistException {
        super.update(object);
        GenericDao<User_Project, Integer> user_proj_dao = getUserProjectDao();
        List<Integer> update_list = new LinkedList<>(object.getUserKeys());
        List<Integer> change_list = user_proj_dao.getAllBySecondPK(object.getId());
        Iterator<Integer> iterator = update_list.iterator();
        while (iterator.hasNext()){
            Integer id = iterator.next();
            if (change_list.contains(id)){
                change_list.remove(id);
                iterator.remove();
            }
        }
        for (Integer id:update_list){
            user_proj_dao.persist(new User_Project(id, object.getId()));
        }

        for (Integer id:change_list){
            user_proj_dao.delete(new User_Project(id, object.getId()));
        }

    }

    @Override
    public List<Project> getAll() throws PersistException {
        List<Project> projects = super.getAll();
        for (Project project:projects){
            setUsersId(project);
        }
        return projects;
    }
}
