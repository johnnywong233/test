package pattern.flyweight.case1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johnny on 2018/3/18.
 */
class BuildFactory {
    private static final Map<String, Gymnasium> GYMS = new HashMap<>();

    static Gymnasium getGym(String sport) {
        Gymnasium tyg = GYMS.get(sport);
        if (tyg == null) {
            tyg = new Gymnasium(sport);
            GYMS.put(sport, tyg);
        }
        return tyg;
    }

    static int getSize() {
        return GYMS.size();
    }
}