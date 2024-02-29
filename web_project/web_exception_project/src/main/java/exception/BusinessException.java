package exception;

public class BusinessException extends RuntimeException {
    public BusinessException(Object obj) {
        super(obj.toString());
    }

}
