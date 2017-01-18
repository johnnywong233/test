package lombok;


/**
 * Author: Johnny
 * Date: 2017/1/18
 * Time: 20:07
 */
@ToString(callSuper = true, exclude = "someExcludedField")
public class Person {
    @Getter
    @Setter
    private boolean employed;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String name;

    public String say() {
        return getName();
    }

    private boolean someBoolean = true;
    private String someStringField;
    private float someExcludedField;
}
