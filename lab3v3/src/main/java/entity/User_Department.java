package entity;

import dao.Identified;
import dao.Relates;

import java.io.Serializable;

/**
 * Created by Ruslan on 13.03.2015.
 */
public class User_Department extends Identified<Integer> implements Relates{
    private Integer userPK;
    private Integer departmentPK;

    public User_Department(Integer userPK, Integer departmentPK) {
        this.userPK = userPK;
        this.departmentPK = departmentPK;
    }

    public Integer getUserPK() {
        return userPK;
    }

    public Integer getDepartmentPK() {
        return departmentPK;
    }

    @Override
    public Integer getId() {
        return null;
    }
}
