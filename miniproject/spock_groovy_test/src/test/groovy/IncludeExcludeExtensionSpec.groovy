import spock.lang.Specification

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Shows how methods can be included and excluded from a spec run. For
 * demonstration purposes, the configuration script's location is set
 * programmatically in a static initializer. Usually, the configuration
 * script's location would be set externally (via the same system property),
 * or the configuration script would reside in one of the two default locations:
 * <li>
 * <ul><tt>SpockConfig.groovy</tt> on the class path</ul>
 * <ul><tt>$user_home/.spock/SpockConfig.groovy</tt> on the file system</ul>
 * </li>
 *
 * <p>Note that the configuration script referenced by the system property
 * may either reside on the class path or on the file system (make sure to
 * include the whole path).
 *
 * <p>What's not shown here is that filtering also works for classes.
 * Whereas methods can only be filtered based on annotations, classes can
 * also be filtered based on their base class.
 *
 * @since 0.4
 */
class IncludeExcludeExtensionSpec extends Specification {
    static {
        System.setProperty "spock.configuration", "IncludeFastConfig.groovy"

        // Alternatively, try this:
        // System.setProperty "spock.configuration", "ExcludeSlowConfig.groovy"
    }

    @Fast
    def "a fast method"() {
        expect: true
    }

    @Slow
    def "a slow method"() {
        expect: true
    }

    def "a neither fast nor slow method"() {
        expect: true
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Fast {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Slow {}
