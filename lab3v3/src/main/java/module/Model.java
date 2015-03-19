package module;

import dao.DaoFactory;
import dao.GenericDao;
import dao.impl.oracle.OracleDaoFactory;
import entity.Department;
import entity.Project;
import entity.User;
import util.exeption.ModelException;
import util.exeption.PersistException;

import java.sql.Connection;
import java.util.*;

/**
 * Created by Ruslan on 14.03.2015.
 */

public class Model<Context extends Connection> {

    private DaoFactory<Context> factory;
    private Context context;

    public Model(DaoFactory<Context> factory) throws ModelException {
        this.factory = factory;
        try {
            context = factory.getContext();
        } catch (PersistException e) {
            throw new ModelException(e);
        }
    }

    /**
     * список сотрудников, работающих над заданным проектом;
     * @param project - проект
     * @return - список сотрудников
     * @throws ModelException
     */
    public List<User> getUsersWithProject(Project project) throws ModelException {
        List<User> users;
        try {
            GenericDao<User, Integer> userDao = factory.getDao(context, User.class);
            users = new LinkedList<>();
            for (Integer users_id : project.getUserKeys()) {
                users.add(userDao.getByPK(users_id));
            }
        } catch (PersistException e) {
            throw new ModelException(e);
        }
        return users;
    }


    /**
     * список проектов, в которых участвует заданный сотрудник
     * @param user сотрудник
     * @return список проектов
     * @throws PersistException
     */
    public List<Project> getAllProjectToUser(User user) throws PersistException {
        List<Project> projects = new LinkedList<>();
        try {
            GenericDao<Project, Integer> projectDao = factory.getDao(context, Project.class);
            for (Integer project_id:user.getProjectKeys()){
                projects.add(projectDao.getByPK(project_id));
            }
        } catch (PersistException e) {
            throw new PersistException(e);
        }

        return projects;
    }

    /**
     *•	список сотрудников из заданного отдела, не участвующих ни в одном проекте
     * @param department отдел
     * @return список сотрудников
     * @throws ModelException
     */
    public List<User> getUsersWhoDoNothingInDepartment(Department department) throws ModelException {
        List<User> users = new LinkedList<>();
        try {
            GenericDao<User, Integer> userDao = factory.getDao(context, User.class);
            for (Integer user_id : department.getUserKeys()) {
                User user = userDao.getByPK(user_id);
                if (user.getProjectKeys() == null || user.getProjectKeys().size() == 0) {
                    users.add(user);
                }
            }
        } catch (PersistException e) {
            throw new ModelException(e);
        }
        return users;
    }

    /**
     * список сотрудников, не участвующих ни в одном проекте
     * @return список сотрудников
     * @throws ModelException
     */
    public List<User> getUsersWhoDoNothing() throws ModelException {
        List<User> users = new LinkedList<>();
        try {
            GenericDao<User, Integer> userDao = factory.getDao(context, User.class);
            for (User user : userDao.getAll()) {
                if (user.getProjectKeys() == null || user.getProjectKeys().size() == 0) {
                    users.add(user);
                }
            }
        } catch (PersistException e) {
            throw new ModelException(e);
        }
        return users;
    }

    /**
     * список подчиненных для заданного руководителя (по всем проектам, которыми он руководит)
     * @param user босс
     * @return список сотрудников
     * @throws ModelException
     */
    public List<User> getAllEmployeeOnBoss(User user) throws ModelException {
        if (user.getRole() != 0) throw new ModelException("it is not boss");
        return getUsersNeighborsToUser(user);
    }


    /**
     * список сотрудников, участвующих в тех же проектах, что и заданный сотрудник
     * @param user сотрудник
     * @return список сотрудников
     * @throws ModelException
     */
    public List<User> getUsersNeighborsToUser(User user) throws ModelException {
        List<User> users = new LinkedList<>();
        GenericDao<Project, Integer> projectDao;
        GenericDao<User, Integer> userDao;
        try {
            projectDao = factory.getDao(context, Project.class);
            userDao = factory.getDao(context, User.class);

            Set<Integer> users_id = new HashSet<>();
            for (Integer proj_id : user.getProjectKeys()) {
                Project project = projectDao.getByPK(proj_id);
                for (Integer user_id : project.getUserKeys()) {
                    users_id.add(user_id);
                }
            }

            users_id.remove(user.getId());
            for (Integer user_id : users_id) {
                users.add(userDao.getByPK(user_id));
            }

        } catch (PersistException e) {
            throw new ModelException(e);
        }
        return users;
    }

    /**
     * список руководителей для заданного сотрудника (по всем проектам, в которых он участвует
     * @param user бос
     * @return список сотрудников
     * @throws ModelException
     */
    public List<User> getAllBossForUser(User user) throws ModelException {
        List<User> bosses = getUsersNeighborsToUser(user);
        Iterator<User> iterator = bosses.iterator();
        while (iterator.hasNext()){
            User u = iterator.next();
            if (u.getRole() != 0) iterator.remove();
        }
        return bosses;
    }

    /**
     * список проектов, выполняемых для заданного заказчика
     * @param user заказчик
     * @return список проектов
     * @throws PersistException
     */
    public List<Project> getAllProjectToBoss(User user) throws PersistException {
        List<Project> projects = new LinkedList<>();
        try {
            GenericDao<Project, Integer> projectDao = factory.getDao(context, Project.class);
            for(Integer project_id: user.getProjectKeys()){
                projects.add(projectDao.getByPK(project_id));
            }
        } catch (PersistException e) {
            throw new PersistException(e);
        }
        return projects;
    }

    /**
     * список сотрудников, участвующих в проектах, выполняемых для заданного заказчика
     * @param user заказчик
     * @return список сотрудников
     * @throws ModelException
     */
        public List<User> getAllUsersForCustomer(User user) throws ModelException {
        if (user.getRole() != 1) throw new ModelException("it is not boss");
        return getUsersNeighborsToUser(user);
    }



    public static void main(String[] args) throws PersistException {
        Project project;
        DaoFactory<Connection> factory = OracleDaoFactory.getOracleDaoFactory();
        GenericDao<Project, Integer> projectIntegerGenericDao = factory.getDao(factory.getContext(), Project.class);
        project = projectIntegerGenericDao.getByPK(13);
        Project project1 = new Project();

        try {
            Model<Connection> model = new Model<>(factory);
            List<User> users = model.getUsersWithProject(project);
            System.out.println(users.size());
            for (User user : users) {
                System.out.println(user);
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }

    }
}
