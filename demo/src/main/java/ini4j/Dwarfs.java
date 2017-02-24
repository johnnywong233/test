package ini4j;

/**
 * Author: Johnny
 * Date: 2017/2/10
 * Time: 18:51
 */
public interface Dwarfs {
    String PROP_BASHFUL = "bashful";
    String PROP_DOC = "doc";
    String PROP_DOPEY = "dopey";
    String PROP_GRUMPY = "grumpy";
    String PROP_HAPPY = "happy";
    String PROP_SLEEPY = "sleepy";
    String PROP_SNEEZY = "sneezy";

    Dwarf getBashful();

    Dwarf getDoc();

    Dwarf getDopey();

    Dwarf getGrumpy();

    Dwarf getHappy();

    Dwarf getSleepy();

    Dwarf getSneezy();
}
