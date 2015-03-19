package dao.impl.collection;

import dao.DaoFactory;
import dao.GenericDao;
import entity.Department;
import entity.Project;
import entity.User;
import util.exeption.PersistException;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ruslan on 05.03.2015.
 */
public class CollectionDaoFactory implements DaoFactory<CollectionPull> {


    private CollectionPull collectionPull;

    
    private Map<Class, DaoCreator<CollectionPull>> creators;

    @Override
    public GenericDao getDao(CollectionPull collectionPull, Class daoClass) throws PersistException {
        collectionPull = CollectionPull.getCollectionPull();
        DaoCreator<CollectionPull> creator = creators.get(daoClass);
        return creator.create(collectionPull);
    }

    @Override
    public CollectionPull getContext() throws PersistException {

        return collectionPull;
    }


    public CollectionDaoFactory(){
        creators = new HashMap<>();

        creators.put(Department.class, collectionPull1 -> new CollectionDao(Department.class, collectionPull1.getHashMap(Department.class)));
        
        creators.put(Project.class, collectionPull1 -> new CollectionDao(Project.class, collectionPull1.getHashMap(Project.class)));
        
        creators.put(User.class, collectionPull -> new CollectionDao(User.class, collectionPull.getHashMap(User.class)));
        
        
        
        
        
    }
    
    

}
