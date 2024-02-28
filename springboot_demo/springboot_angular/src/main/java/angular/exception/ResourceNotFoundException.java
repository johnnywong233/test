package angular.exception;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 22:15
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final Throwable cause) {
        super(cause);
    }
}
