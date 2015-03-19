package entity;

import dao.Identified;

import java.util.List;

/**
 * Created by Ruslan on 02.03.2015.
 */
public class Project extends Identified<Integer> {
    
    private Integer id;
    
    private String name;
    
    private List<Integer> userKeys;

    public List<Integer> getUserKeys() {
        return userKeys;
    }

    public void setUserKeys(List<Integer> userKeys) {
        this.userKeys = userKeys;
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

    public Project() {
    }
}
