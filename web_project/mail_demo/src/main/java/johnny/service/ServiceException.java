package johnny.service;

public class ServiceException extends Exception {
    private static final long serialVersionUID = -1708015121235851228L;

    ServiceException(String message) {
        super(message);
    }
}
