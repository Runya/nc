package dao.impl.collection;

import dao.Identified;

import java.util.HashMap;

/**
 * Created by Ruslan on 05.03.2015.
 */
public class CollectionPull {
    
    private static CollectionPull collectionPull;
    
    private HashMap<Class, HashMap> hashMap;
    
    private CollectionPull(){
        hashMap = new HashMap<>();
    }
    
    public static CollectionPull getCollectionPull(){
        
        if (collectionPull == null) collectionPull = new CollectionPull();
        
        return collectionPull;
        
    }
    
    public HashMap getHashMap(Class class1){
        if (! hashMap.containsKey(class1)) hashMap.put(class1, new HashMap<>());
        return hashMap.get(class1);
    }
    
}
