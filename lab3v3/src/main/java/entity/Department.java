package entity;

import dao.Identified;
import dao.Relates;

import java.util.*;

/**
 * Created by Ruslan on 02.03.2015.
 */

public class Department extends Identified<Integer> {
    
    private Integer id;
    
    private String name;

    private List<Integer> userKeys;

    public List<Integer> getUserKeys() {
        return userKeys;
    }

    public void setUserKeys(List<Integer> userKeys) {
        this.userKeys = userKeys;
    }



    public Department() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
