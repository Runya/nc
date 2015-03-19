package dao;

import util.exeption.PersistException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Абстрактний класс, надає юазову реалізацію CRUD операцій з використовуванням JDBC.
 * @param <T> тип об'єкту персистенції
 * @param <PK> тип первинного ключа
 */


public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Serializable> implements GenericDao<T, PK> {

    private Connection connection;

    public abstract String getTableName();
    /**
     *
     * @return повертає sql запит для отримання всіх записів
     */


    public abstract String getSelectQuery();

    /**
     *
     * @return повертає SQL запит для оновлення запису.
     */
    public abstract String getUpdateQuery();

    /**
     *
     * @return повертає SQL запит для видалення запису.
     */
    public abstract String getDeleteQuery();

    /**
     * @return повертає SQL запит для створення запиту.
     */
    protected abstract String getCreateQuery();

    /**
     *
     * @param resultSet резудьтат запиту
     * @return повертає список обєктів
     */
    protected abstract List<T> parseResultSet(ResultSet resultSet) throws PersistException;


    /**
     * встановлює значення аргументів для update
     * @param statement - обєкт запиту
     * @param object - значення аргументу
     * @throws PersistException
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;
    /**
     * встановлює значення аргументів для delete
     * @param statement - обєкт запиту
     * @param object - об'єкт для видалення
     * @throws PersistException
     */
    protected abstract void prepareStatementForDelete(PreparedStatement statement, T object) throws PersistException;

    /**
     * встановлює значення аргументів для insert
     * @param statement - обєкт запиту
     * @param object - об'єкт для добавляння
     * @throws PersistException
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;

    @Override
    public T getByPK(PK key) throws PersistException {
        List<T> list = null;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer)key);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new PersistException(e);
        }

        if (list == null || list.size() == 0)
            return null;

        if (list.size() > 1)
            throw new PersistException("Received more than one record.");

        return list.iterator().next();

    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new PersistException(e);
        }

        return list;

    }

    @Override
    public void update(T object) throws PersistException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }

    }

    @Override
    public void delete(T object) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
            statement.close();
        } catch (SQLException e) {
            throw new PersistException(e);
        }

    }

    protected Connection getConnection() {
        return connection;
    }

    @Override
    public T persist(T object) throws PersistException {
        if (object.getId() != null) {
            throw new PersistException("Object is already persist.");
        }
        T persistInstance;
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        sql = getSelectQuery() + " WHERE id = (SELECT MAX(id) from " + getTableName() + ")";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<T> list = parseResultSet(resultSet);
            if (list == null || list.size() != 1) {
                throw new PersistException("Exception on findByPK new persist data.");
            }
            persistInstance = list.iterator().next();
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return persistInstance;
    }


    public AbstractJDBCDao(Connection connection) {
        this.connection = connection;
    }


}
