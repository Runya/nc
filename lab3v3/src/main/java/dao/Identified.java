package dao;

import java.io.Serializable;

/**
 * Created by Ruslan on 02.03.2015.
 */
public abstract class Identified <PK extends Serializable> {
    
    public abstract PK getId();

    
}
