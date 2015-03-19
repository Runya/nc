package module;

import dao.DaoFactory;
import dao.GenericDao;
import dao.impl.oracle.OracleDaoFactory;
import entity.Department;
import entity.Project;
import entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import util.exeption.ModelException;
import util.exeption.PersistException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ModelTest {

    private static final DaoFactory<Connection> factory = OracleDaoFactory.getOracleDaoFactory();

    private Connection connection;
    private Model<Connection> model;


    @Before
    public void beforeTest() throws ModelException, SQLException, PersistException {
            connection  = factory.getContext();
            connection.setAutoCommit(false);
            model = new Model<>(factory);
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback();
    }


    @Test
    public void testGetUsersWithProject() throws Exception {

        List<User> list = model.getUsersWithProject(getProject(13));
        Assert.assertTrue(list.size() > 0);
    }

    private Project getProject(Integer id) throws PersistException {
        GenericDao<Project, Integer> projectDao = factory.getDao(connection, Project.class);
        return projectDao.getByPK(id);
    }

    private User getUser(Integer id) throws PersistException {
        GenericDao<User, Integer> userDao = factory.getDao(connection, User.class);
        return userDao.getByPK(id);
    }

    private Department getDepartament(Integer id) throws PersistException{
        GenericDao<Department, Integer> departmentDao = factory.getDao(connection, Department.class);
        return departmentDao.getByPK(id);
    }

    @Test
    public void testGetAllProjectToUser() throws Exception {
        List<Project> projects = model.getAllProjectToUser(getUser(2));
        Assert.assertNotNull(projects);
        Assert.assertTrue(projects.size() > 0);
    }

    @Test
    public void testGetUsersWhoDoNothingInDepartment() throws Exception {
        Department department = getDepartament(4);
        List<User> useless = model.getUsersWhoDoNothingInDepartment(department);
        Assert.assertNotNull(useless);
        Assert.assertTrue(useless.size() > 0);
    }

    @Test
    public void testGetUsersWhoDoNothing() throws Exception {
        List<User> useless = model.getUsersWhoDoNothing();
        Assert.assertNotNull(useless);
        Assert.assertTrue(useless.size() > 0);
    }

    @Test
    public void testGetAllEmployeeOnBoss() throws Exception {
        User user = getUser(5);
        List<User> employees = model.getAllEmployeeOnBoss(user);
        Assert.assertNotNull(employees);
        Assert.assertTrue(employees.size() > 0);
    }

    @Test
    public void testGetUsersNeighborsToUser() throws Exception {
        User user = getUser(2);
        List<User> neighbors = model.getUsersNeighborsToUser(user);
        Assert.assertNotNull(neighbors);
        Assert.assertTrue(neighbors.size() > 0);
    }

    @Test
    public void testGetAllBossForUser() throws Exception {
        User user = getUser(2);
        List<User> bosses = model.getAllBossForUser(user);
        Assert.assertNotNull(bosses);
        Assert.assertTrue(bosses.size() > 0);
    }

    @Test
    public void testGetAllProjectToBoss() throws Exception {
        List<Project> projects = model.getAllProjectToBoss(getUser(5));
        Assert.assertNotNull(projects);
        Assert.assertTrue(projects.size() > 0);
    }

    @Test
    public void testAllUsersForCustomer() throws Exception {
        User user = getUser(4);
        List<User> bosses = model.getAllBossForUser(user);
        Assert.assertNotNull(bosses);
        Assert.assertTrue(bosses.size() > 0);
    }


}