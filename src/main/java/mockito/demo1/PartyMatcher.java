package mockito.demo1;

import org.mockito.ArgumentMatcher;

/**
 * Created by wajian on 2016/10/10.
 */
public class PartyMatcher<T> extends ArgumentMatcher<T> {
    //TODO
    private Object value;
    private Function<T, Object> function;

    public PartyMatcher(Function<T, Object> getProperty, Object value) {
        this.value = value;
        this.function = getProperty;
    }

    public static <F> PartyMatcher<F> partyMatcher(Function<F, Object> getProperty, Object value) {
        return new PartyMatcher<F>(getProperty, value);
    }

    @Override
    public boolean matches(Object o) {
        return function.apply((T) o).equals(value);
    }

}
