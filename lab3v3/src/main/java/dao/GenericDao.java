package dao;

import dao.Identified;
import util.exeption.PersistException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ruslan on 02.03.2015.
 */

/**
 * Інтерфейс управління персистентним станом об'єкта
 * @param <T> - тип об'єкта
 * @param <PK> - тип первинного ключа
 */
public interface GenericDao <T extends Identified<PK>, PK extends Serializable> {


    /**
     * Створює нувий запис і відповідний об'єкт
     * @return відповідний обєкт
     * @throws PersistException
     */

    public T create() throws PersistException;

    /**
     * Створює нувий запис відповідаючий об'єкту
     * @return новий об'єкт
     * @throws PersistException
     */
    public T persist(T object) throws PersistException;


    /**
     * повертає об'єкт відповідний первинному ключу id
     * @param id - первинний ключ
     * @return обєкт відповідний первинному ключу id
     * @throws PersistException
     */
    public T getByPK(PK id) throws PersistException;


    /**
     *
     * @param object - оновлює запис про обєкт
     * @throws PersistException
     */
    public void update(T object) throws PersistException;

    /**
     * Видаляє запис про обєкт
     * @param object об'єкт
     * @throws PersistException
     */
    public void delete(T object) throws PersistException;

    /**
     *
     * @return повертає список всіх обєктів
     * @throws PersistException
     */
    public List<T> getAll() throws PersistException;

    public List<PK> getAllByFirstPK(PK key) throws PersistException;

    public List<PK> getAllBySecondPK(PK key) throws PersistException;

}
