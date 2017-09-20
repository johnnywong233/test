package angular.exception;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 22:15
 */
public class ResourceNotFoundException extends Exception {
    private static final long serialVersionUID = 4088649120307193208L;

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
