package entity;

import dao.Identified;

import java.util.List;

/**
 * Created by Ruslan on 02.03.2015.
 */
public class User extends Identified<Integer> {
    
    private Integer id;
    
    private String name;
    
    private String sur_name;
    
    private int role;

    private List<Integer> projectKeys;
    
    private List<Integer> departmentKeys;

    public User() {
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

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<Integer> getProjectKeys() {
        return projectKeys;
    }

    public void setProjectKeys(List<Integer> projectKeys) {
        this.projectKeys = projectKeys;
    }

    public List<Integer> getDepartmentKeys() {
        return departmentKeys;
    }

    public void setDepartmentKeys(List<Integer> departmentKeys) {
        this.departmentKeys = departmentKeys;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append(id).append(" ")
                .append(name)
                .append(" ")
                .append(sur_name)
                .append(role)
                .append("\n");
        ans.append("projects id:\n");
        for (Integer id:projectKeys) {
            ans.append("\t")
                    .append(id).append(", ");
        }
        ans.append("\ndepartments id:");
        for (Integer id:departmentKeys) {
            ans.append("\t")
                    .append(id).append(", ");
        }
        return ans.toString();
    }
}
