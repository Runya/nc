package model;

import javax.ejb.Stateless;

/**
 * Created by Ruslan on 20.03.2015.
 */
@Stateless(name = "CalcEJB")
public class CalcBean {
    public CalcBean() {
    }

    public int calc(int x, int y){
        return x + y;
    }
}
