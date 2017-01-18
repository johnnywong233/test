package lombok;

/**
 * Author: Johnny
 * Date: 2017/1/18
 * Time: 20:41
 */
@EqualsAndHashCode(callSuper = true, exclude = {"address", "city", "state", "zip"})
public class Person1 extends SentientBeing {
    //EqualsAndHashCode can only used when a class extend a parent class
    enum Gender {Male, Female}

    @NonNull
    private String name;
    @NonNull
    private Gender gender;

    private String ssn;
    private String address;
    private String city;
    private String state;
    private String zip;
}
