package module.comparators;

import entity.Project;

import java.util.Comparator;

/**
 * Created by Ruslan on 17.03.2015.
 */
public class SortProjects implements Comparator<Project> {
    @Override
    public int compare(Project o1, Project o2) {
        return o1.getName().compareTo(o2.getName());
    }
}