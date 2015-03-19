import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ruslan on 13.03.2015.
 */
public class TestList {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Iterator<Integer> iterator = list.iterator();
        for(;iterator.hasNext();){
            iterator.next();
            iterator.remove();
        }





    }
}
