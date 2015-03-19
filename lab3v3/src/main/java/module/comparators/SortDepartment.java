package module.comparators;

import entity.Department;

import java.util.Comparator;

/**
 * Created by Ruslan on 17.03.2015.
 */
public class SortDepartment implements Comparator<Department> {

    @Override
    public int compare(Department o1, Department o2) {
        return o1.getName().compareTo(o2.getName());
    }

}