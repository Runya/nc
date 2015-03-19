package dao;

import dao.impl.oracle.OracleDaoFactory;
import entity.Department;
import entity.Project;
import entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import util.exeption.PersistException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;


public class OracleDaoTest extends GenericDaoTest<Connection> {

    private Connection connection;

    private GenericDao dao;

    private static final DaoFactory<Connection> factory = OracleDaoFactory.getOracleDaoFactory();

    @Parameterized.Parameters
    public static Collection getParameters() {
        return Arrays.asList(new Object[][]{
                {Department.class, new Department()},
                {User.class, new User()},
                {Project.class, new Project()}
        });
    }

    @Before
    public void setUp() throws PersistException, SQLException {
        connection = factory.getContext();
        connection.setAutoCommit(false);
        dao = factory.getDao(connection, daoClass);
    }

    @After
    public void tearDown() throws SQLException {
        context().rollback();
        context().close();
    }

    @Override
    public GenericDao dao() {
        return dao;
    }

    @Override
    public Connection context() {
        return connection;
    }

    /*@Test
    public void testUpdate()throws Exception{
        Department department = (Department) dao.create();
        Assert.assertNotNull(department.getId());
        String update = "PNX";
        department.setName(update);
        dao.update(department);
        Department updateDept = (Department) dao.getByPK(department.getId());
        Assert.assertEquals(department.getName(), updateDept.getName());
    }*/


   /* @Test
    public void testDepartmentUsers() throws Exception{
        Department department = (Department) dao.getByPK(4);
        Assert.assertTrue("users do not linked", department.getUserKeys().size() > 0);
    }


    @Test
    public void testDepartmentUsersDelete() throws PersistException, SQLException {
        connection.setAutoCommit(false);
        connection.commit();
        Department department = (Department) dao.getByPK(4);
        int user_count = department.getUserKeys().size();
        department.getUserKeys().clear();
        dao.update(department);
        department = (Department) dao.getByPK(4);
        Assert.assertTrue(department.getUserKeys().size() == 0);
        connection.rollback();
    }*/

    public OracleDaoTest(Class clazz, Identified<Integer> notPersistedDto) {
        super(clazz, notPersistedDto);
    }
}
