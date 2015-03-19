package entity;

import dao.Identified;
import dao.Relates;

/**
 * Created by Ruslan on 13.03.2015.
 */

public class User_Project extends Identified<Integer> implements Relates  {
    private Integer userId;
    private Integer projectId;

    public User_Project(Integer userId, Integer projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

    public Integer getUserPK() {
        return userId;
    }


    public Integer getProjectPK() {
        return projectId;
    }

    @Override
    public Integer getId() {
        return null;
    }
}
