package module.comparators;

import entity.User;

import java.util.Comparator;

/**
 * Created by Ruslan on 17.03.2015.
 */

public class SortUsersByName implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        int k = o1.getName().compareTo(o2.getName());
        if (k == 0) k = o1.getSur_name().compareTo(o2.getSur_name());
        return k;
    }

}
