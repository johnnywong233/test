package ini4j;

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.net.URI;

/**
 * Author: Johnny
 * Date: 2017/2/10
 * Time: 18:49
 */
public interface Dwarf {
    String PROP_AGE = "age";
    String PROP_FORTUNE_NUMBER = "fortuneNumber";
    String PROP_HEIGHT = "height";
    String PROP_HOME_DIR = "homeDir";
    String PROP_HOME_PAGE = "homePage";
    String PROP_WEIGHT = "weight";

    int getAge();

    void setAge(int age);

    int[] getFortuneNumber();

    void setFortuneNumber(int[] value);

    double getHeight();

    void setHeight(double height) throws PropertyVetoException;

    String getHomeDir();

    void setHomeDir(String dir);

    URI getHomePage();

    void setHomePage(URI location);

    double getWeight();

    void setWeight(double weight);

    void addPropertyChangeListener(String property, PropertyChangeListener listener);

    void addVetoableChangeListener(String property, VetoableChangeListener listener);

    boolean hasAge();

    boolean hasHeight();

    boolean hasHomePage();

    boolean hasWeight();

    void removePropertyChangeListener(String property, PropertyChangeListener listener);

    void removeVetoableChangeListener(String property, VetoableChangeListener listener);
}
