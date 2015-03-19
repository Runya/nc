package dao.impl.collection;

import dao.GenericDao;
import dao.Identified;
import util.exeption.PersistException;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Ruslan on 02.03.2015.
 */


public  class CollectionDao<T extends Identified<PK>, PK extends Serializable> implements GenericDao<T, PK> {
    
    private ContextMap<T, PK> context;
    private Class type;
    
    @Override
    public T getByPK(PK id) throws PersistException {
        checkError(id);
        T object = context.get(id);
        if (object == null) throw new PersistException("Element not found");
        return object;
    }


    @Override
    public void update(T object) throws PersistException {
        checkError(object);
        checkError(object.getId());
        context.put(object.getId(), object);
    }

    @Override
    public void delete(T object) throws PersistException {
        checkError(object);
        context.remove(object.getId());
    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list = new ArrayList<>();
        for (PK t:context.keySet()){
            list.add(context.get(t));
        }
        return list;
    }

    @Override
    public List<PK> getAllByFirstPK(PK key) throws PersistException {
        return null;
    }

    @Override
    public List<PK> getAllBySecondPK(PK key) throws PersistException {
        return null;
    }

    private void checkError(Object obj) throws PersistException{
        if (obj == null) throw new PersistException("Null pointer exception");
    }

    @Override
    public T create() throws PersistException {
        try {
            return persist((T)type.newInstance());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public T persist(T object) throws PersistException {
        return null;
    }


    public CollectionDao(Class type, Map<PK, T> context){
        this.type = type;
        this.context = new ContextMap(context);
    }
}

class ContextMap<T extends Identified<PK>, PK extends Serializable>{

    private Map<PK, T> context;


    public ContextMap(Map<PK, T> context) {
        this.context = context;

    }


    public T get(PK id) throws PersistException {
        chekId(id);
        return context.get(id);
    }

    public void put(PK id, T object) throws PersistException {
        chekId(id);
        context.put(id, object);
    }

    public void remove(PK id) throws PersistException {
        chekId(id);
        context.remove(id);
    }

    public Set<PK> keySet() {
        return context.keySet();
    }


    public void chekId(PK id) throws PersistException {
        if (id == null) throw new PersistException("id is nall.");
    }
}