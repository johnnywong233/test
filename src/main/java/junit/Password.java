package junit;

public class Password {
	public static void validate(String password) throws InvalidPasswordException {
        if (password == null || password.length() == 0) {
            throw new InvalidPasswordException("Password is required.");
        }

        if (password.length() < 6) {
            throw new InvalidPasswordException("Password must contains at least 6 letters.");
        }

        if (password.length() > 15) {
            throw new InvalidPasswordException("Password length less than 15 letters");
        }
    }
}

@SuppressWarnings("serial")
class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }

}
