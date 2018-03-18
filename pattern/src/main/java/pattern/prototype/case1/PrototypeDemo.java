package pattern.prototype.case1;

/**
 * Created by Johnny on 2018/3/18.
 */
public class PrototypeDemo implements Cloneable {
    public Object clone() {
        Object object = null;
        try {
            object = super.clone();
        } catch (CloneNotSupportedException exception) {
            System.err.println("Not support cloneable");
        }
        return object;
    }
}