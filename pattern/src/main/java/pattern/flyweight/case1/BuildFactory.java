package pattern.flyweight.case1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johnny on 2018/3/18.
 */
class BuildFactory {
    private static final Map<String, Gymnasium> gyms = new HashMap<>();

    static Gymnasium getGym(String sport) {
        Gymnasium tyg = gyms.get(sport);
        if (tyg == null) {
            tyg = new Gymnasium(sport);
            gyms.put(sport, tyg);
        }
        return tyg;
    }

    static int getSize() {
        return gyms.size();
    }
}